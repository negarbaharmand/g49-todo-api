package se.lexicon.g49todoapi.service;

import se.lexicon.g49todoapi.domain.dto.UserDTOForm;
import se.lexicon.g49todoapi.domain.dto.UserDTOView;
import se.lexicon.g49todoapi.domain.entity.User;

public interface UserService {
    //register user(email, password,role)
    //Find user by email
    //disable user by email
    //enable user by email
    //...

    UserDTOView register(UserDTOForm dtoForm);

    UserDTOView getByEmail(String email);

    void disableEmail(String email);

    void enableEmail(String email);

}
