package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddDiscountDTO;
import com.example.technomarketproject.model.DTOs.NewDiscountDTO;
import com.example.technomarketproject.model.DTOs.ProductWithIdOnlyDTO;
import com.example.technomarketproject.model.entities.Discount;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.DiscountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService extends AbstractService{
    @Autowired
    private DiscountRepository discountRepository;
    @Transactional
    public NewDiscountDTO addDiscount(AddDiscountDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            logger.error("A non-admin user with ID " + id +
                    " tried to add a new product discount with value "
                    + dto.getPercentageOff() + " to product with ID " + dto.getProduct().getId());
            throw new UnauthorizedException("Only admins can add discounts!");
        }
        int pId = dto.getProduct().getId();
        if(!productRepository.existsById(pId)){
            logger.error("A user with ID " + id +
                    " tried to add a new product discount with value "
                    + dto.getPercentageOff() + " to a non-existing product with ID " + pId);
            throw new FileNotFoundException("Product with id " + pId + " not found!");
        }
        Product p = productRepository.findById(pId).get();
        if(p.getDiscount() != null){
            logger.error("A user with ID " + id +
                    " tried to add a new product discount with value "
                    + dto.getPercentageOff() + " to an already discounted product with ID " + pId);
            throw new BadRequestException("The product already has a discount!");
        }
        Discount d = mapper.map(dto, Discount.class);
        p.setPrice(p.getPrice() * (100 - dto.getPercentageOff()) / 100);
        d.setActive(true);
        discountRepository.save(d);
        p.setDiscount(d);
        productRepository.save(p);

        List<User> users = userRepository.findAllByFavouritesContaining(p);
        for(User u : users){
            if(u.isEmailSubscription()){
                String email = u.getEmail();
                String subject = "Product discount alert";
                String message = "Dear " + u.getFirstName() + ",\n\n" +
                        "The product '" + p.getName() + "' has just received a discount of " + d.getPercentageOff() + "%. " +
                        "Its new price is " + p.getPrice() + ".\n\n" +
                        "Best regards,\n" +
                        "Your Online Store Team";
                sendEmail(email, subject, message);
            }
        }

        NewDiscountDTO result = mapper.map(d, NewDiscountDTO.class);
        result.setProduct(mapper.map(p, ProductWithIdOnlyDTO.class));
        logger.info("A user with ID " + id +
                " has successfully added a new product discount with value "
                + dto.getPercentageOff() + " to a product with ID " + pId);
        return result;
    }
    @Transactional
    public void removeDiscount(int productId, int loggedId) {
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isEmpty()){
            logger.error("A user with ID " + loggedId +
                    " tried to remove a product discount from a non-existing product with ID " + productId);
            throw new FileNotFoundException("Product with id " + productId + " not found!");
        }
        if(!findUserById(loggedId).isAdmin()){
            logger.error("A non-admin user with ID " + loggedId +
                    " tried to remove a product discount from product with ID " + productId);
            throw new UnauthorizedException("Only admins can remove discounts!");
        }
        Product p = opt.get();
        Discount d = p.getDiscount();
        p.setPrice(p.getPrice() * 100 / (100 - d.getPercentageOff()));
        p.setDiscount(null);
        d.setActive(false);
        discountRepository.save(d);
        productRepository.save(p);
        logger.info("A user with ID " + loggedId +
                " has successfully removed a product discount with value "
                + d + " from a product with ID " + productId);
    }
}
