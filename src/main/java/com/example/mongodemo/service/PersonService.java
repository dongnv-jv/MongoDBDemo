package com.example.mongodemo.service;

import com.example.mongodemo.document.Person;
import com.example.mongodemo.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {


    @Autowired
    private PersonRepo personRepo;

    public Person getPerson(int id) {
        Optional<Person> person = personRepo.findById(id);
        return person.orElseThrow();
    }

    public Person addPerson(Person person) {

        return personRepo.save(person);
    }
}
