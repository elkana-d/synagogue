package com.synagogue.validator;

import com.synagogue.domain.Person;
import com.synagogue.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;

@Component
public class PersonCreateFormValidator implements Validator {

    private final PersonService personService;

    @Inject
    public PersonCreateFormValidator(final PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person form = (Person) target;
        Person person = personService.findOneByFirstNameAndLastName(form.getFirstName(),form.getLastName());
        if (person != null && form.getFirstName().equals(person.getFirstName()) && form.getLastName().equals(person.getLastName())) {
            //errors.rejectValue("firstName", "person.firstNameAndLastNameExist");
            //errors.rejectValue("lastName", "person.firstNameAndLastNameExist");
            errors.reject("person.firstNameAndLastNameExist");
        }
    }
}
