package se.lexicon.g49todoapi.domain.dto;

import se.lexicon.g49todoapi.domain.entity.Role;

import java.util.Set;

public class UserDTOForm {
    private String email;
    private String password;
    private Set<RoleDTOForm> roles;


}
