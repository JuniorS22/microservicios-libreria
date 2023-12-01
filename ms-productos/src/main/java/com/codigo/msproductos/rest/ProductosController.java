package com.codigo.msproductos.rest;

import com.codigo.msproductos.constantes.Constantes;
import com.codigo.msproductos.entity.Productos;
import com.codigo.msproductos.response.ResponseProductos;
import com.codigo.msproductos.service.ProductosService;
import com.codigo.msproductos.util.ProductosUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/productos")
public class ProductosController {

    @Autowired
    private ProductosService productosService;

    @PostMapping("/registrarProductos")
    public ResponseEntity<String> registrarPersona(@RequestBody(required = true) Map<String,String> requestMap, HttpServletRequest header){
        try {
            return productosService.registrarProductos(requestMap,header);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductosUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/actualizaStockProducto/{id}")
    public ResponseEntity<String> actualizaStockProducto( @PathVariable Integer id,@RequestBody(required = true) Map<String,String> requestMap, HttpServletRequest header){
        try {
            return productosService.aumentarDisminuirStockProducto(id,requestMap,header);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductosUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ObtenerAllProductos")
    public ResponseEntity<ResponseProductos> allProductos(HttpServletRequest authorizationHeader){
        try {
            return productosService.obtenerAllProductos(authorizationHeader);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
