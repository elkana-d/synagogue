package com.synagogue.controller;

import java.util.List;
import java.util.Map;

import com.synagogue.domain.Person;
import com.synagogue.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//   import com.kendoui.spring.models.Product;
//import com.kendoui.spring.models.ProductDao;

@Controller("grid-editing-inline-controller")
@RequestMapping(value="/grid/")
public class EditingInlineController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/editing-inline", method = RequestMethod.GET)
    public String index() {
        return "grid/editing-inline";
    }

    @RequestMapping(value = "/editing-inline/read", method = RequestMethod.POST)
    public @ResponseBody List<Person> read() {
        return personService.getList();
    }

    @RequestMapping(value = "/editing-inline/update", method = RequestMethod.POST)
    public @ResponseBody Person update(@RequestBody Map<String, Object> model) {
        Person target = new Person();

        target.setId((int) model.get("id"));
        target.setFirstName((String)model.get("firstName"));
        target.setLastName((String)model.get("lastName"));
        target.setPrice((int)model.get("price"));
        target.setNote((String)model.get("note"));
        //target.setUnitsInStock((int)model.get("unitsInStock"));
        //target.setDiscontinued((boolean)model.get("discontinued"));
        //target.setCategoryId((int)model.get("categoryId"));

        personService.save(target);

        return target;
    }

    @RequestMapping(value = "/editing-inline/create", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> create(@RequestBody Map<String, Object> model) {
        Person personExist = personService.findOneByFirstNameAndLastName((String)model.get("firstName"),(String)model.get("lastName"));
        if(personExist != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        Person target = new Person();
        target.setFirstName((String)model.get("firstName"));
        target.setLastName((String)model.get("lastName"));
        target.setPrice((int)model.get("price"));
        target.setNote((String)model.get("note"));

        personService.save(target);
        return new ResponseEntity<>(target,HttpStatus.OK);
    }

    @RequestMapping(value = "/editing-inline/destroy", method = RequestMethod.POST)
    public @ResponseBody Person destroy(@RequestBody Map<String, Object> model) {
        Person target = new Person();

        target.setId((int)model.get("id"));

        personService.delete(target);

        return target;
    }
}