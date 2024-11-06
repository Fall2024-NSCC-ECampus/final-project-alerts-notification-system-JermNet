package com.example.personinfo.controller;

import com.example.personinfo.model.Medication;
import com.example.personinfo.model.Person;
import com.example.personinfo.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Having the mapping here makes it the mapping for all functions. In a case where there would be 2 or more of the same mapping, I could specify something like "/map" for the method, and, as a result, the entire path would be "/medication/map"
@RestController
@RequestMapping("/medication")
public class MedicationController {
    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping
    public ResponseEntity<List<Medication>> getAllMedication() {
        List<Medication> response = medicationRepository.findAll();
        // Return not found if response is empty, return ok if the response is there
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // Save to db, and then return HttpStatus to let know it worked
    @PostMapping
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication) {
        Medication savedMedication = medicationRepository.save(medication);
        return new ResponseEntity<>(savedMedication, HttpStatus.CREATED);
    }

    // Update a medication, also removing it from a person since it's a new medication
    @PutMapping("/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable Long id, @RequestBody Medication medicationDetails) {
        return medicationRepository.findById(id)
                .map(medication -> {
                    for (Person person : medication.getPeople()) {
                        person.getMedication().remove(medication);
                    }
                    medication.setName(medicationDetails.getName());
                    medication.setDosage(medicationDetails.getDosage());
                    Medication updatedMedication = medicationRepository.save(medication);
                    return ResponseEntity.ok(updatedMedication);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a medication should it exist, also deleting it from a person
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedication(@PathVariable Long id) {
        return medicationRepository.findById(id)
                .map(medication -> {
                    for (Person person : medication.getPeople()) {
                        person.getMedication().remove(medication);
                    }
                    medicationRepository.delete(medication);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
