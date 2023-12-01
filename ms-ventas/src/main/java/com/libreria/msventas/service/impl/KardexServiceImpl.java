package com.libreria.msventas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KardexServiceImpl {
    @Autowired
    private RestTemplate restTemplate;

    public String registrarKardex(Map<String, String> requestMap, String token) {

        try {

            String apiUrl = "http://localhost:8080/api/kardex/registrarKardex";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            Map<String, String> body = new HashMap<>();

            body.put("fecha", requestMap.get("fechaEmision"));
            body.put("idProducto", requestMap.get("idProducto"));
            body.put("tipoMovimiento", "Salida");
            body.put("cantidad", requestMap.get("cantidad"));


            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);


            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else if (response.getStatusCode().is4xxClientError()) {

                return null;
            } else {

                return null;
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
