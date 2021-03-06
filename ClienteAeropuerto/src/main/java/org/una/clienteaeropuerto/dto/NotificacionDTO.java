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
    private Date fecha_entrega;
    private String mensaje;
    private String emisor;
    private String receptor;
    private boolean estado;
    private UsuarioDTO usuarios;

    public NotificacionDTO() {
    }

    public NotificacionDTO(Long id, Date fecha_envio, Date fecha_entrega, String mensaje, String emisor, String receptor, boolean estado, UsuarioDTO usuarios) {
        this.id = id;
        this.fecha_envio = fecha_envio;
        this.fecha_entrega = fecha_entrega;
        this.mensaje = mensaje;
        this.emisor = emisor;
        this.receptor = receptor;
        this.estado = estado;
        this.usuarios = usuarios;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
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

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
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

    @Override
    public String toString() {
        return "NotificacionDTO{" + "id=" + id + ", fecha_envio=" + fecha_envio + ", fecha_entrega=" + fecha_entrega + ", mensaje=" + mensaje + ", emisor=" + emisor + ", receptor=" + receptor + ", estado=" + estado + ", usuarios=" + usuarios + '}';
    }
}
