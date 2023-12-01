package com.libreria.mscompras.response;


import com.libreria.mscompras.entity.Compras;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCompras {

    private String codigo;
    private String message;
    private List<Compras> ventas;
}
