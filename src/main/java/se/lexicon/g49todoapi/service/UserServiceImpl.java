package se.lexicon.g49todoapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;

@Service
public class UserServiceImpl implements UserService {
    //todo: add required dependencies

    @Override
    public UserDTOView register(UserDTOForm dtoForm) {
        //todo: Implement method
        return null;
    }

    @Override
    public UserDTOView getByEmail(String email) {
        //todo: Implement method
        return null;
    }

    @Override
    public void disableEmail(String email) {
        //todo: Implement method

    }

    @Override
    public void enableEmail(String email) {
        //todo: Implement method

    }
}
