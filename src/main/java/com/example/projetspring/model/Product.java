package com.example.projetspring.model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name="idCat", nullable=false)
    private Category category;

    @Column(name = "price")
    private float price;

    @Column(name = "exemplaires")
    private int NbrExemplaires;

    public Product(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNbrExemplaires() {
        return NbrExemplaires;
    }

    public void setNbrExemplaires(int nbrExemplaires) {
        NbrExemplaires = nbrExemplaires;
    }
}
