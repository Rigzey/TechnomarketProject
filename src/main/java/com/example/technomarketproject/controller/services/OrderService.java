package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddOrderDTO;
import com.example.technomarketproject.model.DTOs.ProductWithIdOnlyDTO;
import com.example.technomarketproject.model.DTOs.SimpleOrderDTO;
import com.example.technomarketproject.model.entities.Order;
import com.example.technomarketproject.model.entities.Product;
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
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService{
    @Autowired
    private OrderRepository orderRepository;
    public SimpleOrderDTO addOrder(AddOrderDTO dto, int id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + id + " not found!");
        }
        if(dto.getDeliveryAddress().isBlank()){
            throw new BadRequestException("Delivery address cannot be null!");
        }
        if(dto.getDeliveryAddress().length() > 200){
            throw new BadRequestException("Delivery address length cannot be more than 200!");
        }
        if(dto.getOrderDate().isAfter(LocalDate.now())){
            throw new BadRequestException("Invalid order date!");
        }
        if(dto.getTotalPrice() < 0){
            throw new BadRequestException("Total price cannot be negative!");
        }
        User u = opt.get();
        Order order = mapper.map(dto, Order.class);
        order.setUser(u);
        for(Product p : order.getProducts()){
            if(productRepository.findById(p.getId()).isEmpty()){
                throw new FileNotFoundException("Product with id " + p.getId() + " not found!");
            }
        }
        orderRepository.save(order);
        SimpleOrderDTO so = mapper.map(order, SimpleOrderDTO.class);

        for(Product p : order.getProducts()){
            so.getProducts().add(mapper.map(p, ProductWithIdOnlyDTO.class));
        }
        return mapper.map(order, SimpleOrderDTO.class);
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

    public SimpleOrderDTO showSpecific(int orderId, int userId) {
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
        return mapper.map(optOrder.get(), SimpleOrderDTO.class);
    }

    public List<SimpleOrderDTO> showUserOrders(int userId, int sessionLoggedId) {
        Optional<User> optSession = userRepository.findById(sessionLoggedId);
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + userId + " not found!");
        }
        if(userId != sessionLoggedId && !optSession.get().isAdmin()){
            throw new UnauthorizedException("Only admins can see other people`s orders!");
        }
        return opt.get().getOrders()
                .stream()
                .map(o -> mapper.map(o, SimpleOrderDTO.class))
                .collect(Collectors.toList());
    }
}
