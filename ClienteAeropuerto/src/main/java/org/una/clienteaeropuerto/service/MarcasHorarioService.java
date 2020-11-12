/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.MarcaHorarioDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Luis
 */
public class MarcasHorarioService {

    private final String urlFindAll = "http://localhost:8098/marcas_horario";
    private final String urlCreateModify = "http://localhost:8098/marcas_horario/";

    public List<MarcaHorarioDTO> getAll() throws InterruptedException, ExecutionException, IOException {
           return ConnectionUtils.ListFromConnectionMarcas(urlFindAll, MarcaHorarioDTO.class);
    }

    public void add(MarcaHorarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreateModify, object);
    }

    public void modify(Long id, MarcaHorarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnectionModify(urlCreateModify + id, object);
    }

    public static MarcasHorarioService getInstance() {
        return MarcasHorarioServiceHolder.INSTANCE;
    }

    private static class MarcasHorarioServiceHolder {

        private static final MarcasHorarioService INSTANCE = new MarcasHorarioService();
    }
}
