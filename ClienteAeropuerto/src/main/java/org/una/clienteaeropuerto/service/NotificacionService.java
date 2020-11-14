/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Andres
 */
public class NotificacionService {

    private final String urlFindAll = "http://localhost:8098/notificaciones";
    private final String urlCreateModify = "http://localhost:8098/notificaciones/";

    public List<NotificacionDTO> getAll() throws InterruptedException, ExecutionException, IOException {
        return ConnectionUtils.ListFromConnection(urlFindAll, NotificacionDTO.class);
    }

    public void add(NotificacionDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreateModify, object);
    }
    
     public void modify(Long id, NotificacionDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnectionModify(urlCreateModify + id, object);
    }

    public static NotificacionService getInstance() {
        return NotificacionServiceHolder.INSTANCE;
    }

    private static class NotificacionServiceHolder {

        private static final NotificacionService INSTANCE = new NotificacionService();
    }
}
