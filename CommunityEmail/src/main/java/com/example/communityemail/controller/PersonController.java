package com.example.communityemail.controller;

import com.example.communityemail.model.City;
import com.example.communityemail.model.Person;
import com.example.communityemail.repository.CityRepository;
import com.example.communityemail.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is the same as the fire station where this mapping is for all methods unless otherwise specified
@RestController
@RequestMapping("/communityEmail")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CityRepository cityRepository;

    // Get all the people by the name of the city they're attached to.
    @GetMapping
    public ResponseEntity<List<Person>> getPeopleByCity(@RequestParam String city) {
        List<Person> people = personRepository.findByCityName(city);

        if (people.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(people);
        }
    }

    // Use this to add a person. First get the city (if it exists), set the city to the person, add the person to the city, save the person to the db and then return status created.
    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        City city = cityRepository.findById(person.getCityId()).orElseThrow(() -> new RuntimeException("City not found"));
        person.setCity(city);
        city.getPeople().add(person);
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
                    person.setEmail(personDetails.getEmail());
                    person.setAge(personDetails.getAge());
                    person.setCityId(personDetails.getCityId());
                    City city = cityRepository.findById(personDetails.getCityId())
                            .orElseThrow(() -> new RuntimeException("City not found"));
                    person.setCity(city);
                    City existingCity = person.getCity();
                    // Remove from old city, add to new
                    existingCity.getPeople().remove(person);
                    existingCity.getPeople().add(person);
                    Person updatedPerson = personRepository.save(person);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Same as city but removes the person from the city as well
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    City city = person.getCity();
                    city.getPeople().remove(person);
                    personRepository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
