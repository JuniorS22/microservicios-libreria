package com.libreria.mskardex.controller;


import com.libreria.mskardex.constantes.Constantes;
import com.libreria.mskardex.response.ResponseKardex;
import com.libreria.mskardex.service.KardexService;
import com.libreria.mskardex.util.KardexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/kardex")
public class KardexController {

    @Autowired
    private KardexService kardexService;

    @PostMapping("/registrarKardex")
    public ResponseEntity<String> registrarKardex(@RequestBody(required = true) Map<String,String> requestMap, HttpServletRequest header){
        try {
            return kardexService.registrarKardex(requestMap,header);
        }catch (Exception e){
            e.printStackTrace();
        }
        return KardexUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ObtenerAllKardex")
    public ResponseEntity<ResponseKardex> allKardex(HttpServletRequest authorizationHeader){
        try {
            return kardexService.obtenerAllKardex(authorizationHeader);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
