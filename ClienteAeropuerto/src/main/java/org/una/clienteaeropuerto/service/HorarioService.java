/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.HorarioDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Andres
 */
public class HorarioService {
    
    private final String urlstring = "http://localhost:8098/horarios";
    private final String urlCreate = "http://localhost:8098/horarios/";
    
      public List<HorarioDTO> getAll() throws InterruptedException, ExecutionException, IOException {
       

        return ConnectionUtils.ListFromConnectionHorario(urlstring, HorarioDTO.class);
    }

    public void add(HorarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreate, object);
    }

    public static HorarioService getInstance() {
        return HorarioServiceHolder.INSTANCE;
    }

    private static class HorarioServiceHolder {

        private static final HorarioService INSTANCE = new HorarioService();
    }
}
