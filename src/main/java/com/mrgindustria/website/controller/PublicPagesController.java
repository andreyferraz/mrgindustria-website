package com.mrgindustria.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPagesController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
