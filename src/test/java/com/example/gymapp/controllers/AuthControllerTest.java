package com.example.gymapp.controllers;

import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.repositories.RoleRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerTest {

    private MockMvc mvc;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void setUpUserRole() {
        if (!roleRepository.existsByName("USER")) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }
    }


    @Test
    void testRegister() throws Exception {


    }

}