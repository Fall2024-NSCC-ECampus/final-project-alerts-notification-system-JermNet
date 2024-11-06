package com.example.communityemail.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="CityTable")
public class City {

    // City ID
    @Id
    @GeneratedValue
    private long id;

    // Things for a city. Population isn't the count of how many people are in the people list, it's a more general number. Like how in real life where a population isn't perfectly exact.
    private int population;
    private String name;
    private String areaCode;

    // Realistically, one person can't be in more city than one at a time, so this makes the most sense. Cascade and orphanRemoval are to prevent db issues when a city is deleted but a person is not.
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Person> people;

    // Constructors
    public City(long id, int population, String name, String areaCode, List<Person> people) {
        this.id = id;
        this.population = population;
        this.name = name;
        this.areaCode = areaCode;
        this.people = people;
    }

    public City(int population, String name, String areaCode, List<Person> people) {
        this.population = population;
        this.name = name;
        this.areaCode = areaCode;
        this.people = people;
    }

    public City(long id, int population, String name, String areaCode) {
        this.id = id;
        this.population = population;
        this.name = name;
        this.areaCode = areaCode;
    }

    public City() {

    }

    // Setters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}

