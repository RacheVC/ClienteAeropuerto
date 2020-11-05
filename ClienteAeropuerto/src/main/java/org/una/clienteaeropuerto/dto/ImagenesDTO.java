/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.dto;

/**
 *
 * @author rache
 */
public class ImagenesDTO {

    private Long id;
    private String imagen_Adjunta;
    private int parte;
    private int totalPartes;
    private NotificacionDTO notificaciones;

    public ImagenesDTO(Long id, String imagen_Adjunta, int parte, int totalPartes, NotificacionDTO notificaciones) {
        this.id = id;
        this.imagen_Adjunta = imagen_Adjunta;
        this.parte = parte;
        this.totalPartes = totalPartes;
        this.notificaciones = notificaciones;
    }

    public ImagenesDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen_Adjunta() {
        return imagen_Adjunta;
    }

    public void setImagen_Adjunta(String imagen_Adjunta) {
        this.imagen_Adjunta = imagen_Adjunta;
    }

    public int getParte() {
        return parte;
    }

    public void setParte(int parte) {
        this.parte = parte;
    }

    public int getTotalPartes() {
        return totalPartes;
    }

    public void setTotalPartes(int totalPartes) {
        this.totalPartes = totalPartes;
    }

    public NotificacionDTO getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(NotificacionDTO notificaciones) {
        this.notificaciones = notificaciones;
    }

    
}
