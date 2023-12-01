package com.libreria.mslogin.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@NamedQuery(name = "Usuarios.findByUsuario", query = "select u from Usuarios u where u.usuario=:usuario")

/*
 * 1= CLIENTE
 * 2= EMPLEADO*/
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    private String usuario;
    private String contrasena;
    private String rol;
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    private int estado;
}
