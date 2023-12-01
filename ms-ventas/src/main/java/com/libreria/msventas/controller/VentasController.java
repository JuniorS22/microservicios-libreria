package com.libreria.msventas.controller;


import com.libreria.msventas.constantes.Constantes;
import com.libreria.msventas.response.ResponseVentas;
import com.libreria.msventas.service.VentasService;
import com.libreria.msventas.service.impl.ProductosServiceImpl;
import com.libreria.msventas.util.VentasUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/ventas")
public class VentasController {

    @Autowired
    private VentasService ventasService;



    @PostMapping("/registrarVenta")
    public ResponseEntity<String> registrarVenta(@RequestBody(required = true) Map<String,String> requestMap, HttpServletRequest header){
        try {
            return ventasService.registrarVenta(requestMap,header);
        }catch (Exception e){
            e.printStackTrace();
        }
        return VentasUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/actualizarVenta/{id}")
    public ResponseEntity<String> actualizarVenta(@PathVariable(required = true) Integer id, @RequestBody(required = true) Map<String,String> requestMap, HttpServletRequest header){
        try {
            return ventasService.actualizarVenta(id, requestMap, header);
        }catch (Exception e){
            e.printStackTrace();
        }
        return VentasUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ObtenerAllVentas")
    public ResponseEntity<ResponseVentas> allVentas(HttpServletRequest authorizationHeader){
        try {
            return ventasService.obtenerAllVentas(authorizationHeader);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/eliminarVenta/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable(required = true) Integer id, HttpServletRequest header){
        try {
            return ventasService.eliminarVenta(id, header);
        }catch (Exception e){
            e.printStackTrace();
        }
        return VentasUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
