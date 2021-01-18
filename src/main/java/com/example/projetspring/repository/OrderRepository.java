package com.example.projetspring.repository;

import com.example.projetspring.model.Order;
import com.example.projetspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);

    List<Order> findByDate(Date date);
}
