package com.synagogue.controller;


import com.synagogue.domain.Person;
import com.synagogue.service.PersonService;
import com.synagogue.service.exception.PersonAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRestController.class);
    private final PersonService personService;

    @Inject
    public PersonRestController(final PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public Person createPerson(@RequestBody @Valid final Person person) {
        LOGGER.debug("Received request to create the {}", person);
        return personService.save(person);
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public List<Person> listPersons() {
        LOGGER.debug("Received request to list all persons");
        return personService.getList();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlePersonAlreadyExistsException(PersonAlreadyExistsException e) {
        return e.getMessage();
    }

}
