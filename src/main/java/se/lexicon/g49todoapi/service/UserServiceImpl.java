package se.lexicon.g49todoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g49todoapi.domain.dto.RoleDTOView;
import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import se.lexicon.g49todoapi.domain.entity.Role;
import se.lexicon.g49todoapi.domain.entity.User;
import se.lexicon.g49todoapi.exception.DataDuplicateException;
import se.lexicon.g49todoapi.exception.DataNotFoundException;
import se.lexicon.g49todoapi.exception.EmailServiceFailedException;
import se.lexicon.g49todoapi.repository.RoleRepository;
import se.lexicon.g49todoapi.repository.UserRepository;
import se.lexicon.g49todoapi.util.CustomPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final CustomPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, EmailService emailService, CustomPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserDTOView register(UserDTOForm dtoForm) {
        //1. Check parameters
        if (dtoForm == null) throw new IllegalArgumentException("User from cannot be null");
        //2. Check if the email exist in the database
        boolean emailExists = userRepository.existsByEmail(dtoForm.getEmail());
        if (emailExists) throw new DataDuplicateException("Email already exists.");
        //3. Validate roles in the repository
        Set<Role> roleList = dtoForm.getRoles()
                .stream()
                .map(
                        roleDTOForm -> roleRepository.findById(roleDTOForm.getId())
                                .orElseThrow(() -> new DataNotFoundException("Role is not valid")))
                .collect(Collectors.toSet());

        //4. Convert UserDTOForm to User entity
        //5. todo: Hash the password
        User user = User.builder().email(dtoForm.getEmail())
                .password(passwordEncoder.encode(dtoForm.getPassword()))
                .roles(roleList)
                .build();
        //6. Save the User to the database
        User savedUser = userRepository.save(user);

        //7. Convert the repository result to UserDTOView
        //8. return the result
        Set<RoleDTOView> roleDTOViews = savedUser.getRoles()
                .stream()
                .map(
                        role -> RoleDTOView.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build())
                .collect(Collectors.toSet());

        //TODO: Send a Welcome Email When registered USER ✅
        HttpStatusCode emailStatus = emailService.sendRegistrationEmail(dtoForm.getEmail());

        //4. Validate Response
        if (!emailStatus.is2xxSuccessful()) {
            System.out.println("was not 200!");
            throw new EmailServiceFailedException("Email was not sent successfully.");
        }

        return UserDTOView.builder()
                .email(savedUser.getEmail())
                .roles(roleDTOViews)
                .build();
    }

    @Override
    public UserDTOView getByEmail(String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new DataNotFoundException("Email does not exist."));
        Set<RoleDTOView> roleDTOViews = user.getRoles()
                .stream()
                .map(
                        role -> RoleDTOView.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build())
                .collect(Collectors.toSet());

        return UserDTOView.builder()
                .email(user.getEmail())
                .roles(roleDTOViews)
                .build();
    }

    @Override
    @Transactional
    public void disableEmail(String email) {
        isEmailTaken(email);
        userRepository.updateExpiredByEmail(email, true);

    }

    @Override
    @Transactional
    public void enableEmail(String email) {
        isEmailTaken(email);
        userRepository.updateExpiredByEmail(email, false);
    }

    private void isEmailTaken(String email) {
        if (!userRepository.existsByEmail(email))
            throw new DataNotFoundException("Email does not exist.");

    }

}
