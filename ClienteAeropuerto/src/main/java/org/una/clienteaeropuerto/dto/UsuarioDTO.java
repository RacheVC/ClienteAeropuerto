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
public class UsuarioDTO {
    private Long id; 
    private String nombreCompleto;   
    private String cedula;
    private String correo;
    private String contrasenaEncriptada;
    private boolean estado; 
    private Date fecha_registro; 
    private Long empleadoId; 
    private Long rolesId;
    
   
    public UsuarioDTO(Long id, String nombreCompleto, String cedula, String correo, boolean estado, Date fecha_registro, Long empleadoId, Long rolesId, String contrasenaEncriptada) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.correo = correo;
        this.estado = estado;
        this.fecha_registro = fecha_registro;
        this.empleadoId = empleadoId;
        this.rolesId = rolesId;
        this.contrasenaEncriptada = contrasenaEncriptada;
    }

    public String getContrasenaEncriptada() {
        return contrasenaEncriptada;
    }

    public void setContrasenaEncriptada(String contrasenaEncriptada) {
        this.contrasenaEncriptada = contrasenaEncriptada;
    }

    public UsuarioDTO() {
   }

 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Long getRolesId() {
        return rolesId;
    }

    public void setRolesId(Long rolesId) {
        this.rolesId = rolesId;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "fecha_registro=" + fecha_registro + '}';
    }
    
}