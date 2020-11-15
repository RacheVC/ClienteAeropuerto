/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.dto;

import java.util.Date;

/**
 *
 * @author Luis
 */
public class MarcaHorarioDTO {

    private Long id;
    private Date marca_entrada;
    private Date marca_salida;
    private boolean estado;
    private Usuarios_AreasDTO usuariosAreas;

    public MarcaHorarioDTO() {
    }

    public MarcaHorarioDTO(Long id, Date marca_entrada, Date marca_salida, boolean estado, Usuarios_AreasDTO usuariosAreas) {
        this.id = id;
        this.marca_entrada = marca_entrada;
        this.marca_salida = marca_salida;
        this.estado = estado;
        this.usuariosAreas = usuariosAreas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMarca_entrada() {
        return marca_entrada;
    }

    public void setMarca_entrada(Date marca_entrada) {
        this.marca_entrada = marca_entrada;
    }

    public Date getMarca_salida() {
        return marca_salida;
    }

    public void setMarca_salida(Date marca_salida) {
        this.marca_salida = marca_salida;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Usuarios_AreasDTO getUsuariosAreas() {
        return usuariosAreas;
    }

    public void setUsuariosAreas(Usuarios_AreasDTO usuariosAreas) {
        this.usuariosAreas = usuariosAreas;
    }

    @Override
    public String toString() {
        return "MarcaHorarioDTO{" + "id=" + id + ", marca_entrada=" + marca_entrada + ", marca_salida=" + marca_salida + ", estado=" + estado + ", usuariosAreas=" + usuariosAreas + '}';
    }

}
