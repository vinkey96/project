package com.example.projetspring.controllers;


import com.example.projetspring.model.Category;
import com.example.projetspring.service.CategoryService;
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
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping("/cat")
    public String viewHomePage(Model model){
        List<Category> listCat = service.listAll();
        model.addAttribute("listCategory",listCat);
        return "cat_admin";
    }

    @RequestMapping("/newCAT")
    public String showNewCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);

        return "new_category";
    }

    @RequestMapping(value = "/saveCAT", method = RequestMethod.POST)
    public String saveCategory(@ModelAttribute("category") Category category) {
        service.save(category);

        return "redirect:/cat";
    }

    @RequestMapping("/editCAT/{id}")
    public ModelAndView showEditCategoryForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_category");

        Category category = service.get(id);
        mav.addObject("category", category);

        return mav;
    }

    @RequestMapping("/deleteCAT/{id}")
    public String deleteCategory(@PathVariable(name = "id") Long id) {
        service.delete(id);

        return "redirect:/cat";
    }

}
