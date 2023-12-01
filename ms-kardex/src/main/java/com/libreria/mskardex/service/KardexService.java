package com.libreria.mskardex.service;

import com.libreria.mskardex.response.ResponseKardex;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface KardexService {

    ResponseEntity<String> registrarKardex(Map<String,String> requestMap, HttpServletRequest header);
    ResponseEntity<ResponseKardex> obtenerAllKardex(HttpServletRequest header);
}
