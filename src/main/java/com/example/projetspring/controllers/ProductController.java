package com.example.projetspring.controllers;


import com.example.projetspring.model.Category;
import com.example.projetspring.model.Product;
import com.example.projetspring.repository.CategoryRepository;
import com.example.projetspring.repository.ProductRepository;
import com.example.projetspring.service.CategoryService;
import com.example.projetspring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService service;
    @Autowired
    private CategoryService repo;
    @Autowired
    private ProductRepository prodRepo;


    @RequestMapping("/catalogue")
    public String viewHomePage(Model model) {
        List<Product> listProducts = service.listAll();
        List<Category> listCat = repo.listAll();
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("listCategory",listCat);
        return "catalogue_page";
    }

    @RequestMapping("/prod")
    public String viewProducts(Model model) {
        List<Product> listProducts = service.listAll();
        List<Category> listCat = repo.listAll();
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("listCategory",listCat);
        return "prod_admin";
    }

    @RequestMapping("/newProd")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        List<Category> listCat = repo.listAll();
        model.addAttribute("listCategory",listCat);
        model.addAttribute("product", product);

        return "new_product";
    }

    @RequestMapping(value = "/saveProd", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        service.save(product);

        return "redirect:/prod";
    }

    @RequestMapping("/editProd/{id}")
    public ModelAndView showEditProductForm(@PathVariable(name = "id") Long id) {
        Product product = prodRepo.findById(id).get();
        List<Category> listCat = repo.listAll();
        //model.addAttribute("listCategory",listCat);
        //model.addAttribute("product", product);
        ModelAndView mav = new ModelAndView("edit_product");
        //Product product = service.get(id);
        mav.addObject("listCategory",listCat);
        mav.addObject("product", product);

        return mav;
    }

    @RequestMapping("/deleteProd/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        service.delete(id);

        return "redirect:/prod";
    }
}
