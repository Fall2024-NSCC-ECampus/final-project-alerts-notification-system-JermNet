package com.example.childalert.controller;

import com.example.childalert.model.Address;
import com.example.childalert.model.Person;
import com.example.childalert.repository.AddressRepository;
import com.example.childalert.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is the same as address where this mapping is for all methods unless otherwise specified
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    // Get all the people
    @GetMapping
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = personRepository.findAll();
        return ResponseEntity.ok(people);
    }

    // Use this to add a person. First get the address (if it exists), set the address to the person, add the person to the address, save the person to the db and then return status created.
    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Address address = addressRepository.findById(person.getAddressId()).orElseThrow(() -> new RuntimeException("Address not found"));
        person.setAddress(address);
        address.getPeople().add(person);
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
                    person.setAge(personDetails.getAge());
                    person.setPhoneNumber(personDetails.getPhoneNumber());
                    person.setAddressId(personDetails.getAddressId());
                    Address address = addressRepository.findById(personDetails.getAddressId())
                            .orElseThrow(() -> new RuntimeException("Address not found"));
                    person.setAddress(address);
                    Address existingAddress = person.getAddress();
                    // Remove from old address, add to new
                    existingAddress.getPeople().remove(person);
                    existingAddress.getPeople().add(person);
                    Person updatedPerson = personRepository.save(person);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Same as address but removes the person from the address as well
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    Address address = person.getAddress();
                    address.getPeople().remove(person);
                    personRepository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
