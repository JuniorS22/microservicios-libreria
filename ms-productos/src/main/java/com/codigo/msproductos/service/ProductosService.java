package com.codigo.msproductos.service;

import com.codigo.msproductos.entity.Productos;
import com.codigo.msproductos.response.ResponseProductos;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ProductosService {

    ResponseEntity<String> registrarProductos(Map<String,String> requestMap, HttpServletRequest header);
    ResponseEntity<ResponseProductos> obtenerAllProductos(HttpServletRequest header);

    ResponseEntity<String> aumentarDisminuirStockProducto(Integer id,Map<String,String> requestMap, HttpServletRequest header);

}
