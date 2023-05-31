package com.example.mongodemo.repo;

import com.example.mongodemo.document.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepo extends MongoRepository<Person, Integer> {
}
