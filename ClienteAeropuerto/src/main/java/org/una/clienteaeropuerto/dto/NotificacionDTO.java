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
public class NotificacionDTO {
     private Long id; 
    private Date fecha_envio; 
    private Date fecha_lectura;
    private String mensaje;
    private String emisor;
    private byte estado;
    private UsuarioDTO usuarios;

    public NotificacionDTO() {
    }

    public NotificacionDTO(Long id, Date fecha_envio, Date fecha_lectura, String mensaje, String emisor, byte estado, UsuarioDTO usuarios) {
        this.id = id;
        this.fecha_envio = fecha_envio;
        this.fecha_lectura = fecha_lectura;
        this.mensaje = mensaje;
        this.emisor = emisor;
        this.estado = estado;
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(Date fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public Date getFecha_lectura() {
        return fecha_lectura;
    }

    public void setFecha_lectura(Date fecha_lectura) {
        this.fecha_lectura = fecha_lectura;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public UsuarioDTO getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(UsuarioDTO usuarios) {
        this.usuarios = usuarios;
    }
}

