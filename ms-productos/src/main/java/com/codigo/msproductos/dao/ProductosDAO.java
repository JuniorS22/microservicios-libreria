package com.codigo.msproductos.dao;

import com.codigo.msproductos.entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductosDAO extends JpaRepository<Productos,Integer> {

    Optional<Productos> findByNombre(String nombre);
}
