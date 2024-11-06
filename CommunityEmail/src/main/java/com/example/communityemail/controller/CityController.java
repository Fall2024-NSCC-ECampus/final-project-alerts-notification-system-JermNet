package com.example.communityemail.controller;

import com.example.communityemail.model.City;
import com.example.communityemail.model.Person;
import com.example.communityemail.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Having the mapping here makes it the mapping for all functions. In a case where there would be 2 or more of the same mapping, I could specify something like "/map" for the method, and, as a result, the entire path would be "/city/map"
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        List<City> response = cityRepository.findAll();
        // Return not found if response is empty, return ok if the response is there
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // Save to db, and then return HttpStatus to let know it worked
    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody City city) {
        City savedCity = cityRepository.save(city);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }

    // Update a city by finding it by id and setting an old cities values
    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setPopulation(cityDetails.getPopulation());
                    city.setName(cityDetails.getName());
                    city.setAreaCode(cityDetails.getAreaCode());
                    City updatedCity = cityRepository.save(city);
                    return ResponseEntity.ok(updatedCity);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // Delete a city should it exist
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCity(@PathVariable Long id) {
        return cityRepository.findById(id)
                .map(city -> {
                    cityRepository.delete(city);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}

