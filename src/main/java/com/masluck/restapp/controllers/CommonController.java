package com.masluck.restapp.controllers;

import com.masluck.restapp.entities.User;
import com.masluck.restapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class CommonController {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        return "index";
    }

    @GetMapping("/showuser")
    public String onlyUserPage(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        return "showuser";
    }

    @GetMapping("/showadmin")
    public String userPage(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        return "showadmin";
    }

}
