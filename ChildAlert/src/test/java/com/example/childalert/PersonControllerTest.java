package com.example.childalert;

import com.example.childalert.controller.PersonController;
import com.example.childalert.model.Address;
import com.example.childalert.model.Person;
import com.example.childalert.repository.AddressRepository;
import com.example.childalert.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This tests sending valid data using Mockito. This test is successful, so the test works.
@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonController personController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addPerson_SuccessfulCreation() throws Exception {
        Address address = new Address(1, "123 Main St");
        Person person = new Person("John", "Doe", "123-456-7890", 30, 1);
        String personJson = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personJson))
                .andExpect(status().is2xxSuccessful());
    }
}