package com.libreria.mslogin.service;

import com.libreria.mslogin.response.ResponseDNI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiExternaService {

    @GET("/v2/reniec/dni")
    Call<ResponseDNI> consultarDNI(@Query("numero") String numero, @Header("Authorization") String token);
}
