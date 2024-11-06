package com.example.personinfo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

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

    // Make a table for this since it's more than one value.
    @ElementCollection
    @CollectionTable(name = "Allergies", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "allergy")
    private List<String> allergies;

    // This is many to many since one person can have more than one medication and vise versa. This also creates the medication if it doesn't exist already
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "person_medication",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private List<Medication> medication;

    // Constructors
    public Person(long id, String firstName, String lastName, String address, String phoneNumber, int age, List<String> allergies, List<Medication> medication) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.allergies = allergies;
        this.medication = medication;
    }

    public Person(String firstName, String lastName, String address, String phoneNumber, int age, List<String> allergies, List<Medication> medication) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.allergies = allergies;
        this.medication = medication;
    }

    public Person(String firstName, String lastName, String address, String phoneNumber, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
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

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
    }

}
