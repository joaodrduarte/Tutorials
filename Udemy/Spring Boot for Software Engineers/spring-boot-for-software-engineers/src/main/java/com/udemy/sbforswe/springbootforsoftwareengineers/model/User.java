package com.udemy.sbforswe.springbootforsoftwareengineers.model;

import com.udemy.sbforswe.springbootforsoftwareengineers.enums.Gender;

import java.util.UUID;

public class User {
    private final UUID userUUID;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final int age;
    private final String email;

    public User(UUID userUUID, String firstName, String lastName, Gender gender, int age, String email) {
        this.userUUID = userUUID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userUUID=" + userUUID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
