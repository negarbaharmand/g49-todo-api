package se.lexicon.g49todoapi.service;

import se.lexicon.g49todoapi.domain.dto.PersonDTOView;
import se.lexicon.g49todoapi.domain.dto.PersonDTOForm;

import java.util.List;

public interface PersonService {
    PersonDTOView create(PersonDTOForm personDtoForm);

    PersonDTOView findById(Long id);

    List<PersonDTOView> findAll();

    PersonDTOView update(PersonDTOForm personDtoForm);

    void delete(Long id);
}
