/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.DivisaDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Andres
 */
public class DivisaService {
    private final String urlFindAll = "https://www.freeforexapi.com/api/live?pairs=EURUSD";
   
      public Object getAll() throws InterruptedException, ExecutionException, IOException {
        return ConnectionUtils.ListFromConnectionDivisa(urlFindAll, DivisaDTO.class);
    }
    
    public static DivisaService getInstance() {
        return DivisaServiceHolder.INSTANCE;
    }

    private static class DivisaServiceHolder {

        private static final DivisaService INSTANCE = new DivisaService();
    }
}
