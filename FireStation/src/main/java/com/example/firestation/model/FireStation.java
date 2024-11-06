package com.example.firestation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="FireStationTable")
public class FireStation {

    // Fire station ID
    @Id
    @GeneratedValue
    private long id;

    // Station number and address
    private int stationNumber;
    private String address;

    // Instead of having this be many to many, this is one to many since a fire station can serve many people, but, realistically, a person wouldn't be served by many different fire stations if they're living in one area. Json thing is to avoid json issues
    @OneToMany(mappedBy = "fireStation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Person> people;

    // Constructors
    public FireStation(long id, int stationNumber, String address, List<Person> people) {
        this.id = id;
        this.stationNumber = stationNumber;
        this.address = address;
        this.people = people;
    }
    
    public FireStation(int stationNumber, String address, List<Person> people) {
        this.stationNumber = stationNumber;
        this.address = address;
        this.people = people;
    }

    public FireStation(long id, int stationNumber, String address) {
        this.id = id;
        this.stationNumber = stationNumber;
        this.address = address;
    }

    public FireStation() {

    }

    // Setters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
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
