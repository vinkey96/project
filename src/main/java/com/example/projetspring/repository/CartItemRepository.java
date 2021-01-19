package com.example.projetspring.repository;

import com.example.projetspring.model.CartItem;
import com.example.projetspring.model.Product;
import com.example.projetspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> findByUser(User user);

    CartItem findByUserAndProduct(User user, Product product);

    @Query(nativeQuery = true,value="UPDATE CartItem c SET c.quantity = ?1 WHERE c.product.id = ?2 AND c.user.id = ?3")
    @Modifying
    public void updateQuantity(Integer qte,Long idProd,Long idUser);

    @Query(value="DELETE FROM CartItem c WHERE c.user.id = ?1 AND c.product.id = ?2")
    @Modifying
    public void deleteByUserAndProduct(Long idUser,Long idProd);

    @Query(nativeQuery = true,value="SELECT SUM(Cart_Items.quantity*product.price) FROM Cart_Items JOIN product")
    public float getTotalAmmountById(Long idUser);

}
