package com.example.projetspring.service;


import com.example.projetspring.model.Category;
import com.example.projetspring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<Category> listAll(){
        return repo.findAll();
    }

    public void save(Category category){
        repo.save(category);
    }

    public Category get(long id){
        return repo.findById(id).get();
    }

    public void delete(long id){
        repo.deleteById(id);
    }

}
