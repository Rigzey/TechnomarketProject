package com.example.technomarketproject.controller.controllers;

import com.example.technomarketproject.model.DTOs.AddNewCharacteristicDTO;
import com.example.technomarketproject.controller.services.CharacteristicService;
import com.example.technomarketproject.model.entities.Characteristic;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CharacteristicController extends GeneralController{
    @Autowired
    private CharacteristicService characteristicService;

    @PostMapping("/characteristics")
    public Characteristic add(@Valid @RequestBody AddNewCharacteristicDTO dto, HttpSession s){
        int id = findSessionLoggedId(s);
        return characteristicService.addCharacteristic(dto, id);
    }

    @DeleteMapping("/characteristics/{cId}")
    public void delete(@PathVariable int cId, HttpSession s){
        int loggedId = findSessionLoggedId(s);
        characteristicService.deleteCharacteristic(cId, loggedId);
    }

}
