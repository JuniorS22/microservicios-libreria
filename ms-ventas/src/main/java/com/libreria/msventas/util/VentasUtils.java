package com.libreria.msventas.util;

import com.libreria.msventas.response.ResponseVentas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VentasUtils {

    private VentasUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message,httpStatus);
    }
    public static ResponseEntity<ResponseVentas> getResponseEntityList(ResponseVentas ventas, HttpStatus httpStatus){
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
}
