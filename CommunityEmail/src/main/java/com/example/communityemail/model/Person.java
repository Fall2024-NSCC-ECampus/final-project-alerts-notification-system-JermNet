package com.example.communityemail.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="PersonTable")
public class Person {

    // Person ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private int age;

    // A city can have many people but only one person can be in a city at a time so this makes perfect sense
    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonBackReference
    private City city;

    // This means that this should not persist, using this so I can check if a fire station is attached to a person
    @Transient
    private long cityId;

    // Constructors
    public Person(long id, String firstName, String lastName, String address, String phoneNumber, String email, int age, City city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.city = city;
    }

    public Person(String firstName, String lastName, String address, String phoneNumber, String email, int age, City city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.city = city;
    }

    public Person(String firstName, String lastName, String address, String phoneNumber, String email, int age, long cityId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.cityId = cityId;
    }

    public Person() {

    }

    // Setters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

}

