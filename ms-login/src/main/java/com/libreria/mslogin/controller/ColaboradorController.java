package com.libreria.mslogin.controller;


import com.libreria.mslogin.constantes.Constantes;
import com.libreria.mslogin.entity.Colaboradores;
import com.libreria.mslogin.response.ResponseDNI;
import com.libreria.mslogin.service.ApiExternaService;
import com.libreria.mslogin.service.ColaboradoresService;
import com.libreria.mslogin.util.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradoresService colaboradoresService;

    @Autowired
    private ApiExternaService apiExternaService;

    @PostMapping("/registrarColaboradores")
    public ResponseEntity<String> registrarcolaborador(@RequestBody(required = true) Map<String,String> requestMap){
        try {
            return colaboradoresService.registrarColaborador(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return LoginUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ObtenerAllColaboradores")
    public List<Colaboradores> allColaboradores(){
        try {
            return colaboradoresService.obtenerAllColaboradores();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @GetMapping("/consultarDNI")
    public ResponseDNI consultarDNI(@RequestBody(required = true) Map<String,String> requestMap) throws IOException {
        Call<ResponseDNI> call = apiExternaService.consultarDNI(requestMap.get("dni"),"Bearer "+Constantes.TOKEN_DNI);
        retrofit2.Response<ResponseDNI> response = call.execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            // Manejar el error
            return null;
        }
    }
}
