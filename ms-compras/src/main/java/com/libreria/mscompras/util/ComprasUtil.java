package com.libreria.mscompras.util;

import com.libreria.mscompras.response.ResponseCompras;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ComprasUtil {

    private ComprasUtil(){

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message,httpStatus);
    }
    public static ResponseEntity<ResponseCompras> getResponseEntityList(ResponseCompras compras, HttpStatus httpStatus){
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }
}
