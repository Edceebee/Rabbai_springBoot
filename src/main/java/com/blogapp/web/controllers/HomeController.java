package com.blogapp.web.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class HomeController {

    @GetMapping("/")

    public @ResponseBody String showWelcome(){
        return "<h1> Welcome to Pentax!!! </h1>";
    }
}
