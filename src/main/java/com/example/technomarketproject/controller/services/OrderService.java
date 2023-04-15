package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddOrderDTO;
import com.example.technomarketproject.model.entities.Order;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService extends AbstractService{
    @Autowired
    private OrderRepository orderRepository;
    public Order addOrder(AddOrderDTO dto, int id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + id + " not found!");
        }
        if(dto.getOrderDate().isAfter(LocalDate.now())){
            throw new BadRequestException("Invalid order date!");
        }
        if(dto.getTotalPrice() < 0){
            throw new BadRequestException("Total price cannot be negative!");
        }
        Order order = mapper.map(dto, Order.class);
        order.setUser(opt.get());
        orderRepository.save(order);
        return order;
    }

    public void removeOrder(int orderId, int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if(optUser.isEmpty()){
            throw new FileNotFoundException("No user with id " + userId + " found!");
        }
        if(optOrder.isEmpty()){
            throw new FileNotFoundException("No order with id " + orderId + " found!");
        }
        if(!optUser.get().isAdmin() && optUser.get() != optOrder.get().getUser()){
            throw new UnauthorizedException("Only admins can remove other people`s orders!");
        }
        orderRepository.deleteById(orderId);
    }

    public Order showSpecific(int orderId, int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if(optUser.isEmpty()){
            throw new FileNotFoundException("No user with id " + userId + " found!");
        }
        if(optOrder.isEmpty()){
            throw new FileNotFoundException("No order with id " + orderId + " found!");
        }
        if(!optUser.get().isAdmin() && optUser.get() != optOrder.get().getUser()){
            throw new UnauthorizedException("Only admins can see other people`s orders!");
        }
        return optOrder.get();
    }

    public List<Order> showUserOrders(int userId, int sessionLoggedId) {
        Optional<User> optSession = userRepository.findById(sessionLoggedId);
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + userId + " not found!");
        }
        if(userId != sessionLoggedId && !optSession.get().isAdmin()){
            throw new UnauthorizedException("Only admins can see other people`s orders!");
        }
        return opt.get().getOrders();
    }
}
