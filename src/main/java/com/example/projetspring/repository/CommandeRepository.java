package com.example.projetspring.repository;

import com.example.projetspring.model.Commande;
import com.example.projetspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande,Long> {



    Commande findByUser(User user);

    List<Commande> findByDate(String date);
}
