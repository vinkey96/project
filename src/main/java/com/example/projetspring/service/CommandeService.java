package com.example.projetspring.service;

import com.example.projetspring.model.Commande;
import com.example.projetspring.model.User;
import com.example.projetspring.repository.CartItemRepository;
import com.example.projetspring.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CartItemRepository itemRepository;

    public List<Commande> listOrders(){return commandeRepository.findAll();}

    public Commande listByUser(User user){return commandeRepository.findByUser(user);}

    public float addOrder(User user){
        Commande commande = new Commande();
        float total = itemRepository.getTotalAmmountById(user.getId());
        commande.setUser(user);
        commande.setTotal(total);
        commande.setDate(LocalDate.now().toString());
        commandeRepository.save(commande);
        return total;
    }

    public void delete(Long id) {
        commandeRepository.deleteById(id);
    }

    public void save(Commande commande) { commandeRepository.save(commande);
    }
}
