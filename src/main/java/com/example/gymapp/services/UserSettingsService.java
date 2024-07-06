package com.example.gymapp.services;

import com.example.gymapp.repositories.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {

    @Autowired
    UserSettingsRepository userSettingsRepository;
}
