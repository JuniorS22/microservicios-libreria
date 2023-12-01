package com.libreria.mscompras.controller;

import com.libreria.mscompras.constantes.Constantes;
import com.libreria.mscompras.response.ResponseCompras;
import com.libreria.mscompras.service.ComprasService;
import com.libreria.mscompras.util.ComprasUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/compras")
public class ComprasController {
    @Autowired
    private ComprasService comprasService;

    @PostMapping("/registrarCompra")
    public ResponseEntity<String> registrarCompra(@RequestBody(required = true) Map<String,String> requestMap, HttpServletRequest header){
        try {
            return comprasService.registrarCompra(requestMap,header);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ComprasUtil.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ObtenerAllCompras")
    public ResponseEntity<ResponseCompras> allCompras(HttpServletRequest authorizationHeader){
        try {
            return comprasService.obtenerAllCompras(authorizationHeader);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
