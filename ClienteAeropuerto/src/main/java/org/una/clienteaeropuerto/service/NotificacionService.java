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
    
        List<NotificacionDTO> listanot;
        NotificacionDTO ntf;
    
    private final String urlstring ="http://localhost:8098/notificaciones";
     public List<NotificacionDTO> getAll() throws InterruptedException, ExecutionException, IOException {
         listanot =ConnectionUtils.ListFromConnection(urlstring, NotificacionDTO.class);
         System.out.println("AAAAAA"+listanot);
        
        return ConnectionUtils.ListFromConnection(urlstring, NotificacionDTO.class);
    }
     
     public static NotificacionService getInstance() {
        return NotificacionServiceHolder.INSTANCE;
    }

    private static class NotificacionServiceHolder {

        private static final NotificacionService INSTANCE = new NotificacionService();
    }
}
