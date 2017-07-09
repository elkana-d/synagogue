package com.synagogue.service;


import com.synagogue.domain.Person;

import java.util.List;

public interface PersonService {

    void delete(Person person);

    Person save(Person person);

    List<Person> getList();

    Person findOneByFirstNameAndLastName(String firstName,String lastName);

}
