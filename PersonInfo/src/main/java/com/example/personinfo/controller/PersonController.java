package com.example.personinfo.controller;

import com.example.personinfo.model.Medication;
import com.example.personinfo.model.Person;
import com.example.personinfo.repository.MedicationRepository;
import com.example.personinfo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is the same as the medication where this mapping is for all methods unless otherwise specified
@RestController
@RequestMapping("/personinfo")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    // Get all the people by first and last name
    @GetMapping
    public ResponseEntity<List<Person>> getPeopleByName(@RequestParam String firstName, @RequestParam String lastName) {
        List<Person> people = personRepository.findByFirstNameAndLastName(firstName, lastName);

        if (people.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(people);
        }
    }

    // Use this to add a person. Medication is automatically created if it doesn't already exist (that's done in the person model)
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }


    // Update a person
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePersonWithMedications(@PathVariable Long id, @RequestBody Person personDetails) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setFirstName(personDetails.getFirstName());
                    person.setLastName(personDetails.getLastName());
                    person.setAddress(personDetails.getAddress());
                    person.setPhoneNumber(personDetails.getPhoneNumber());
                    person.setAge(personDetails.getAge());

                    // Clear previous medications and then loop through adding the ones in the request
                    if (personDetails.getMedication() != null) {
                        // Clear current medications
                        person.getMedication().clear();

                        // Loop through the medications from the request and set them
                        for (Medication medication : personDetails.getMedication()) {
                            Medication existingMedication = medicationRepository.findById(medication.getId())
                                    .orElseThrow(() -> new RuntimeException("Medication not found"));
                            person.getMedication().add(existingMedication);
                        }
                    }

                    // Save the person
                    Person updatedPerson = personRepository.save(person);
                    return ResponseEntity.ok(updatedPerson);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a person, doesn't have to delete medications since they may be used for other people
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
