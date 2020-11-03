/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author rache
 */
public class ImagenService {
       List<ImagenesDTO> listaimagenes;

    private final String urlstring = "http://localhost:8098/imagenes";
    private final String urlCreate = "http://localhost:8098/imagenes/";

    public List<ImagenesDTO> getAll() throws InterruptedException, ExecutionException, IOException {
        listaimagenes = ConnectionUtils.ListFromConnectionImagen(urlstring, ImagenesDTO.class);

        return ConnectionUtils.ListFromConnectionImagen(urlstring, ImagenesDTO.class);
    }

    public void add(ImagenesDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreate, object);
    }

    public static NotificacionService getInstance() {
        return NotificacionServiceHolder.INSTANCE;
    }

    private static class NotificacionServiceHolder {

        private static final NotificacionService INSTANCE = new NotificacionService();
    }
}
