package com.example.firestation.controller;

import com.example.firestation.model.FireStation;
import com.example.firestation.model.Person;
import com.example.firestation.repository.FireStationRepository;
import com.example.firestation.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is the same as the fire station where this mapping is for all methods unless otherwise specified
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    // Get all the people
    @GetMapping
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = personRepository.findAll();
        return ResponseEntity.ok(people);
    }

    // Use this to add a person. First get the fire station (if it exists), set the fire station to the person, add the person to the fire station, save the person to the db and then return status created.
    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        FireStation fireStation = fireStationRepository.findById(person.getFireStationId()).orElseThrow(() -> new RuntimeException("FireStation not found"));
        person.setFireStation(fireStation);
        fireStation.getPeople().add(person);
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    // Update a person
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setFirstName(personDetails.getFirstName());
                    person.setLastName(personDetails.getLastName());
                    person.setAddress(personDetails.getAddress());
                    person.setPhoneNumber(personDetails.getPhoneNumber());
                    person.setAge(personDetails.getAge());
                    person.setFireStationId(personDetails.getFireStationId());
                    FireStation fireStation = fireStationRepository.findById(personDetails.getFireStationId())
                            .orElseThrow(() -> new RuntimeException("Fire station not found"));
                    person.setFireStation(fireStation);
                    FireStation existingFireStation = person.getFireStation();
                    // Remove from old fire station, add to new
                    existingFireStation.getPeople().remove(person);
                    existingFireStation.getPeople().add(person);
                    Person updatedPerson = personRepository.save(person);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Same as fire station but removes the person from the fire station as well
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    FireStation fireStation = person.getFireStation();
                    fireStation.getPeople().remove(person);
                    personRepository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

