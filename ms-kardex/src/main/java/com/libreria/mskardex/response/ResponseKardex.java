package com.libreria.mskardex.response;

import com.libreria.mskardex.entity.Kardex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseKardex {
    private String codigo;
    private String message;
    private List<Kardex> productos;
}
