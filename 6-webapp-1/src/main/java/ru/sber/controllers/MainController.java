package ru.sber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.services.CakeService;


@Controller
public class MainController {
    private final CakeService cakeService;

    public MainController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @GetMapping("/home")
    public String viewProducts(Model model) {
        var cakes = cakeService.findAll();
        model.addAttribute("cakes", cakes);
        return "page.html";
    }

    @PostMapping("/home")
    public String findCake(
            @RequestParam  String cake_name,
            Model model
    ) {
        var cakes = cakeService.findCake(cake_name);
        model.addAttribute("cakes", cakes);

        return "page.html";
    }

}
