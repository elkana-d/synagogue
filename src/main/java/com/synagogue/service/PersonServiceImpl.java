package com.synagogue.service;


import com.synagogue.domain.Person;
import com.synagogue.repository.PersonRepository;
import com.synagogue.service.exception.PersonAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;

    @Inject
    public PersonServiceImpl(final PersonRepository repository) {
        this.personRepository = repository;
    }

    @Override
    @Transactional
    public Person save(@NotNull @Valid final Person person) {
        LOGGER.debug("Creating  {}", person);
        return personRepository.save(person) ;
    }

    @Override
    @Transactional
    public void delete(@NotNull @Valid final Person person) {
        LOGGER.debug("remove{}", person);
        personRepository.delete(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getList() {
        LOGGER.debug("Retrieving the list of all persons");
        return personRepository.findAll();
    }
    @Override
    public Person findOneByFirstNameAndLastName(String firstName, String lastName) {
        return personRepository.findOneByFirstNameAndLastName(firstName, lastName);
    }

}
