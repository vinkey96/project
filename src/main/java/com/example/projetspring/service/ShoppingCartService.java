package com.example.projetspring.service;

import com.example.projetspring.model.CartItem;
import com.example.projetspring.model.Product;
import com.example.projetspring.model.User;
import com.example.projetspring.repository.CartItemRepository;
import com.example.projetspring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private CartItemRepository repo;

    @Autowired
    private ProductRepository repoProd;

    public List<CartItem> listCartItems(User user){
        return repo.findByUser(user);
    }

    public int addProduct(Long idprod,int quantity,User user){
        int qte = quantity;
        Product product = repoProd.findById(idprod).get();
        CartItem cartItem = repo.findByUserAndProduct(user,product);

        if(cartItem != null){
            qte = cartItem.getQuantity() + qte;
            cartItem.setQuantity(qte);
        }
        else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setProduct(product);
            cartItem.setUser(user);
        }
        repo.save(cartItem);
        return qte;
    }

    public float updateQuantity(Long idProd,Integer quantity,User user){
        repo.updateQuantity(quantity,idProd,user.getId());
        Product product = repoProd.findById(idProd).get();

        float subTotal = product.getPrice()*quantity;
        return subTotal;
    }

    public void removeProduct(Long idProd,User user){
        repo.deleteByUserAndProduct(user.getId(),idProd);
    }
}
