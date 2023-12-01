package com.libreria.msventas.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseKardex {

    private int idKardex;

    private LocalDate fecha;

    private int idProducto;

    private String tipoMovimiento;
    private int cantidad;


    private String createdBy;


    private String updatedBy;


    private Date createdAt;


    private Date updatedAt;
    private int estado;
}
