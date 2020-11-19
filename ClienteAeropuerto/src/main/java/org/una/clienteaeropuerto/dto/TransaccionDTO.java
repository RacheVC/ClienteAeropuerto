/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.dto;

import java.util.Date;

/**
 *
 * @author Andres
 */
public class TransaccionDTO {
    private Long id;
    private String nombre;
    private Date fecha_registro;
    private boolean estado;
    private UsuarioDTO usuarios;

    
    
     public TransaccionDTO() {
    }

    
    public TransaccionDTO(Long id, String nombre, Date fecha_registro, boolean estado, UsuarioDTO usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_registro = fecha_registro;
        this.estado = estado;
        this.usuarios = usuarios;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public UsuarioDTO getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(UsuarioDTO usuarios) {
        this.usuarios = usuarios;
    }

   
}
