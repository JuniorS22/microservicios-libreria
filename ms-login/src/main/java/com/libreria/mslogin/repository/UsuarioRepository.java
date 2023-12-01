package com.libreria.mslogin.repository;

import com.libreria.mslogin.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios,Integer> {
    Usuarios findByUsuario(@Param(("usuario")) String usuario);

    Optional<Usuarios> findById(Integer Id);

    List<Usuarios> findByEstado(Integer estado);
}
