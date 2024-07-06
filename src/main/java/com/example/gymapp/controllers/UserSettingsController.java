package com.example.gymapp.controllers;

import com.example.gymapp.services.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-settings")
public class UserSettingsController {

    @Autowired
    UserSettingsService userSettingsService;

}
