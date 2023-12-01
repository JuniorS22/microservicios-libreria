package com.libreria.mslogin.controller;

import com.libreria.mslogin.constantes.Constantes;
import com.libreria.mslogin.entity.Usuarios;
import com.libreria.mslogin.service.UsuariosService;
import com.libreria.mslogin.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/registrarUsuarios")
    public ResponseEntity<String> registrarUsuarios(@RequestBody(required = true) Map<String,String> requestMap){
        try {
            return usuariosService.registrarUsuario(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return LoginUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ObtenerAllUsuarios")
    public List<Usuarios> allUsuarios(){
        try {
            return usuariosService.obtenerAllUsuarios();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping("/ObtenerUsuario")
    public Optional<Usuarios> ObtenerUnUsuario(@RequestParam(required = true) Integer id){
        try {
            return usuariosService.obtenerUsuario(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requestMap){
        try {
            return usuariosService.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return LoginUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
