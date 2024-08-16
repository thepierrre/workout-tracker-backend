package com.example.gymapp.services;

import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void createRoleIfNotExistent(String name) {
        roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role(name);
            return roleRepository.save(role);
        });
    }
}
