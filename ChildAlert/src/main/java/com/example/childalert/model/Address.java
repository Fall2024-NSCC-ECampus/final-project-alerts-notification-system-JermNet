package com.example.childalert.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="AddressTable")
public class Address {

    // Address ID
    @Id
    @GeneratedValue
    private long id;

    // String with the address. This is all a separate table so it works better in a database rather than each person having their own string address in which there would be copied addresses.
    private String address;

    // One to many since a person should only have one address, but an address can have many people. Json thing is to avoid json issues
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Person> people;

    // Constructors
    public Address(long id, String address, List<Person> people) {
        this.id = id;
        this.address = address;
        this.people = people;
    }

    public Address(String address, List<Person> people) {
        this.address = address;
        this.people = people;
    }

    public Address(long id, String address) {
        this.id = id;
        this.address = address;
    }

    public Address() {

    }

    // Setters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
