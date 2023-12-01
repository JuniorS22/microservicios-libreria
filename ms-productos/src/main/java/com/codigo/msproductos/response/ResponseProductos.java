package com.codigo.msproductos.response;

import com.codigo.msproductos.entity.Productos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductos {
    private String codigo;
    private String message;
    private List<Productos>productos;
}
