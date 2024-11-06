package com.example.childalert.controller;

import com.example.childalert.model.Address;
import com.example.childalert.repository.AddressRepository;
import com.example.childalert.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Having the mapping here makes it the mapping for all functions. In a case where there would be 2 or more of the same mapping, I could specify something like "/map" for the method, and, as a result, the entire path would be "/address/map"
@RestController
@RequestMapping("/childAlert")
public class AddressController {
    @Autowired
    private PersonService personService;
    @Autowired
    private AddressRepository addressRepository;

    // Initialize the service so we can use it
    public AddressController(PersonService personService) {
        this.personService = personService;
    }

    // Here I use a Map, since the keys can't be the same which works really nicely for this
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPeopleByAddress(@RequestParam("address") String address) {
        Map<String, Object> response = personService.getPeopleByAddress(address);
        // Return not found if response is empty, return ok if the response is there.
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // Save to db, and then return HttpStatus to let know it worked
    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        Address savedaddress = addressRepository.save(address);
        return new ResponseEntity<>(savedaddress, HttpStatus.CREATED);
    }

    // Update an address by finding it by id and setting an old addresses values
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        return addressRepository.findById(id)
                .map(address -> {
                    address.setAddress(addressDetails.getAddress());
                    Address updatedAddress = addressRepository.save(address);
                    return ResponseEntity.ok(updatedAddress);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an address by using its id, return different responses depending on if the id exists or not.
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable Long id) {
        return addressRepository.findById(id)
                .map(address -> {
                    addressRepository.delete(address);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}