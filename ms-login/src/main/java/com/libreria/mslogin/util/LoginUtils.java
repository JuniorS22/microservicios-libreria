package com.libreria.mslogin.util;

import com.libreria.mslogin.response.ResponseDNI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginUtils {

    private LoginUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message,httpStatus);
    }
    public static ResponseEntity<ResponseDNI> getResponseEntityApi(ResponseDNI responseDNI, HttpStatus httpStatus){
        return new ResponseEntity<ResponseDNI>(responseDNI,httpStatus);
    }
}
