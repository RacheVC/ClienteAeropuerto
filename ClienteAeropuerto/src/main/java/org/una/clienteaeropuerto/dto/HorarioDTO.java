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
    private Usuarios_AreasDTO Usuarios_Areas;

    public HorarioDTO() {
    }

    @Override
    public String toString() {
        return "HorarioDTO{" + "id=" + id + ", Dia_Entrada=" + dia_Entrada + ", Dia_Salida=" + dia_Salida + ", Usuarios_Areas=" + Usuarios_Areas + '}';
    }

    public HorarioDTO(Long id, String Dia_Entrada, String Dia_Salida, Usuarios_AreasDTO Usuarios_Areas) {
        this.id = id;
        this.dia_Entrada = Dia_Entrada;
        this.dia_Salida = Dia_Salida;
        this.Usuarios_Areas = Usuarios_Areas;
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

    public Usuarios_AreasDTO getUsuarios_Areas() {
        return Usuarios_Areas;
    }

    public void setUsuarios_Areas(Usuarios_AreasDTO Usuarios_Areas) {
        this.Usuarios_Areas = Usuarios_Areas;
    }

}
