package com.example.firestation.controller;

import com.example.firestation.model.FireStation;
import com.example.firestation.repository.FireStationRepository;
import com.example.firestation.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Having the mapping here makes it the mapping for all functions. In a case where there would be 2 or more of the same mapping, I could specify something like "/map" for the method, and, as a result, the entire path would be "/firestation/map"
@RestController
@RequestMapping("/firestation")
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;
    @Autowired
    private FireStationRepository fireStationRepository;

    // Initialize the service so we can use it
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    // Here I use a Map, since the keys can't be the same which works really nicely for this
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPeopleByStation(@RequestParam("stationNumber") int stationNumber) {
        Map<String, Object> response = fireStationService.getPeopleByStation(stationNumber);
        // Return not found if response is empty, return ok if the response is there.
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // Save to db, and then return HttpStatus to let know it worked
    @PostMapping
    public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation fireStation) {
        FireStation savedFireStation = fireStationRepository.save(fireStation);
        return new ResponseEntity<>(savedFireStation, HttpStatus.CREATED);
    }

    // Update a fire station by finding it by id and setting an old addresses values
    @PutMapping("/{id}")
    public ResponseEntity<FireStation> updateFireStation(@PathVariable Long id, @RequestBody FireStation fireStationDetails) {
        return fireStationRepository.findById(id)
                .map(fireStation -> {
                    fireStation.setStationNumber(fireStationDetails.getStationNumber());
                    fireStation.setAddress(fireStationDetails.getAddress());
                    FireStation updatedFireStation = fireStationRepository.save(fireStation);
                    return ResponseEntity.ok(updatedFireStation);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a fire station should it exist
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFireStation(@PathVariable Long id) {
        return fireStationRepository.findById(id)
                .map(fireStation -> {
                    fireStationRepository.delete(fireStation);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
