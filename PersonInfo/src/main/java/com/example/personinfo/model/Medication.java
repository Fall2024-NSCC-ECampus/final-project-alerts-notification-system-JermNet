package com.example.personinfo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="MedicationTable")
public class Medication {

    // Medication ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Name and dosage. Dosage is a string so the amount in number and measurement can be specified.
    private String name;
    private String dosage;

    // Medication is many to many since one person can have more than one medication and vise versa. Json thing is to avoid json issues
    @ManyToMany(mappedBy = "medication")
    @JsonIgnore
    private List<Person> people;

    // Constructors
    public Medication(long id, String name, String dosage, List<Person> people) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.people = people;
    }

    public Medication(String name, String dosage, List<Person> people) {
        this.name = name;
        this.dosage = dosage;
        this.people = people;
    }

    public Medication(long id, String name, String dosage) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
    }

    public Medication() {

    }

    // Setters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}

