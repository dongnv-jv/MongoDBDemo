package com.example.mongodemo.service;

import com.example.mongodemo.document.Person;
import com.example.mongodemo.repo.PersonRepo;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

//        for (int i = 0; i < 10; i++) {
//            Person p = new Person();
//            p.setId(i+150);
//            p.setName(i%2==0 ? "Đồng":"Cảnh");
//            p.setAge(20 );
//            p.setAdress(i%2==0 ? "Hà Nội":"Sài Gòn");
//            personList.add(p);
//
//        }
        Person p = new Person();
        p.setId(200);
        p.setName("Nam");
        p.setAge(20);
        p.setAdress("Nghệ An");
        Person p1 = new Person();
        p1.setId(202);
        p1.setName("Nam");
        p1.setAge(20);
        p1.setAdress("Nghệ An");

        Person p2 = new Person();
        p2.setId(201);
        p2.setName("Hoa");
        p2.setAge(20);
        p2.setAdress("Đà Nẵng");
        Person p3 = new Person();
        p3.setId(203);
        p3.setName("Hoa");
        p3.setAge(20);
        p3.setAdress("Đà Nẵng");


        personList.add(p);
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        return personRepo.saveAll(personList);
    }

    public List<Person> getListPerson(String name, Integer age, String adress) {
        String batdauKophanbiet = "(?i)" + name;
        String ketThucLa = "$";
        String batDauBang = "^";
        String coChua = "";

        List<Person> personList = new ArrayList<Person>();
        Criteria criteria1 = new Criteria();

        if (name != null) {
            criteria1.and("name").regex(name);
        }
        if (age != null) {
            criteria1.and("age").is(age);
        }
        if (adress != null) {
            criteria1.and("adress").is(adress);
        }
        Query query = new Query(criteria1);

        personList = mongoOperations.find(query, Person.class);

//        List<Person> personList1 = personList.stream().filter(distinctByKey()).collect(Collectors.toList());

        List<Person> filteredList = personList.stream().filter(distinctByKey(p -> {
            Map<String, Person> map = new HashMap<>();
            if (p.getId() % 2 == 0) {
                String key = p.getName() + "-" + p.getAge();
                return map.putIfAbsent(key, p) == null;
            } else {
                String key = p.getAdress() + "-" + p.getAge();
                return map.putIfAbsent(key, p) == null;
            }
        })).toList();


        filteredList.sort(Comparator.comparing((Person g) -> {
            if (g.getId() % 2 == 0) {
                return g.getAdress();
            } else return g.getName();
        }).thenComparing((Person t) -> {
            if (t.getId() % 2 != 0) {
                return t.getName();
            } else return t.getAdress();
        }));
        return filteredList;
    }

    public static <T> Predicate<T> distinctByKey(Predicate<T> predicate) {
        return predicate;
    }

    public static Predicate<Person> distinctByKey() {
        Map<String, Person> map = new HashMap<>();
        return person -> {
            if (person.getId() % 2 == 0) {
                String key = person.getName() + "-" + person.getAge();
                return map.putIfAbsent(key, person) == null;
            } else {
                String key = person.getAdress() + "-" + person.getAge();
                return map.putIfAbsent(key, person) == null;
            }
        };
    }

    public static <T> Predicate<T> distinctByKey1(Function<? super T, Object> keyExtractor, Function<? super T, Object> condition, Predicate<? super T> check) {
        Map<Object, Boolean> seen = new HashMap<>();
        return t -> {
            Object key = keyExtractor.apply(t);
            Object conditionValue = condition.apply(t);

            if (check.test(t) && seen.putIfAbsent(key, Boolean.TRUE) == null) {
                return true;
            } else {
                return false;
            }
        };
    }

    public void delete() {
        personRepo.deleteAll();
    }


}
