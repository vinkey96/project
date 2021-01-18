package com.example.projetspring.controllers;

import com.example.projetspring.model.CartItem;
import com.example.projetspring.model.User;
import com.example.projetspring.service.ShoppingCartService;
import com.example.projetspring.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/cart")
public String ShowShoppingCart(Model model, @AuthenticationPrincipal Authentication authentication){
        User user = userService.getCurrentlyLogedUser(authentication);
        List<CartItem> cartItems = cartService.listCartItems(user);
        model.addAttribute("cartItems",cartItems);
        //model.addAttribute("pageTitle","Shopping cart");

        return "shopping_cart";
    }
}
