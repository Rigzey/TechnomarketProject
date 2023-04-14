package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.UserRegisterDTO;
import com.example.technomarketproject.model.DTOs.UserWithoutPasswordDTO;
import com.example.technomarketproject.model.entities.User;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService {


    public UserWithoutPasswordDTO register(UserRegisterDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Password mismatch");
        }
        User u = mapper.map(dto, User.class);
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
}
