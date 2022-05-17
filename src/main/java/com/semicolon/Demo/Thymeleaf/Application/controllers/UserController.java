package com.semicolon.Demo.Thymeleaf.Application.controllers;


import com.semicolon.Demo.Thymeleaf.Application.exception.ApplicationException;
import com.semicolon.Demo.Thymeleaf.Application.models.User;
import com.semicolon.Demo.Thymeleaf.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
 public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/sign-up")
    public String showSignUpPage(User user){
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid User user , BindingResult result, Model model){
        if(result.hasErrors()){
            return "add-user";
        }
        if(userRepository.findUserByEmail(user.getEmail()) != null){
            return "add-user";

        }
        model.addAttribute("user", user);
        userRepository.save(user);
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showAllUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("edit/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model){
       User user =  userRepository.findById(id).orElseThrow(()->new ApplicationException("invalid user"));
        model.addAttribute("user", user);
        return "update-form";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model){
        if(result.hasErrors()){
            user.setId(id);
            return "update-form";
        }
        userRepository.save(user);
        return "redirect:/index";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model){
        User user = userRepository.findById(id).orElseThrow(()-> new ApplicationException("user does not exist"));
        userRepository.delete(user);
        return "redirect:/index";
    }
}
