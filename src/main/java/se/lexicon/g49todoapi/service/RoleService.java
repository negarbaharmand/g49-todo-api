package se.lexicon.g49todoapi.service;

import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.dto.RoleDTOView;
import se.lexicon.g49todoapi.domain.entity.Role;

import java.util.List;

public interface RoleService {

    List<RoleDTOView> getAll();
}
