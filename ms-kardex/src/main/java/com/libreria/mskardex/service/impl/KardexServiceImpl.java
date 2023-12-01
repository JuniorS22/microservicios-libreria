package com.libreria.mskardex.service.impl;

import com.libreria.mskardex.constantes.Constantes;
import com.libreria.mskardex.entity.Kardex;
import com.libreria.mskardex.repository.KardexRepository;
import com.libreria.mskardex.response.ResponseKardex;
import com.libreria.mskardex.security.jwt.JwtUtil;
import com.libreria.mskardex.service.KardexService;
import com.libreria.mskardex.util.KardexUtils;
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
public class KardexServiceImpl implements KardexService {

    @Autowired
    private KardexRepository kardexRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> registrarKardex(Map<String, String> requestMap, HttpServletRequest header) {
        log.info("Registro Interno del kardex : " + requestMap);
        try {
            String token = devuelveToken(header);
            if(jwtUtil.validateToken(token)){
                if(validateRegistroProducto(requestMap)){
                    kardexRepository.save(getKardexMap(requestMap));
                    return KardexUtils.getResponseEntity("Kardex Registrado con éxito", HttpStatus.CREATED);

                }else {
                    return KardexUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.BAD_REQUEST);
                }
            }else{
                return KardexUtils.getResponseEntity(Constantes.DATA_INVALIDA, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return KardexUtils.getResponseEntity(Constantes.ALGO_SALIO_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ResponseKardex> obtenerAllKardex(HttpServletRequest header) {
        String token = devuelveToken(header);
        try {
            if(jwtUtil.validateToken(token)){
                List<Kardex> list = new ArrayList<>();
                ResponseKardex kardex = new ResponseKardex();

                list =kardexRepository.findAll();
                if(list.size()>0){
                    kardex.setCodigo(Constantes.OPER_EXITOSA);
                    kardex.setMessage(Constantes.MSG_EXITOSO);
                    kardex.setProductos(list);
                    return KardexUtils.getResponseEntityList(kardex,HttpStatus.OK);
                }else {
                    kardex.setCodigo(Constantes.OPER_VACIA);
                    kardex.setMessage(Constantes.MSG_VACIA);
                    kardex.setProductos(list);
                    return KardexUtils.getResponseEntityList(null,HttpStatus.OK);
                }

            }else{
                return  KardexUtils.getResponseEntityList(null, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (JwtException e){
            ResponseKardex responseProductos = new ResponseKardex();
            responseProductos.setCodigo(Constantes.OPER_NO_EXITOSA);
            responseProductos.setMessage(Constantes.MSG_NO_EXITOSO);
            responseProductos.setProductos(null);
            return  KardexUtils.getResponseEntityList(responseProductos, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private Boolean validateRegistroProducto(Map<String, String> requestMap){
        if(requestMap.containsKey("fecha")
                && requestMap.containsKey("idProducto")
                && requestMap.containsKey("tipoMovimiento")
                && requestMap.containsKey("cantidad")){
            return true;
        }
        return false;

    }
    private Kardex getKardexMap(Map<String, String> requestMap){
        Kardex kardex = new Kardex();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        kardex.setFecha(LocalDate.parse(requestMap.get("fecha"), formatterDate));
        kardex.setIdProducto(Integer.parseInt(requestMap.get("idProducto")));
        kardex.setTipoMovimiento(requestMap.get("tipoMovimiento"));
        kardex.setCantidad(Integer.parseInt(requestMap.get("cantidad")));
        kardex.setEstado(Constantes.ACTIVO);

        return kardex;

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
