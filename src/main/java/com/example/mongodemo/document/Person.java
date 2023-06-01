package com.example.mongodemo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("person")
public class Person {
    @Id
    private int id;
//    @Indexed(unique = true)
    private String name;
//    @Indexed(unique = true)
    private int age;
//    @Indexed(unique = true)
    private String adress;
}
