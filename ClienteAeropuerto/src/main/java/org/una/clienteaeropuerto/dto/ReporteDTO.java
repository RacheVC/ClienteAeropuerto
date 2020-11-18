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
public class ReporteDTO {
    
    private String nombreReporte;

    public ReporteDTO() {
    }

    @Override
    public String toString() {
        return "ReporteDTO{" + "nombreReporte=" + nombreReporte + '}';
    }

    public ReporteDTO(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }
    
    
}
