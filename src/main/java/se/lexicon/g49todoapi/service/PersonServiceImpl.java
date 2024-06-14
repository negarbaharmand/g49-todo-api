package se.lexicon.g49todoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g49todoapi.domain.dto.PersonDTOForm;
import se.lexicon.g49todoapi.domain.dto.PersonDTOView;
import se.lexicon.g49todoapi.domain.entity.Person;
import se.lexicon.g49todoapi.exception.DataNotFoundException;
import se.lexicon.g49todoapi.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public PersonDTOView create(PersonDTOForm personDtoForm) {
        if (personDtoForm == null) throw new IllegalArgumentException("PersonDto was null.");

        Person person = new Person(personDtoForm.getName());
        Person savedPerson = personRepository.save(person);
        return PersonDTOView.builder().id(savedPerson.getId()).name(savedPerson.getName()).build();
    }

    @Override
    public PersonDTOView findById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Person id is not valid."));
        return PersonDTOView.builder().id(person.getId()).name(person.getName()).build();
    }

    @Override
    public List<PersonDTOView> findAll() {
        List<Person> persons = personRepository.findAll();
        return persons.stream()
                .map(person -> PersonDTOView.builder().id(person.getId()).name(person.getName()).build())
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTOView update(PersonDTOForm personDtoForm) {
        Person person = personRepository.findById(personDtoForm.getId()).orElseThrow(() -> new DataNotFoundException("Person Id is not valid."));
        person.setName(personDtoForm.getName());
        return PersonDTOView.builder().id(person.getId()).name(person.getName()).build();
    }

    @Override
    public void delete(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Person id is not valid."));
        personRepository.delete(person);
    }
}
