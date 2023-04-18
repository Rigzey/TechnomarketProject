package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddNewCharacteristicDTO;
import com.example.technomarketproject.model.entities.Characteristic;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.CharacteristicRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicService extends AbstractService{
    @Autowired
    private CharacteristicRepository characteristicRepository;

    public Characteristic addCharacteristic(AddNewCharacteristicDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            throw new UnauthorizedException("Only admins can add new characteristics!");
        }
        if(characteristicRepository.existsByName(dto.getName())){
            throw new BadRequestException("Characteristic with this name already exists!");
        }
        Characteristic c = mapper.map(dto, Characteristic.class);
        characteristicRepository.save(c);
        return c;
    }
    public void deleteCharacteristic(int cId, int loggedId) {
        if(!userRepository.findById(loggedId).get().isAdmin()){
            throw new UnauthorizedException("Only admins can delete characteristics!");
        }
        if(!characteristicRepository.existsById(cId)){
            throw new FileNotFoundException("Characteristic with this id does not exist!");
        }
        characteristicRepository.deleteById(cId);
    }
}
