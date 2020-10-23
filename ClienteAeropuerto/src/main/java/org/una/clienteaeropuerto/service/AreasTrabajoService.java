/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.Areas_trabajoDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Luis
 */
public class AreasTrabajoService {
   
    List<Areas_trabajoDTO> areasTrabajoDTO;
    
    private final String urlFindAll ="http://localhost:8098/areas_trabajo";
    private final String urlCreate ="http://localhost:8098/areas_trabajo/";
    
    public List<Areas_trabajoDTO> getAll() throws InterruptedException, ExecutionException, IOException {
         areasTrabajoDTO =ConnectionUtils.ListFromConnectionAT(urlFindAll, Areas_trabajoDTO.class);
       
        
        return ConnectionUtils.ListFromConnectionAT(urlFindAll, Areas_trabajoDTO.class);
    }
     
     
        public void add(Areas_trabajoDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreate, object);
    }
     
     public static AreasTrabajoService getInstance() {
        return AreasTrabajoServiceHolder.INSTANCE;
    }

    private static class AreasTrabajoServiceHolder {

        private static final AreasTrabajoService INSTANCE = new AreasTrabajoService();
    }
}
