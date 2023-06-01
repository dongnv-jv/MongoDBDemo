package com.example.mongodemo.repo;

import com.example.mongodemo.document.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PersonRepo extends MongoRepository<Person, Integer> {

    @Query("employee_management.person.find({\n" +
            "  'name': ?0,\n" +
            "  'age': ?1,\n" +
            "  'adress': ?2,\n" +
            "})")
    List<Person> findByCondition(String name,int age,String adress);

}
