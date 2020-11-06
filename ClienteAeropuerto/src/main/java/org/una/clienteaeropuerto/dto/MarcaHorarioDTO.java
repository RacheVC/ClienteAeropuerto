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
    private Areas_trabajoDTO areas_trabajo;

    public MarcaHorarioDTO() {
    }

    public MarcaHorarioDTO(Long id, Date marca_entrada, Date marca_salida, boolean estado, Areas_trabajoDTO areas_trabajo) {
        this.id = id;
        this.marca_entrada = marca_entrada;
        this.marca_salida = marca_salida;
        this.estado = estado;
        this.areas_trabajo = areas_trabajo;
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

    public Areas_trabajoDTO getAreas_trabajo() {
        return areas_trabajo;
    }

    public void setAreas_trabajo(Areas_trabajoDTO areas_trabajo) {
        this.areas_trabajo = areas_trabajo;
    }

    @Override
    public String toString() {
        return "MarcaHorarioDTO{" + "id=" + id + ", marca_entrada=" + marca_entrada + ", marca_salida=" + marca_salida + ", estado=" + estado + ", Areas_trabajo=" + areas_trabajo + '}';
    }

}
