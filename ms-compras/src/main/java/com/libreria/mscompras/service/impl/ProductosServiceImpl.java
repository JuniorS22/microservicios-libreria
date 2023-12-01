package com.libreria.mscompras.service.impl;

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
public class ProductosServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public String actualizarStockProducto(Integer id,Map<String, String> requestMap, String token) {

        try {
            //String token = Utils.devuelveToken(httpServletRequest);
            // Define la URL de la API a la que deseas acceder
            String apiUrl = "http://localhost:8080/api/productos/actualizaStockProducto/"+ id;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            Map<String, String> body = new HashMap<>();

            body.put("tipoMovimiento", "Entrada");
            body.put("cantidad", requestMap.get("cantidad"));

            // Configura la solicitud con el encabezado
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            // Realiza la solicitud HTTP GET a la API con el encabezado de autorización
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.PUT, entity, String.class);

            // Verifica si la solicitud fue exitosa (código de estado 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // Devuelve el objeto Usuario
            } else if (response.getStatusCode().is4xxClientError()) {
                // Maneja errores del cliente (por ejemplo, 404 Not Found)
                return null; // O cualquier otro manejo de error que necesites
            } else {
                // Maneja el caso en el que la solicitud no fue exitosa
                return null; // O cualquier otro manejo de error que necesites
            }
        } catch (HttpClientErrorException.NotFound ex) {
            // Maneja específicamente el error 404 si ocurriera
            System.out.println("Error 404: Recurso no encontrado");
            return null; // Otra forma de manejo de error
        } catch (Exception ex) {
            // Maneja otras excepciones generales, como problemas de red, etc.
            System.out.println("Error inesperado: " + ex.getMessage());
            return null; // Otra forma de manejo de errores generales}
        }
    }
}
