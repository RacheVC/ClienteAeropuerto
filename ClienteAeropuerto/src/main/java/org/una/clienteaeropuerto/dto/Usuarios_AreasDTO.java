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
public class Usuarios_AreasDTO {
      private Long id;
    private Areas_trabajoDTO areas_trabajo;
    private UsuarioDTO usuarios;

    public Usuarios_AreasDTO() {
    }

    public Usuarios_AreasDTO(Long id, Areas_trabajoDTO areas_trabajo, UsuarioDTO usuarios) {
        this.id = id;
        this.areas_trabajo = areas_trabajo;
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Areas_trabajoDTO getAreas_trabajo() {
        return areas_trabajo;
    }

    public void setAreas_trabajo(Areas_trabajoDTO areas_trabajo) {
        this.areas_trabajo = areas_trabajo;
    }

    public UsuarioDTO getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(UsuarioDTO usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public String toString() {
        return "Usuarios_AreasDTO{" + "id=" + id + ", areas_trabajo=" + areas_trabajo + ", usuarios=" + usuarios + '}';
    }
    
}
