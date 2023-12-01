package com.codigo.msproductos.service.impl;


import com.codigo.msproductos.constantes.Constantes;
import com.codigo.msproductos.dao.ProductosDAO;
import com.codigo.msproductos.entity.Productos;
import com.codigo.msproductos.response.ResponseProductos;
import com.codigo.msproductos.security.jwt.JwtUtil;
import com.codigo.msproductos.service.ProductosService;
import com.codigo.msproductos.util.ProductosUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
public class ProductosServiceImpl implements ProductosService {


    @Autowired
    private ProductosDAO productosDAO;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> registrarProductos(Map<String, String> requestMap, HttpServletRequest header) {
        log.info("Registro Interno de una Producto : " + requestMap);
        try {
            String token = devuelveToken(header);
            if(jwtUtil.validateToken(token)){
                if(validateRegistroProducto(requestMap)){
                    productosDAO.save(getProductosMap(requestMap));
                    return ProductosUtils.getResponseEntity("Producto Registrado con éxito", HttpStatus.CREATED);

                }else {
                    return ProductosUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return ProductosUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductosUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ResponseProductos> obtenerAllProductos(HttpServletRequest header) {
        String token = devuelveToken(header);
        try {
            if(jwtUtil.validateToken(token)){
                List<Productos> list = new ArrayList<>();
                ResponseProductos productos = new ResponseProductos();

                list =productosDAO.findAll();
                if(list.size()>0){
                    productos.setCodigo(Constantes.OPER_EXITOSA);
                    productos.setMessage(Constantes.MSG_EXITOSO);
                    productos.setProductos(list);
                    return ProductosUtils.getResponseEntityList(productos,HttpStatus.OK);
                }else {
                    productos.setCodigo(Constantes.OPER_VACIA);
                    productos.setMessage(Constantes.MSG_VACIA);
                    productos.setProductos(list);
                    return ProductosUtils.getResponseEntityList(null,HttpStatus.OK);
                }

            }else{
                return  ProductosUtils.getResponseEntityList(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (JwtException e){
            ResponseProductos responseProductos = new ResponseProductos();
            responseProductos.setCodigo(Constantes.OPER_NO_EXITOSA);
            responseProductos.setMessage(Constantes.MSG_NO_EXITOSO);
            responseProductos.setProductos(null);
            return  ProductosUtils.getResponseEntityList(responseProductos, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<String> aumentarDisminuirStockProducto(Integer id,Map<String, String> requestMap, HttpServletRequest header) {

        try {
            String token = devuelveToken(header);
            int nuevoStock;
            if(jwtUtil.validateToken(token)){
                if(validateAumentarDisminuirProducto(requestMap)){
                    Optional<Productos> productoExistente=productosDAO.findById(id);
                    if (productoExistente.isPresent()){
                        Productos productos=productoExistente.get();
                        if (verificarTipoMovimiento(requestMap.get("tipoMovimiento"))){
                            if (requestMap.get("tipoMovimiento").equals("Entrada")){
                                nuevoStock=productos.getStock()+Integer.parseInt(requestMap.get("cantidad"));
                            }else{
                                nuevoStock=productos.getStock()-Integer.parseInt(requestMap.get("cantidad"));
                            }
                            productos.setStock(nuevoStock);
                            productosDAO.save(productos);
                            return ProductosUtils.getResponseEntity("Producto Registrado con éxito", HttpStatus.CREATED);

                        }else {
                            return ProductosUtils.getResponseEntity("Tipo de movimiento debe ser Entrada o S valida",HttpStatus.BAD_REQUEST);
                        }
                    }else {
                        return ProductosUtils.getResponseEntity("El producto no existe",HttpStatus.BAD_REQUEST);
                    }
                }else {
                    return ProductosUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return ProductosUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ProductosUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Boolean validateRegistroProducto(Map<String, String> requestMap){
        if(requestMap.containsKey("nombre")
                && requestMap.containsKey("precio")
                && requestMap.containsKey("stock")
                && requestMap.containsKey("idCategoria")){
            return true;
        }
        return false;

    }
    private Productos getProductosMap(Map<String, String> requestMap){
        Productos productos = new Productos();
        productos.setNombre(requestMap.get("nombre"));
        productos.setPrecio(Double.parseDouble(requestMap.get("precio")));
        productos.setStock(Integer.parseInt(requestMap.get("stock")));
        productos.setIdCategoria(Integer.parseInt(requestMap.get("idCategoria")));
        productos.setEstado(Constantes.ACTIVO);

        return productos;

    }

    private String devuelveToken(HttpServletRequest header){
        String authorizationHeader = header.getHeader("Authorization");
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
             token = authorizationHeader.substring(7);
        }
        return token;
    }

    private boolean verificarExisteProducto(String nombre){
        Optional<Productos> productoExistente=productosDAO.findByNombre(nombre);
        return productoExistente.isPresent();
    }


    private Boolean validateAumentarDisminuirProducto(Map<String, String> requestMap){
        return requestMap.containsKey("tipoMovimiento")
                && requestMap.containsKey("cantidad");

    }

    private Boolean verificarTipoMovimiento(String tipoMovimiento){
        Set<String> tipoMovimientos=new HashSet<>(Set.of("Entrada","Salida"));
        return tipoMovimientos.contains(tipoMovimiento);
    }
}
