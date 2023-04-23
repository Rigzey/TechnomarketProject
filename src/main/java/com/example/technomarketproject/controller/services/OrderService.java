package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddOrderDTO;
import com.example.technomarketproject.model.DTOs.ProductWithIdOnlyDTO;
import com.example.technomarketproject.model.DTOs.SimpleOrderDTO;
import com.example.technomarketproject.model.entities.Order;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.ShoppingCart;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService{
    @Autowired
    private OrderRepository orderRepository;
    @Transactional
    public SimpleOrderDTO addOrder(AddOrderDTO dto, int id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with id " + id + " not found!");
        }
        User u = opt.get();
        Order order = mapper.map(dto, Order.class);
        order.setUser(u);
        List<Product> list = new ArrayList<>();
        double totalPrice = 0;
        for(ShoppingCart s : shoppingCartRepository.findAllByUser(u)){
            for (int i = 0; i < s.getQuantity(); i++) {
                list.add(s.getProduct());
                totalPrice += s.getProduct().getPrice();
            }
        }
        order.setTotalPrice(totalPrice);
        if(list.isEmpty()){
            throw new BadRequestException("User has no products in the cart");
        }
        order.setProducts(list);
        for(Product p : order.getProducts()){
            if(productRepository.findById(p.getId()).isEmpty()){
                throw new FileNotFoundException("Product with id " + p.getId() + " not found!");
            }
        }
        shoppingCartRepository.deleteAllByUser(u);
        orderRepository.save(order);
        SimpleOrderDTO so = mapper.map(order, SimpleOrderDTO.class);

        for(Product p : order.getProducts()){
            so.getProducts().add(mapper.map(p, ProductWithIdOnlyDTO.class));
        }
        logger.info("A user with ID " + id + " placed a new order with ID " + order.getId());
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
        logger.info("A user with ID " + userId + " cancelled an order with ID " + orderId);
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
        if(opt.isEmpty() || opt.get().isDeleted()){
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
