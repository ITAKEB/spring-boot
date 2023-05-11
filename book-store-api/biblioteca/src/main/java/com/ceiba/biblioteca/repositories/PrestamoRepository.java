package com.ceiba.biblioteca.repositories;


import com.ceiba.biblioteca.models.PrestamoModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends CrudRepository<PrestamoModel, Integer>{
    
    @Query(value = "SELECT * FROM prestamos WHERE identificacion_usuario = ?1", nativeQuery = true)
    List<PrestamoModel> findByUserId(String idUsuario);
    
}   
