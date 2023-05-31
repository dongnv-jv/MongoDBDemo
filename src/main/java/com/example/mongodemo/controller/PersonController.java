package com.example.mongodemo.controller;

import com.example.mongodemo.document.Person;
import com.example.mongodemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public ResponseEntity<?> getPerson(@RequestParam("id") int id){
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> getPerson(@RequestBody Person person){
        return ResponseEntity.ok(personService.addPerson(person));
    }
}
