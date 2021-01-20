package com.example.projetspring.controllers;

import com.example.projetspring.model.User;
import com.example.projetspring.service.CommandeService;
import com.example.projetspring.service.ShoppingCartService;
import com.example.projetspring.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommandeService commandeService;

    @RequestMapping("/cart/add/{pid}/{qte}")
    public RedirectView addproductToCart(@PathVariable("pid") Long idProd,
                                   @PathVariable("qte") Integer qte,
                                   @AuthenticationPrincipal Authentication authentication){

        System.out.println("produit a ajouter : "+idProd+"-"+qte);
        User user = userService.getCurrentlyLogedUser(authentication);
        Integer addedQte = cartService.addProduct(idProd,qte,user);
        System.out.println("produit ajouté avec succes");
        return new RedirectView("http://localhost:8080/catalogue");
    }

    @RequestMapping("/cart/update/{pid}/{qte}")
    public String updateQuantity(@PathVariable("pid") Long idProd,
                                   @PathVariable("qte") Integer qte,
                                   @AuthenticationPrincipal Authentication authentication){
        User user = userService.getCurrentlyLogedUser(authentication);
        float subTotal = cartService.updateQuantity(idProd,qte,user);
        return String.valueOf(subTotal);
    }

    @RequestMapping(value = "/cart/remove/{pid}")
    public RedirectView removeProductFromCart(@PathVariable("pid") Long idProd,
                                 @AuthenticationPrincipal Authentication authentication){
        User user = userService.getCurrentlyLogedUser(authentication);
        cartService.removeProduct(idProd,user);
        return new RedirectView("http://localhost:8080/cart");
    }

    @RequestMapping("cart/checkout")
    public RedirectView checkOut( @AuthenticationPrincipal Authentication authentication) {
        User user = userService.getCurrentlyLogedUser(authentication);
        float total = commandeService.addOrder(user);
        return new RedirectView("http://localhost:8080/paiment");
        //return "Votre commande a bien ete enregistré, votre total est : "+total+" mad.";
    }
}
