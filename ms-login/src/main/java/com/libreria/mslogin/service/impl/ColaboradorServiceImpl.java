package com.libreria.mslogin.service.impl;


import com.libreria.mslogin.constantes.Constantes;
import com.libreria.mslogin.entity.Colaboradores;
import com.libreria.mslogin.entity.Usuarios;
import com.libreria.mslogin.repository.ColaboradorRepository;
import com.libreria.mslogin.repository.UsuarioRepository;
import com.libreria.mslogin.response.ResponseDNI;
import com.libreria.mslogin.service.ApiExternaService;
import com.libreria.mslogin.service.ColaboradoresService;
import com.libreria.mslogin.service.UsuariosService;
import com.libreria.mslogin.util.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ColaboradorServiceImpl implements ColaboradoresService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ApiExternaService apiExternaService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public ResponseEntity<String> registrarColaborador(Map<String, String> requestMap) {

        log.info("Registro Interno de un colaborador : " + requestMap);
        try {
            if(validateRegistroColaborador(requestMap)){
                Usuarios usuarios=getUsuarioMap(requestMap);
                Colaboradores colaboradores=getColaboradorMap(requestMap);
                colaboradores.setIdUsuario(usuarios.getIdUsuario());
                colaboradorRepository.save(colaboradores);
                return LoginUtils.getResponseEntity("Usuario Registrado con éxito", HttpStatus.CREATED);

            }else {
                return LoginUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return LoginUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public List<Colaboradores> obtenerAllColaboradores() {
        return colaboradorRepository.findAll();
    }

    private Boolean validateRegistroColaborador(Map<String, String> requestMap){
        if(requestMap.containsKey("dni")
                && requestMap.containsKey("usuario")
                && requestMap.containsKey("contrasena")
                && requestMap.containsKey("correoElectronico")
                && requestMap.containsKey("idUsuario")
        ){
            return true;
        }
        return false;
    }
    private Colaboradores getColaboradorMap(Map<String, String> requestMap) throws IOException {
        Colaboradores colaborador = new Colaboradores();
        colaborador = getDatosDNI(requestMap);
        colaborador.setCorreoElectronico(requestMap.get("correoElectronico"));
        colaborador.setIdUsuario(Integer.parseInt(requestMap.get("idUsuario")));
        colaborador.setEstado(Constantes.ACTIVO);

        return colaborador;

    }

    private Colaboradores getDatosDNI(Map<String, String> requestMap) throws IOException {
        ResponseDNI responseDNI = new ResponseDNI();
        Call<ResponseDNI> call = apiExternaService.consultarDNI(requestMap.get("dni"),"Bearer "+Constantes.TOKEN_DNI);
        retrofit2.Response<ResponseDNI> response = call.execute();

        if (response.isSuccessful()) {
            responseDNI= response.body();
        } else {
            throw new RuntimeException();
        }
        Colaboradores colaboradores = new Colaboradores();
        colaboradores.setDni(responseDNI.getNumeroDocumento());
        colaboradores.setNombre(responseDNI.getNombres());
        colaboradores.setApellido(responseDNI.getApellidoPaterno() + " " +responseDNI.getApellidoMaterno());

        return colaboradores;

    }

    private Usuarios getUsuarioMap(Map<String, String> requestMap) throws IOException {
        Usuarios usuario = new Usuarios();
        usuario.setUsuario(requestMap.get("usuario"));
        usuario.setContrasena(requestMap.get("contrasena"));
        usuario.setEstado(Constantes.ACTIVO);
        usuario.setRol(Constantes.ROLE_USUARIO);
        return usuarioRepository.save(usuario);
    }
}
