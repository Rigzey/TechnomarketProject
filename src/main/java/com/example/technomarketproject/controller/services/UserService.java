package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.*;
import com.example.technomarketproject.model.entities.*;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService extends AbstractService {

    public UserWithoutPasswordDTO register(UserRegisterDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("Email must be unique");
        }
        if(userRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new BadRequestException("Phone number must be unique");
        }
        User u = mapper.map(dto, User.class);
        String encodedPass = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPass);
        userRepository.save(u);
        logger.info("A new user with name " + dto.getFirstName() + dto.getLastName() +
                " and e-mail " + dto.getEmail() + " registered at " + LocalDateTime.now());
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
    public UserWithoutPasswordDTO login(UserLoginDTO dto) {
        boolean existsByEmail = userRepository.existsByEmail(dto.getEmail());
        if(!existsByEmail){
            throw new FileNotFoundException("No user with this email exists!");
        }
        User u = userRepository.findByEmail(dto.getEmail());
        if(!passwordEncoder.matches(dto.getPassword(), u.getPassword())){
            throw new UnauthorizedException("Invalid password!");
        }
        if(u.isDeleted()){
            throw new BadRequestException("User is deleted!");
        }
        logger.info("An existing user with e-mail " + dto.getEmail() + " logged in at " + LocalDateTime.now());
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
    public UserWithoutPasswordDTO changePassword(int userId, int loggedId, ChangePasswordDTO dto) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can change other people's passwords.");
        }
        User u = userRepository.findById(userId).get();
        if(!passwordEncoder.matches(dto.getOldPassword(), u.getPassword())){
            throw new UnauthorizedException("Invalid current password!");
        }
        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new BadRequestException("New password and old password must be different!");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Password mismatch!");
        }
        String encodedPass = passwordEncoder.encode(dto.getNewPassword());
        u.setPassword(encodedPass);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
    @Transactional
    public void deleteUser(int userId, int loggedId, UserWithPassDTO dto) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can delete other users` accounts.");
        }
        String currentPass = userRepository.findById(userId).get().getPassword();

        if (!passwordEncoder.matches(dto.getPassword(), currentPass)) {
            throw new UnauthorizedException("Incorrect password!");
        }
        User u = userRepository.findById(userId).get();
        userRepository.findById(userId).get().setDeleted(true);
        List<Order> orders = orderRepository.findByUser(u);
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser(u);
        List<SearchHistory> searchHistories = searchHistoryRepository.findAllByUser(u);
        List<Review> reviews = reviewRepository.findAllByUser(u);
        reviewRepository.deleteAll(reviews);
        searchHistoryRepository.deleteAll(searchHistories);
        shoppingCartRepository.deleteAll(shoppingCarts);
        orderRepository.deleteAll(orders);
        userRepository.save(u);
    }
    public UserWithoutPasswordDTO updateUser(int userId, int loggedId, UserWithoutPasswordDTO dto) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can update other users` profiles!");
        }
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("Email must be unique");
        }
        if(userRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new BadRequestException("Phone number must be unique");
        }
        User u = userRepository.findById(userId).get();
        u.setEmail(dto.getEmail());
        u.setGender(dto.getGender());
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setDateOfBirth(dto.getDateOfBirth());
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setAddress(dto.getAddress());
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO viewProfile(int userId) {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isEmpty() || opt.get().isDeleted()) {
            throw new FileNotFoundException("No such user.");
        }
        User u = opt.get();
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
    public Set<ProductWithIdOnlyDTO> viewSearchHistory(int userId, int loggedId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId) {
            throw new UnauthorizedException("Cannot watch other users` history");
        }

        User u = userRepository.findById(userId).get();
        List<SearchHistory> searchHistory = new ArrayList<>();

        Optional<List<SearchHistory>> opt = searchHistoryRepository.findByUser(u);
        if (opt.isPresent()) {
            searchHistory = opt.get();
        }
        Set<ProductWithIdOnlyDTO> list = new HashSet<>();
        for (SearchHistory s : searchHistory) {
            ProductWithIdOnlyDTO p = mapper.map(s.getProductId(), ProductWithIdOnlyDTO.class);
            list.add(p);
        }
        return list;
    }
    public void addRemoveFavourites(ProductWithIdOnlyDTO dto, int userId) {
        Optional<User> optU = userRepository.findById(userId);
        if(optU.isEmpty() || optU.get().isDeleted()){
            throw new FileNotFoundException("User with this id was not found!");
        }
        Optional<Product> optP = productRepository.findById(dto.getId());
        if(optP.isEmpty()){
            throw new FileNotFoundException("Product with this id was not found!");
        }
        User user = optU.get();
        Product product = optP.get();
        if(user.getFavourites().contains(product)){
            user.getFavourites().remove(product);
        }
        else{
            user.getFavourites().add(product);
        }
        userRepository.save(user);
    }

    public UserFavouritesDTO showUserFavourites(int userId) {
        Optional<User> opt = userRepository.findById(userId);
        if(opt.isEmpty() || opt.get().isDeleted()){
            throw new FileNotFoundException("User with this id was not found!");
        }
        return mapper.map(opt.get(), UserFavouritesDTO.class);
    }

    public void forgotPass(UserEmailDTO dto) {
        String email = dto.getEmail();

        if(!userRepository.existsByEmail(email)){
            throw new FileNotFoundException("User with this email does not exist");
        }
        User u = userRepository.findByEmail(email);
        String title = "Forgot email";
        String msg = "Dear, " + u.getFirstName() + "\n\n"
                + "You have requested to remember your password. \n\n"
                + "Here it is: " + u.getPassword() + "\n\n"
                + "If this request was not done by you, please, be cautious.";
        sendEmail(email, title, msg);

    }
}
