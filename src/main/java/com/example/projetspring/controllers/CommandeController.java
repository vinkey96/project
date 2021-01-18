package com.example.projetspring.controllers;

import com.example.projetspring.model.*;
import com.example.projetspring.repository.CommandeRepository;
import com.example.projetspring.service.CommandeService;
import com.example.projetspring.service.ShoppingCartService;
import com.example.projetspring.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/commande")
    public String ShowOrders(Model model){
        List<Commande> orders = commandeService.listOrders();
        model.addAttribute("orders",orders);
        return "commande_admin";
    }
    @RequestMapping(value = "/saveCommande", method = RequestMethod.POST)
    public String saveCommande(@ModelAttribute("commande") Commande commande) {
        commandeService.save(commande);

        return "redirect:/prod";
    }

    @RequestMapping("/edit/commande/{id}")
    public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
        Commande order = commandeRepository.findById(id).get();
        List<User> listUsers = userService.listAll();
        ModelAndView mav = new ModelAndView("edit_order");
        mav.addObject("listUser",listUsers);
        mav.addObject("order", order);
        return mav;
    }

    @RequestMapping("/delete/commande/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        commandeService.delete(id);

        return "redirect:/order";
    }
}
