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
public class HorarioDTO {

    private Long id;
    private String diaEntrada;
    private String diaSalida;
    private boolean estado;
    private Date hora_entrada;
    private Date hora_salida;
    private Areas_trabajoDTO areas_trabajo;

    public HorarioDTO() {
    }

    public HorarioDTO(Long id, String diaEntrada, String diaSalida, boolean estado, Date hora_entrada, Date hora_salida, Areas_trabajoDTO areas_trabajo) {
        this.id = id;
        this.diaEntrada = diaEntrada;
        this.diaSalida = diaSalida;
        this.estado = estado;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.areas_trabajo = areas_trabajo;
    }

    @Override
    public String toString() {
        return diaEntrada ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiaEntrada() {
        return diaEntrada;
    }

    public void setDiaEntrada(String diaEntrada) {
        this.diaEntrada = diaEntrada;
    }

    public String getDiaSalida() {
        return diaSalida;
    }

    public void setDiaSalida(String diaSalida) {
        this.diaSalida = diaSalida;
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

    public Date getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(Date hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public Date getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(Date hora_salida) {
        this.hora_salida = hora_salida;
    }

}
