package com.libreria.mslogin.service;

import com.libreria.mslogin.entity.Colaboradores;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ColaboradoresService {

    ResponseEntity<String> registrarColaborador(Map<String,String> requestMap);
    List<Colaboradores> obtenerAllColaboradores();
}
