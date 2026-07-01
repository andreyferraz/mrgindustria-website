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

    @GetMapping("/sistemas-pneumaticos")
    public String sistemasPneumaticos() {
        return "sistemas-pneumaticos";
    }

    @GetMapping("/lubrificacao-industrial")
    public String lubrificacaoIndustrial() {
        return "lubrificacao-industrial";
    }

    @GetMapping("/servicos-realizados")
    public String servicosRealizados() {
        return "servicos-realizados";
    }

    @GetMapping("/contato")
    public String contato() {
        return "contato";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
