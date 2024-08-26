package com.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

/*    @GetMapping("/")
    public String home(){
        return "index";
    }*/

    @GetMapping("/booking")
    public String booking(){
        return "booking";
    }

    @GetMapping("/event")
    public String event(){
        return "event";
    }

}
