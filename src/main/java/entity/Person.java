package entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.WebApplicationException;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @Positive(message = "Age must be greater than zero")
    private int age;
    // Default constructor

    public Person() {
        // Default constructor is necessary for Hibernate
    }

    public Person(Integer id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new WebApplicationException("Age must be greater than zero",BAD_REQUEST);
        }
        this.age = age;
    }
}