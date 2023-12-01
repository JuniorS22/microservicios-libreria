package com.libreria.mslogin.service;

import com.libreria.mslogin.entity.Usuarios;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UsuariosService {

    ResponseEntity<String> login(Map<String,String> requestMap);
    ResponseEntity<String> registrarUsuario(Map<String,String> requestMap);
    List<Usuarios> obtenerAllUsuarios();
    Optional<Usuarios> obtenerUsuario(Integer id);
}
