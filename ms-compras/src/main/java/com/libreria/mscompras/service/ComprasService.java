package com.libreria.mscompras.service;

import com.libreria.mscompras.response.ResponseCompras;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ComprasService {

    ResponseEntity<String> registrarCompra(Map<String,String> requestMap, HttpServletRequest header);
    ResponseEntity<ResponseCompras> obtenerAllCompras(HttpServletRequest header);
}
