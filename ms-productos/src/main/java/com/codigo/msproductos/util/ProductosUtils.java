package com.codigo.msproductos.util;

import com.codigo.msproductos.entity.Productos;
import com.codigo.msproductos.response.ResponseProductos;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProductosUtils {
    private ProductosUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message,httpStatus);
    }
    public static ResponseEntity<ResponseProductos> getResponseEntityList(ResponseProductos productos, HttpStatus httpStatus){
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }


}
