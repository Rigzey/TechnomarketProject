package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.*;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.entities.SearchHistory;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class UserService extends AbstractService {

    public UserWithoutPasswordDTO register(UserRegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Password mismatch");
        }
        if(!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            throw new BadRequestException("Invalid email address!");
        }
        if(dto.getEmail().length() > 100 || dto.getEmail().length() < 6){
            throw new BadRequestException("Invalid email size!");
        }
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("Email already exists!");
        }
        if(!dto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$") || dto.getPassword().length() < 8){
            throw new BadRequestException("Password too weak. Must contain at least one upper case, lower case and number!");
        }
        if(!dto.getPhoneNumber().matches("^(\\+359|0)[87-9][0-9]{7}$")){
            throw new BadRequestException("Invalid phone number!");
        }
        if(userRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new BadRequestException("Phone number already exists!");
        }
        User u = mapper.map(dto, User.class);
        String encodedPass = passwordEncoder.encode(u.getPassword());
        u.setPassword(encodedPass);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO login(UserLoginDTO dto) {
        boolean existsByEmail = userRepository.existsByEmail(dto.getEmail());
        if(!existsByEmail){
            throw new BadRequestException("No user with this email exists!");
        }
        User u = userRepository.findByEmail(dto.getEmail());
        if(!passwordEncoder.matches(dto.getPassword(), u.getPassword())){
            throw new UnauthorizedException("Invalid password!");
        }
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
        if(!dto.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$") || dto.getNewPassword().length() < 8){
            throw new BadRequestException("Weak password! Please choose another one.");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Password mismatch!");
        }
        String encodedPass = passwordEncoder.encode(dto.getNewPassword());
        u.setPassword(encodedPass);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public void deleteUser(int userId, int loggedId, String password) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can delete other users` accounts.");
        }
        String encodedPass = userRepository.findById(userId).get().getPassword();
        if (!passwordEncoder.matches(encodedPass, password)) {
            throw new UnauthorizedException("Incorrect password!");
        }
        userRepository.findById(userId).get().setDeleted(true);
    }

    public UserWithoutPasswordDTO updateUser(int userId, int loggedId, UserWithoutPasswordDTO dto) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can update other users` profiles!");
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
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        User u = userRepository.findById(userId).get();
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public Set<ProductWithIdOnlyDTO> viewSearchHistory(int userId, int loggedId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can view other people's search histroy.");
        }

        User u = userRepository.findById(userId).get();
        List<SearchHistory> searchHistory = new ArrayList<>();

        Optional<List<SearchHistory>> opt = searchHistoryRepository.findByUser(u);
        if(opt.isPresent()){
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
        if(optU.isEmpty()){
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
        if(opt.isEmpty()){
            throw new FileNotFoundException("User with this id was not found!");
        }
        return mapper.map(opt.get(), UserFavouritesDTO.class);
    }
}
