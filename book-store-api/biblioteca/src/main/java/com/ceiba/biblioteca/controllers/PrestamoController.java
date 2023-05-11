package com.ceiba.biblioteca.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ceiba.biblioteca.models.PrestamoModel;
import com.ceiba.biblioteca.services.PrestamoService;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {
    @Autowired
    PrestamoService prestamoService;
    
    @GetMapping()
    public List<PrestamoModel> getAllPrestamos(){
        return prestamoService.getAllPrestamos();
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> postPrestamo(@RequestBody PrestamoModel prestamo){
        HashMap<String, Object> mapResponse = new HashMap<>();
        if(prestamo.getTipoUsuario() <= 0 || prestamo.getTipoUsuario() > 3){
            String errMssg = "Tipo de usuario no permitido en la biblioteca";    
            mapResponse.put("mensaje", errMssg);
            return new ResponseEntity<>(mapResponse,HttpStatus.BAD_REQUEST);
        }

        String userId = prestamo.getIdentificacionUsuario();
        String regex = "^[a-zA-Z0-9]*$";
        if(userId == null || userId.length() == 0
         || !userId.matches(regex) || userId.length() > 10){
            String errMssg = "Id usuario invalido";    
            mapResponse.put("mensaje", errMssg);
            return new ResponseEntity<>(mapResponse,HttpStatus.BAD_REQUEST);
        }

        PrestamoModel prestamoResponse = this.prestamoService.savePrestamo(prestamo); 
        if(prestamoResponse != null){
            mapResponse.put("id", prestamoResponse.getId());
            mapResponse.put("fechaMaximaDevolucion", prestamoResponse.getFechaMaximaDevolucion());
            return new ResponseEntity<>(mapResponse,HttpStatus.OK);
        }

        String errMssg = "El usuario con identificación %s ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo";
        String resMessage = String.format(errMssg, prestamo.getIdentificacionUsuario());  
                        
        mapResponse.put("mensaje", resMessage);
        return new ResponseEntity<>(mapResponse,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{idPrestamo}")
    public Optional<PrestamoModel> getPrestamoById(@PathVariable("idPrestamo") int id){
        return this.prestamoService.getPrestamoById(id);
    }

}
