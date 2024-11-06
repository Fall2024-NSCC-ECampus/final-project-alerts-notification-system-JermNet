package com.example.childalert;

import com.example.childalert.controller.PersonController;
import com.example.childalert.model.Address;
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
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Despite saying they're not being used, I do still need these
    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonController personController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addAddress_SuccessfulCreation() throws Exception {
        Address address = new Address(1,"123 Main St");
        String addressJson = objectMapper.writeValueAsString(address);

        mockMvc.perform(post("/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addressJson))
                .andExpect(status().is2xxSuccessful());
    }
}
