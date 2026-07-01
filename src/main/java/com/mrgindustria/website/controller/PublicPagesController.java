package com.mrgindustria.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPagesController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/quem-somos")
    public String quemSomos() {
        return "quem-somos";
    }

    @GetMapping("/areas-atuacao")
    public String areasAtuacao() {
        return "areas-atuacao";
    }

    @GetMapping("/sistemas-hidraulicos")
    public String sistemasHidraulicos() {
        return "sistemas-hidraulicos";
    }

}
