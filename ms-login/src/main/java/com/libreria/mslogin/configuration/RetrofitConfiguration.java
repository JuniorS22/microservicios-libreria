package com.libreria.mslogin.configuration;

import com.libreria.mslogin.service.ApiExternaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfiguration {

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.apis.net.pe")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public ApiExternaService reniecApiService() {
        return retrofit().create(ApiExternaService.class);
    }
}
