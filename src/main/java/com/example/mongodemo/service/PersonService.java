package com.example.mongodemo.service;

import com.example.mongodemo.document.Person;
import com.example.mongodemo.repo.PersonRepo;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private PersonRepo personRepo;

    public Person getPerson(int id) {
        Optional<Person> person = personRepo.findById(id);
        return person.orElseThrow();
    }

    public List<Person> addPerson() {

        List<Person> personList = new ArrayList<Person>();

        for (int i = 0; i < 100; i++) {
            Person p = new Person();
            p.setId(i);
            p.setName("Tên " + i);
            p.setAge(20 + i);
            p.setAdress("Adress " + i);
            personList.add(p);

        }
        return personRepo.saveAll(personList);
    }

    public List<Person> getListPerson(String name, Integer age, String adress) {

        Criteria criteria1 = new Criteria();

        if (name != null) {
            criteria1.and("name").is(name);
        }
        if (age != null) {
            criteria1.and("age").is(age);
        }
        if (adress != null) {
            criteria1.and("adress").is(adress);
        }
        Query query = new Query(criteria1);

        return mongoOperations.find(query, Person.class);
    }
}
