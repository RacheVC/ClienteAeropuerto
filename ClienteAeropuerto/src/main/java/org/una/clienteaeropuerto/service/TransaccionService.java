/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Andres
 */
public class TransaccionService {
    
    private final String urlFindAll = "http://localhost:8098/usuarios";
     private final String urlCreate = "http://localhost:8098/transacciones/";
//     public List<TransaccionDTO> getAll() throws InterruptedException, ExecutionException, IOException {
//        return ConnectionUtils.ListFromConnectionUsuario(urlFindAll, TransaccionDTO.class);
//    }
    
    public void add(TransaccionDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreate, object);
    }
    
      public static TransaccionService getInstance() {
        return TransaccionServiceHolder.INSTANCE;
    }
        
        private static class TransaccionServiceHolder {

        private static final TransaccionService INSTANCE = new TransaccionService();
    }
}
