package com.synagogue.controller;


import com.synagogue.domain.Person;
import com.synagogue.service.PersonService;
import com.synagogue.service.exception.PersonAlreadyExistsException;
import com.synagogue.validator.PersonCreateFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PersonCreateController {

    boolean isCreated = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCreateController.class);
    private final PersonService personService;
    private final PersonCreateFormValidator personValidator;

    @Inject
    public PersonCreateController(PersonService personService, PersonCreateFormValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @InitBinder ("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(personValidator);
    }

    @RequestMapping(value = "/person_create.html", method = RequestMethod.GET)
    public ModelAndView getCreatePersonView() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("form", new Person());

        if(isCreated) {
            model.put("isCreated", true);
            isCreated=false;
        }
        else{
            model.put("isCreated", false);
        }

        LOGGER.debug("Received request for person create view");
        return new ModelAndView("person_create", model);
    }
/*
    @RequestMapping(value = "/person_create.html", params = "id", method = RequestMethod.GET)
    public ModelAndView getCreatePersonView(@RequestParam("id") long id) {
        return getCreatePersonViewWhitId(id);
    }
*/
    @RequestMapping(value = "/person_create.html", method = RequestMethod.POST)
    public String createPerson(@ModelAttribute("form") @Valid Person formPerson, BindingResult result) {
        LOGGER.debug("Received request to create {}, with result={}", formPerson, result);
        if (result.hasErrors()) {
            formPerson.setId(null);
            return "person_create";
        }
        try {
            personService.save(formPerson);
        } catch (Exception e) {
            LOGGER.debug("Tried to create person with existing id", e);
            result.reject("person.createFailed");
            formPerson.setId(null);
            return "person_create";
        }
        result.reject("person.createSucceed", new String[] {formPerson.getFirstName() +" " +formPerson.getLastName()}, null);
        isCreated=true;
        return "redirect:/person_create.html";
    }

}
