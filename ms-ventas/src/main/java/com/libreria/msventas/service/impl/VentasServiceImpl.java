package com.libreria.msventas.service.impl;

import com.libreria.msventas.constantes.Constantes;
import com.libreria.msventas.entity.Ventas;
import com.libreria.msventas.repository.VentasRepository;
import com.libreria.msventas.response.ResponseVentas;
import com.libreria.msventas.security.jwt.JwtUtil;
import com.libreria.msventas.service.VentasService;
import com.libreria.msventas.util.VentasUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class VentasServiceImpl implements VentasService {

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private KardexServiceImpl kardexService;

    @Autowired

    private ProductosServiceImpl productosService;
    @Override
    public ResponseEntity<String> registrarVenta(Map<String, String> requestMap, HttpServletRequest header) {
        log.info("Registro Interno de una Venta : " + requestMap);
        try {
            String token = devuelveToken(header);
            if(jwtUtil.validateToken(token)){
                if(validateRegistroVenta(requestMap)){
                    ventasRepository.save(getVentasMap(requestMap));
                    kardexService.registrarKardex(requestMap,token);
                    productosService.actualizarStockProducto(Integer.parseInt(requestMap.get("idProducto")),requestMap,token);
                    return VentasUtils.getResponseEntity("Venta Registrada con éxito", HttpStatus.CREATED);

                }else {
                    return VentasUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return VentasUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return VentasUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ResponseVentas> obtenerAllVentas(HttpServletRequest header) {
        String token = devuelveToken(header);
        try {
            if(jwtUtil.validateToken(token)){
                List<Ventas> list = new ArrayList<>();
                ResponseVentas productos = new ResponseVentas();

                list =ventasRepository.findByEstado(1);
                if(list.size()>0){
                    productos.setCodigo(Constantes.OPER_EXITOSA);
                    productos.setMessage(Constantes.MSG_EXITOSO);
                    productos.setVentas(list);
                    return VentasUtils.getResponseEntityList(productos,HttpStatus.OK);
                }else {
                    productos.setCodigo(Constantes.OPER_VACIA);
                    productos.setMessage(Constantes.MSG_VACIA);
                    productos.setVentas(list);
                    return VentasUtils.getResponseEntityList(null,HttpStatus.OK);
                }

            }else{
                return  VentasUtils.getResponseEntityList(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (JwtException e){
            ResponseVentas responseProductos = new ResponseVentas();
            responseProductos.setCodigo(Constantes.OPER_NO_EXITOSA);
            responseProductos.setMessage(Constantes.MSG_NO_EXITOSO);
            responseProductos.setVentas(null);
            return  VentasUtils.getResponseEntityList(responseProductos, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<String> eliminarVenta(Integer id, HttpServletRequest header) {
        try {
            String token = devuelveToken(header);
            if(jwtUtil.validateToken(token)){

                Optional<Ventas> ventaExistente = ventasRepository.findById(id);

                if (ventaExistente.isPresent()){

                    Ventas ventas = ventaExistente.get();

                    if (Objects.equals(ventas.getEstado(), Constantes.ACTIVO)){

                        ventas.setEstado(0);

                        ventasRepository.save(ventas);

                        return VentasUtils.getResponseEntity(Constantes.MSG_EXITOSO,HttpStatus.OK);
                    }else {
                        return VentasUtils.getResponseEntity(Constantes.MSG_VACIA,HttpStatus.OK);
                    }

                }else {
                    return VentasUtils.getResponseEntity(Constantes.MSG_VACIA,HttpStatus.OK);
                }

            }else{
                return  VentasUtils.getResponseEntity(Constantes.MSG_NO_EXITOSO, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (JwtException e){
            return  VentasUtils.getResponseEntity(Constantes.MSG_NO_EXITOSO, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VentasUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> actualizarVenta(Integer id, Map<String, String> requestMap, HttpServletRequest header) {
        try {
            String token = devuelveToken(header);
            if(jwtUtil.validateToken(token)){
                if(validateRegistroVenta(requestMap)){

                    Optional<Ventas> ventaExistente = ventasRepository.findById(id);

                    if (ventaExistente.isPresent()){

                        Ventas ventas = ventaExistente.get();

                        if (Objects.equals(ventas.getEstado(), Constantes.ACTIVO)){

                            Ventas ventaRequestMap = getVentasMap(requestMap);

                            ventas.setFechaEmision(ventaRequestMap.getFechaEmision());
                            ventas.setIdCliente(ventaRequestMap.getIdCliente());
                            ventas.setIdColaborador(ventaRequestMap.getIdColaborador());
                            ventas.setIdProducto(ventaRequestMap.getIdProducto());
                            ventas.setCantidad(ventaRequestMap.getCantidad());
                            ventas.setPrecioUnitario(ventaRequestMap.getPrecioUnitario());
                            ventas.setTotal(ventaRequestMap.getTotal());
                            ventas.setUpdatedBy(jwtUtil.extracUserName(token));

                            ventasRepository.save(ventas);

                            return VentasUtils.getResponseEntity(Constantes.MSG_EXITOSO,HttpStatus.OK);
                        }else {
                            return VentasUtils.getResponseEntity(Constantes.MSG_VACIA,HttpStatus.OK);
                        }

                    }else {
                        return VentasUtils.getResponseEntity(Constantes.MSG_VACIA,HttpStatus.OK);
                    }
                }else {
                    return VentasUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return VentasUtils.getResponseEntity(Constantes.MSG_NO_EXITOSO, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (JwtException e){
            return  VentasUtils.getResponseEntity(Constantes.MSG_NO_EXITOSO, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VentasUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Boolean validateRegistroVenta(Map<String, String> requestMap){
        if(requestMap.containsKey("fechaEmision")
                && requestMap.containsKey("idCliente")
                && requestMap.containsKey("idColaborador")
                && requestMap.containsKey("idProducto")
                && requestMap.containsKey("cantidad")
                && requestMap.containsKey("precioUnitario")){
            return true;
        }
        return false;

    }
    private Ventas getVentasMap(Map<String, String> requestMap){
        Ventas productos = new Ventas();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        productos.setFechaEmision(LocalDate.parse(requestMap.get("fechaEmision"), formatterDate));
        productos.setIdCliente(Integer.parseInt(requestMap.get("idCliente")));
        productos.setIdColaborador(Integer.parseInt(requestMap.get("idColaborador")));
        productos.setIdProducto(Integer.parseInt(requestMap.get("idProducto")));
        productos.setCantidad(Integer.parseInt(requestMap.get("cantidad")));
        productos.setPrecioUnitario(Double.parseDouble(requestMap.get("precioUnitario")));
        productos.setTotal(calcularTotal(Integer.parseInt(requestMap.get("cantidad")),Double.parseDouble(requestMap.get("precioUnitario"))));
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

    private double calcularTotal(int cantidad,double unitario){
        return cantidad*unitario;
    }


}
