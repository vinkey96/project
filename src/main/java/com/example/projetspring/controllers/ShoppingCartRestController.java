package com.example.projetspring.controllers;

import com.example.projetspring.model.User;
import com.example.projetspring.service.ShoppingCartService;
import com.example.projetspring.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/cart/add/{pid}/{qte}")
    public String addproductToCart(@PathVariable("pid") Long idProd,
                                   @PathVariable("qte") Integer qte,
                                   @AuthenticationPrincipal Authentication authentication){

        System.out.println("produit a ajouter : "+idProd+"-"+qte);
        User user = userService.getCurrentlyLogedUser(authentication);
        Integer addedQte = cartService.addProduct(idProd,qte,user);
        System.out.println("produit ajouté avec succes");
        return addedQte + " produits ont ete ajoute au panier.";
    }

    @PostMapping("/cart/update/{pid}/{qte}")
    public String updateQuantity(@PathVariable("pid") Long idProd,
                                   @PathVariable("qte") Integer qte,
                                   @AuthenticationPrincipal Authentication authentication){
        User user = userService.getCurrentlyLogedUser(authentication);
        float subTotal = cartService.updateQuantity(idProd,qte,user);
        return String.valueOf(subTotal);
    }

    @RequestMapping(value = "/cart/remove/{pid}")
    public String removeProductFromCart(@PathVariable("pid") Long idProd,
                                 @AuthenticationPrincipal Authentication authentication){
        User user = userService.getCurrentlyLogedUser(authentication);
        cartService.removeProduct(idProd,user);
        return "Le produit a été retiré";
    }
}
