/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.dto;

/**
 *
 * @author Andres
 */
public class ParametroDTO {
    
    private Long id;
    private int vigenciaEnMinutos;
    private String nombre;
    private boolean estado;

    public ParametroDTO(Long id, int vigenciaEnMinutos, String nombre, boolean estado) {
        this.id = id;
        this.vigenciaEnMinutos = vigenciaEnMinutos;
        this.nombre = nombre;
        this.estado = estado;
    }

    public ParametroDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVigenciaEnMinutos() {
        return vigenciaEnMinutos;
    }

    public void setVigenciaEnMinutos(int vigenciaEnMinutos) {
        this.vigenciaEnMinutos = vigenciaEnMinutos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ParametroDTO{" + "id=" + id + ", vigenciaEnMinutos=" + vigenciaEnMinutos + ", nombre=" + nombre + ", estado=" + estado + '}';
    }
    
}
