/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.ParametroDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Andres
 */
public class ParametrosService {
    
    private final String urlCreateModify = "http://localhost:8098/parametros/";
    private final String urlFindAll = "http://localhost:8098/parametros";;
    
    
     public List<ParametroDTO> getAll() throws InterruptedException, ExecutionException, IOException {
        return ConnectionUtils.ListFromConnectionParametros(urlFindAll, ParametroDTO.class);
    }
    
     public void add(ParametroDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreateModify, object);
    }
    
     
     public void modify(Long id, ParametroDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnectionModify(urlCreateModify + id, object);
    }
     
    public static ParametrosService getInstance() {
        return ParametrosServiceHolder.INSTANCE;
    }
    
     private static class ParametrosServiceHolder {

        private static final ParametrosService INSTANCE = new ParametrosService();
    }
}
