package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.ChangePasswordDTO;
import com.example.technomarketproject.model.DTOs.UserLoginDTO;
import com.example.technomarketproject.model.DTOs.UserRegisterDTO;
import com.example.technomarketproject.model.DTOs.UserWithoutPasswordDTO;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractService {

    public UserWithoutPasswordDTO register(UserRegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Password mismatch");
        }
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new BadRequestException("Email already exists!");
        }
        if(!dto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")){
            throw new BadRequestException("Password too weak. Must contain at least one upper case, lower case and number!");
        }
        if(userRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw new BadRequestException("Phone number already exists!");
        }
        User u = mapper.map(dto, User.class);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO login(UserLoginDTO dto) {
        boolean existsByEmail = userRepository.existsByEmail(dto.getEmail());
        if(!existsByEmail){
            throw new BadRequestException("No user with this email exists!");
        }
        boolean existsByEmailAndPass = userRepository.existsByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(!existsByEmailAndPass){
            throw new UnauthorizedException("Invalid password!");
        }
        Optional<User> user = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(user.isEmpty()){
            throw new FileNotFoundException("User not found!");
        }
        return mapper.map(user.get(), UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO changePassword(int userId, int loggedId, ChangePasswordDTO dto) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can change other people's passwords.");
        }
        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new BadRequestException("New password and old password must be different!");
        }
        if(!dto.getNewPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")){
            throw new BadRequestException("Weak password! Please choose another one.");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new BadRequestException("Password mismatch!");
        }
        User u = userRepository.findById(userId).get();
        u.setPassword(dto.getNewPassword());
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public void deleteUser(int userId, int loggedId, String password) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
        }
        if (userId != loggedId && !userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can delete other user's accounts.");
        }
        if (!userRepository.findById(userId).get().getPassword().equals(password)) {
            throw new UnauthorizedException("Incorrect password!");
        }
        userRepository.findById(userId).get().setDeleted(true);
    }

    public UserWithoutPasswordDTO updateUser(int userId, int loggedId, UserWithoutPasswordDTO dto) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new FileNotFoundException("No such user.");
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
}
