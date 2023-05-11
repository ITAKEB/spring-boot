package com.ceiba.biblioteca.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.biblioteca.models.PrestamoModel;
import com.ceiba.biblioteca.repositories.PrestamoRepository;

@Service
public class PrestamoService {
    @Autowired
    PrestamoRepository prestamoRepository;

    public List<PrestamoModel> getAllPrestamos(){
        return (ArrayList<PrestamoModel>) prestamoRepository.findAll();
    }

    public PrestamoModel savePrestamo(PrestamoModel prestamo){
        if (prestamo.getTipoUsuario() != 3) {
            return  prestamoRepository.save(prestamo);
        }

        String userId = prestamo.getIdentificacionUsuario();
        List<PrestamoModel> userPrestamos = prestamoRepository.findByUserId(userId);

        if(!userPrestamos.isEmpty()){
            return null;
        }

        return  prestamoRepository.save(prestamo);

    }

    public Optional<PrestamoModel> getPrestamoById(int id){
        return prestamoRepository.findById(id);
    }

    public List<PrestamoModel> getPrestamoByUserId(String id){
        return prestamoRepository.findByUserId(id);
    }
    
}
