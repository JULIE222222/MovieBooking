package com.cinema.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class paymentController {

     @GetMapping ("/paid")
        public String payEnd (){

        return "/payment/paid";
        }
}
