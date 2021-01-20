package com.example.projetspring.controllers;

import javax.servlet.http.HttpServletRequest;

import com.example.projetspring.config.PaypalPaymentIntent;
import com.example.projetspring.config.PaypalPaymentMethod;
import com.example.projetspring.config.URLUtils;
import com.example.projetspring.model.Commande;
import com.example.projetspring.repository.CommandeRepository;
import com.example.projetspring.service.CommandeService;
import com.example.projetspring.service.PaypalService;
import com.example.projetspring.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {

    public static final String PAYPAL_SUCCESS_URL = "pay/success";
    public static final String PAYPAL_CANCEL_URL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private CommandeRepository commandeRepository;

    @RequestMapping(value = "/paiment",method = RequestMethod.GET)
    public String ShowCommande(Model model,@AuthenticationPrincipal Authentication authentication){
        Commande commande = commandeRepository.findByUser(userService.getCurrentlyLogedUser(authentication));
        model.addAttribute(commande);
        return "resume_commande";
    }

    @RequestMapping(method = RequestMethod.POST, value = "pay")
    public String pay(HttpServletRequest request,@AuthenticationPrincipal Authentication authentication){
        Commande commande = commandeRepository.findByUser(userService.getCurrentlyLogedUser(authentication));
        double prix = Double.valueOf(Float.valueOf(commande.getTotal()).toString());
        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
        try {
            Payment payment = paypalService.createPayment(prix
                    ,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
    public String cancelPay(){
        return "cancel";
    }

    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/index";
    }

}
