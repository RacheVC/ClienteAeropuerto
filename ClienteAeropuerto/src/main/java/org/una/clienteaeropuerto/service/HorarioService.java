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

    private final String urlFindAll = "http://localhost:8098/horarios";
    private final String urlCreateModify = "http://localhost:8098/horarios/";
    private final String urlFindDiaEntrada = "http://localhost:8098/horarios/diaEntrada/";
    private final String urlFindDiaSalida = "http://localhost:8098/horarios/diaSalida/";

    public List<HorarioDTO> getAll() throws InterruptedException, ExecutionException, IOException {
        return ConnectionUtils.ListFromConnectionHorario(urlFindAll, HorarioDTO.class);
    }

    public void add(HorarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreateModify, object);
    }

    public void modify(Long id, HorarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnectionModify(urlCreateModify + id, object);
    }

    public static HorarioService getInstance() {
        return HorarioServiceHolder.INSTANCE;
    }

    private static class HorarioServiceHolder {

        private static final HorarioService INSTANCE = new HorarioService();
    }

    public List<HorarioDTO> finByDiaEntrada(String diaEntrada) throws IOException {

        return ConnectionUtils.ConnectionToObjectByDias(urlFindDiaEntrada, diaEntrada);
    }

    public List<HorarioDTO> finByDiaSalida(String diaSalida) throws IOException {

        return ConnectionUtils.ConnectionToObjectByDias(urlFindDiaSalida, diaSalida);
    }

}
