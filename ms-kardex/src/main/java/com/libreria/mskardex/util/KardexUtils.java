package com.libreria.mskardex.util;

import com.libreria.mskardex.response.ResponseKardex;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class KardexUtils {
    private KardexUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message,httpStatus);
    }
    public static ResponseEntity<ResponseKardex> getResponseEntityList(ResponseKardex kardex, HttpStatus httpStatus){
        return new ResponseEntity<>(kardex, HttpStatus.OK);
    }
}
