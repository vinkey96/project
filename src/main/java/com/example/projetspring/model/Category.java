package com.example.projetspring.model;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long idCat;

    @Column(name = "name")
    private String name;


    public Category(){

    }

    public long getId() {
        return idCat;
    }

    public void setId(long id) {
        this.idCat = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
