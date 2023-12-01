package com.libreria.msventas.repository;

import com.libreria.msventas.entity.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentasRepository extends JpaRepository<Ventas,Integer> {

    List<Ventas> findByEstado(Integer estado);
}
