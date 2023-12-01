package com.libreria.mslogin.security;

import com.libreria.mslogin.entity.Usuarios;
import com.libreria.mslogin.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuarios usuarios;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Ingreso loadUserByUsername {} ", username);
        usuarios = usuarioRepository.findByUsuario(username);
        if(!Objects.isNull(usuarios)){
            return new User(usuarios.getUsuario(),usuarios.getContrasena(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("Usuario no Encontrado");
        }
    }

    public Usuarios getUserDetail(){
        return usuarios;
    }
}
