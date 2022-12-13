package com.udemy.sbforswe.springbootforsoftwareengineers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.udemy.sbforswe.springbootforsoftwareengineers.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

@SuppressWarnings("unused")
public class User {
    private final UUID userUUID;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final int age;
    private final String email;

    public User(@JsonProperty("userUUID") UUID userUUID, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("gender") Gender gender, @JsonProperty("age") int age, @JsonProperty("email") String email) {
        this.userUUID = userUUID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    @JsonProperty("id")
    public UUID getUserUUID() {
        return userUUID;
    }

    @JsonIgnore
    public String getFirstName() {
        return firstName;
    }

    @JsonIgnore
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

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getYearOfBirth(){
        return  LocalDate.now().minusYears(age).getYear();
    }

    public static User newUser(UUID userUUID, User user){
        return new User(userUUID, user.getFirstName(), user.getLastName(), user.gender, user.getAge(), user.getEmail());
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
