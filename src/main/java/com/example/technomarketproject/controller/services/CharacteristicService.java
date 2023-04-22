package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.AddNewCharacteristicDTO;
import com.example.technomarketproject.model.entities.Characteristic;
import com.example.technomarketproject.model.exceptions.BadRequestException;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import com.example.technomarketproject.model.repositories.CharacteristicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicService extends AbstractService{
    @Autowired
    private CharacteristicRepository characteristicRepository;

    public Characteristic addCharacteristic(AddNewCharacteristicDTO dto, int id) {
        if(!findUserById(id).isAdmin()){
            logger.error("A non-admin user with ID " + id +
                    " tried to add a new product characteristic with name " + dto.getName());
            throw new UnauthorizedException("Only admins can add new characteristics!");
        }
        if(characteristicRepository.existsByName(dto.getName())){
            logger.error("A user with ID " + id +
                    " tried to add an existing product characteristic with name " + dto.getName());
            throw new BadRequestException("Characteristic with this name already exists!");
        }
        Characteristic c = mapper.map(dto, Characteristic.class);
        characteristicRepository.save(c);
        logger.info("A new product characteristic with name " + dto.getName() + " has been created by user with ID " + id);
        return c;
    }
    public void deleteCharacteristic(int cId, int loggedId) {
        if(!userRepository.findById(loggedId).get().isAdmin()){
            logger.error("A non-admin user with ID " + loggedId +
                    " tried to remove a product characteristic with ID " + cId);
            throw new UnauthorizedException("Only admins can delete characteristics!");
        }
        if(!characteristicRepository.existsById(cId)){
            logger.error("A user with ID " + loggedId +
                    " tried to remove an non-existing product characteristic with ID " + cId);
            throw new FileNotFoundException("Characteristic with this id does not exist!");
        }
        characteristicRepository.deleteById(cId);
        logger.info("A product characteristic with ID " + cId + " has been removed by user with ID " + loggedId);
    }
}
