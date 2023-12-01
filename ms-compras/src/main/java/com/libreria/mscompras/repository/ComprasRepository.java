package com.libreria.mscompras.repository;

import com.libreria.mscompras.entity.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprasRepository extends JpaRepository<Compras,Integer> {

}
