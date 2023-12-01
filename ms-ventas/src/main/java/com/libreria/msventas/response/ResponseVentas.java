package com.libreria.msventas.response;


import com.libreria.msventas.entity.Ventas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVentas {

    private String codigo;
    private String message;
    private List<Ventas> ventas;
}
