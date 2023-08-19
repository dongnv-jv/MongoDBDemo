package com.example.mongodemo.controller;

import com.example.mongodemo.document.Person;
import com.example.mongodemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public ResponseEntity<?> getPerson(@RequestParam("id") int id) {
        return ResponseEntity.ok(personService.getPerson(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> getPerson() {
        return ResponseEntity.ok(personService.addPerson());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPerson(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "adress", required = false) String adress) {

        List<Person> personList = personService.getListPerson(name, age, adress);

        return ResponseEntity.ok(personList);
    }

    @GetMapping("/del")
    public ResponseEntity<?> del() {
        personService.delete();
        return ResponseEntity.ok("Đã xóa");
    }
    @GetMapping("/call")
    public ResponseEntity<?> call() {
        return ResponseEntity.ok("Đã call");
    }
}
