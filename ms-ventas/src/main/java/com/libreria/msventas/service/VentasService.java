package com.libreria.msventas.service;

import com.libreria.msventas.response.ResponseVentas;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface VentasService {

    ResponseEntity<String> registrarVenta(Map<String,String> requestMap, HttpServletRequest header);
    ResponseEntity<ResponseVentas> obtenerAllVentas(HttpServletRequest header);

    ResponseEntity<String> eliminarVenta(Integer id, HttpServletRequest header);

    ResponseEntity<String> actualizarVenta(Integer id, Map<String,String> requestMap, HttpServletRequest header);
}
