package com.libreria.mslogin.service.impl;

import com.libreria.mslogin.constantes.Constantes;
import com.libreria.mslogin.entity.Usuarios;
import com.libreria.mslogin.repository.UsuarioRepository;
import com.libreria.mslogin.security.CustomerDetailsService;
import com.libreria.mslogin.security.jwt.JwtUtil;
import com.libreria.mslogin.service.UsuariosService;
import com.libreria.mslogin.util.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UsuariosServiceImpl implements UsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Ingreso Login : " + requestMap);

        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("usuario"),requestMap.get("contrasena")));

            if(authentication.isAuthenticated()){
                if(customerDetailsService.getUserDetail().getEstado() == 1 ){
                    return new ResponseEntity<String>(
                            "{\"token \":\"" + jwtUtil.generateToken(customerDetailsService.getUserDetail().getUsuario(),
                                    customerDetailsService.getUserDetail().getRol()) +"\"}",
                            HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>("{\"mensaje\": " + "Espera la Aprobación del administrador"+"\"}",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("{}", e);
        }
        return new ResponseEntity<String>("{\"mensaje\": " + "Credenciales Incorrectas"+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> registrarUsuario(Map<String, String> requestMap) {
        log.info("Registro Interno de un Usuario : " + requestMap);
        try {
            if(validatePrevioRegistro(requestMap)){

                //Validamos el email
                Usuarios usuarios = usuarioRepository.findByUsuario(requestMap.get("usuario"));
                if(Objects.isNull(usuarios)){
                    usuarioRepository.save(getUsuariosMap(requestMap));
                    return LoginUtils.getResponseEntity("Usuario Registrado con éxito", HttpStatus.CREATED);
                }else{
                    return LoginUtils.getResponseEntity("Usuario Ya existe", HttpStatus.BAD_REQUEST);
                }
            }else {
                return LoginUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return LoginUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public List<Usuarios> obtenerAllUsuarios() {
        return usuarioRepository.findByEstado(1);
    }

    @Override
    public Optional<Usuarios> obtenerUsuario(Integer id) {
        return usuarioRepository.findById(id);
    }


    private Boolean validatePrevioRegistro(Map<String, String> requestMap){
        if(requestMap.containsKey("usuario")
                && requestMap.containsKey("contrasena")){
            return true;
        }

        return false;
    }
    private Usuarios getUsuariosMap(Map<String, String> requestMap){
        Usuarios usuarios = new Usuarios();
        usuarios.setUsuario(requestMap.get("usuario"));
        usuarios.setContrasena(requestMap.get("contrasena"));
        usuarios.setEstado(Constantes.ACTIVO);
        usuarios.setRol(Constantes.ROLE_USUARIO);
        return usuarios;

    }
}
