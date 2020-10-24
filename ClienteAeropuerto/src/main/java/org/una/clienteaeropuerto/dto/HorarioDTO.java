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
    private int Dia_Entrada;
    private int Dia_Salida;
    private UsuarioAreaDTO Usuarios_Areas;

    public HorarioDTO() {
    }

    public HorarioDTO(Long id, int Dia_Entrada, int Dia_Salida, UsuarioAreaDTO Usuarios_Areas) {
        this.id = id;
        this.Dia_Entrada = Dia_Entrada;
        this.Dia_Salida = Dia_Salida;
        this.Usuarios_Areas = Usuarios_Areas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDia_Entrada() {
        return Dia_Entrada;
    }

    public void setDia_Entrada(int Dia_Entrada) {
        this.Dia_Entrada = Dia_Entrada;
    }

    public int getDia_Salida() {
        return Dia_Salida;
    }

    public void setDia_Salida(int Dia_Salida) {
        this.Dia_Salida = Dia_Salida;
    }

    public UsuarioAreaDTO getUsuarios_Areas() {
        return Usuarios_Areas;
    }

    public void setUsuarios_Areas(UsuarioAreaDTO Usuarios_Areas) {
        this.Usuarios_Areas = Usuarios_Areas;
    }
    
    
}
