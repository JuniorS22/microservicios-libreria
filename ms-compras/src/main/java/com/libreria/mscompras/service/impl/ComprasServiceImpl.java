package com.libreria.mscompras.service.impl;


import com.libreria.mscompras.constantes.Constantes;
import com.libreria.mscompras.entity.Compras;
import com.libreria.mscompras.repository.ComprasRepository;
import com.libreria.mscompras.response.ResponseCompras;
import com.libreria.mscompras.response.ResponseKardex;
import com.libreria.mscompras.security.jwt.JwtUtil;
import com.libreria.mscompras.service.ComprasService;
import com.libreria.mscompras.util.ComprasUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ComprasServiceImpl implements ComprasService {

    @Autowired
    private ComprasRepository comprasRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private KardexServiceImpl kardexService;

    @Autowired
    private ProductosServiceImpl productosService;
    @Override
    public ResponseEntity<String> registrarCompra(Map<String, String> requestMap, HttpServletRequest header) {
        log.info("Registro Interno de una Compra : " + requestMap);
        try {
            String token = devuelveToken(header);
            if(jwtUtil.validateToken(token)){
                if(validateRegistroCompra(requestMap)){
                    comprasRepository.save(getComprasMap(requestMap));
                    kardexService.registrarKardex(requestMap,token);
                    productosService.actualizarStockProducto(Integer.parseInt(requestMap.get("idProducto")),requestMap,token);
                    return ComprasUtil.getResponseEntity("Compra Registrada con éxito", HttpStatus.CREATED);

                }else {
                    return ComprasUtil.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return ComprasUtil.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ComprasUtil.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ResponseCompras> obtenerAllCompras(HttpServletRequest header) {
        String token = devuelveToken(header);
        try {
            if(jwtUtil.validateToken(token)){
                List<Compras> list = new ArrayList<>();
                ResponseCompras productos = new ResponseCompras();

                list =comprasRepository.findAll();
                if(list.size()>0){
                    productos.setCodigo(Constantes.OPER_EXITOSA);
                    productos.setMessage(Constantes.MSG_EXITOSO);
                    productos.setVentas(list);
                    return ComprasUtil.getResponseEntityList(productos,HttpStatus.OK);
                }else {
                    productos.setCodigo(Constantes.OPER_VACIA);
                    productos.setMessage(Constantes.MSG_VACIA);
                    productos.setVentas(list);
                    return ComprasUtil.getResponseEntityList(null,HttpStatus.OK);
                }

            }else{
                return  ComprasUtil.getResponseEntityList(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (JwtException e){
            ResponseCompras responseProductos = new ResponseCompras();
            responseProductos.setCodigo(Constantes.OPER_NO_EXITOSA);
            responseProductos.setMessage(Constantes.MSG_NO_EXITOSO);
            responseProductos.setVentas(null);
            return  ComprasUtil.getResponseEntityList(responseProductos, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private Boolean validateRegistroCompra(Map<String, String> requestMap){
        if(requestMap.containsKey("fechaEmision")
                && requestMap.containsKey("idProveedor")
                && requestMap.containsKey("idProducto")
                && requestMap.containsKey("cantidad")
                && requestMap.containsKey("precioUnitario")){
            return true;
        }
        return false;

    }
    private Compras getComprasMap(Map<String, String> requestMap){
        Compras productos = new Compras();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        productos.setFechaEmision(LocalDate.parse(requestMap.get("fechaEmision"), formatterDate));
        productos.setIdProveedor(Integer.parseInt(requestMap.get("idProveedor")));
        productos.setIdProducto(Integer.parseInt(requestMap.get("idProducto")));
        productos.setCantidad(Integer.parseInt(requestMap.get("cantidad")));
        productos.setPrecioUnitario(Double.parseDouble(requestMap.get("precioUnitario")));
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

}
