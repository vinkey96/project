package com.example.projetspring.service;

import com.example.projetspring.model.Order;
import com.example.projetspring.model.User;
import com.example.projetspring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> listOrders(){return orderRepository.findAll();}

    public List<Order> listByUser(User user){return orderRepository.findByUser(user);}

}
