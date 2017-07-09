package com.synagogue.controller;

import com.synagogue.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
public class PersonListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonListController.class);

    private final PersonService personService;

    @Inject
    public PersonListController(final PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/person_list.html")
    public ModelAndView getListPersonsView() {


        LOGGER.debug("Received request to get person list view");
       // ModelMap model = new ModelMap();
        //model.addAttribute("persons", personService.getList());
        return new ModelAndView("person_list");
    }

}
