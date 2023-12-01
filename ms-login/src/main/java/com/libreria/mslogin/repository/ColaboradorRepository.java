package com.libreria.mslogin.repository;

import com.libreria.mslogin.entity.Colaboradores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaboradores,Integer> {
}
