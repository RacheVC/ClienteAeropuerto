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
public class HorarioDTO {

    private Long id;
    private String dia_Entrada;
    private String dia_Salida;
    private boolean estado;
    private Areas_trabajoDTO areas_trabajo;

    public HorarioDTO() {
    }

    @Override
    public String toString() {
        return "HorarioDTO{" + "id=" + id + ", dia_Entrada=" + dia_Entrada + ", dia_Salida=" + dia_Salida + ", areas_trabajo=" + areas_trabajo + '}';
    }

    public HorarioDTO(Long id, String dia_Entrada, String dia_Salida, Areas_trabajoDTO areas_trabajo) {
        this.id = id;
        this.dia_Entrada = dia_Entrada;
        this.dia_Salida = dia_Salida;
        this.areas_trabajo = areas_trabajo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDia_Entrada() {
        return dia_Entrada;
    }

    public void setDia_Entrada(String Dia_Entrada) {
        this.dia_Entrada = Dia_Entrada;
    }

    public String getDia_Salida() {
        return dia_Salida;
    }

    public void setDia_Salida(String Dia_Salida) {
        this.dia_Salida = Dia_Salida;
    }

    public Areas_trabajoDTO getAreas_trabajo() {
        return areas_trabajo;
    }

    public void setAreas_trabajo(Areas_trabajoDTO areas_trabajo) {
        this.areas_trabajo = areas_trabajo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
