package com.mrgindustria.website.controller;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mrgindustria.website.model.Areas;
import com.mrgindustria.website.model.Marcas;
import com.mrgindustria.website.service.AreasService;
import com.mrgindustria.website.service.MarcasService;

@Controller
public class PublicPagesController {

    private final AreasService areasService;
    private final MarcasService marcasService;

    public PublicPagesController(AreasService areasService, MarcasService marcasService) {
        this.areasService = areasService;
        this.marcasService = marcasService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Areas> areas = StreamSupport.stream(areasService.findAll().spliterator(), false).toList();
        List<Marcas> marcas = StreamSupport.stream(marcasService.findAll().spliterator(), false).toList();
        model.addAttribute("marcas", marcas);
        model.addAttribute("areas", areas);
        return "index";
    }

    @GetMapping("/quem-somos")
    public String quemSomos() {
        return "quem-somos";
    }

    @GetMapping("/areas-atuacao")
    public String areasAtuacao(Model model) {
        List<Areas> areas = StreamSupport.stream(areasService.findAll().spliterator(), false).toList();
        model.addAttribute("areas", areas);
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
