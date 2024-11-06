package com.example.firestation.model;

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
    private int age;

    // Same as FireStation, this is not ManyToMany since a person in an area would most likely only be served by one fire station. Json thing is to avoid json issues
    @ManyToOne
    @JoinColumn(name = "fireStation_id")
    @JsonBackReference
    private FireStation fireStation;

    // This means that this should not persist, using this so I can check if a fire station is attached to a person
    @Transient
    private long fireStationId;

    // Constructors
    public Person(long id, String firstName, String lastName, String address, String phoneNumber, int age, FireStation fireStation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.fireStation = fireStation;
    }

    public Person(String firstName, String lastName, String address, String phoneNumber, int age, FireStation fireStation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.fireStation = fireStation;
    }

    public Person(String firstName, String lastName, String address, String phoneNumber, int age, long fireStationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.fireStationId = fireStationId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public FireStation getFireStation() {
        return fireStation;
    }

    public void setFireStation(FireStation fireStation) {
        this.fireStation = fireStation;
    }

    public long getFireStationId() {
        return fireStationId;
    }

    public void setFireStationId(long fireStationId) {
        this.fireStationId = fireStationId;
    }

}
