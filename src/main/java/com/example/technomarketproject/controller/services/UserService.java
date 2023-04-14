package com.example.technomarketproject.controller.services;

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

    public UserWithoutPasswordDTO changePassword(int userId, int loggedId) {
    }
}
