/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.RolesDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Luis
 */
public class RolesService {

    List<RolesDTO> rolesDTO;

    private final String urlFindAll = "http://localhost:8098/roles";
    private final String urlCreate = "http://localhost:8098/roles/";

    public List<RolesDTO> getAll() throws InterruptedException, ExecutionException, IOException {
        rolesDTO = ConnectionUtils.ListFromConnectionRol(urlFindAll, RolesDTO.class);

        return ConnectionUtils.ListFromConnectionRol(urlFindAll, RolesDTO.class);
    }

    public void add(RolesDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreate, object);
    }

    public static RolesService getInstance() {
        return RolesServiceHolder.INSTANCE;
    }

    private static class RolesServiceHolder {

        private static final RolesService INSTANCE = new RolesService();
    }
}


