package com.synagogue.repository;

import com.synagogue.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findOneByFirstNameAndLastName(String firstName,String lastName);
}
