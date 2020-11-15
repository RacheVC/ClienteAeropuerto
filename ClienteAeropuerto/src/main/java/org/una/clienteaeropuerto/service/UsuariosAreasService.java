/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.Usuarios_AreasDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Luis
 */
public class UsuariosAreasService {
    
    private final String urlFindAll = "http://localhost:8098/usuarios_areas";
    private final String urlCreate = "http://localhost:8098/usuarios_areas/";
    
    public List<Usuarios_AreasDTO> getAll() throws InterruptedException, ExecutionException, IOException {
        return ConnectionUtils.ListFromConnectionUsuariosAreas(urlFindAll, Usuarios_AreasDTO.class);
    }
    
     public void add(Usuarios_AreasDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreate, object);
    }
    
    public static UsuariosAreasService getInstance() {
        return UsuariosAreasServiceHolder.INSTANCE;
    }
    
     private static class UsuariosAreasServiceHolder {

        private static final UsuariosAreasService INSTANCE = new UsuariosAreasService();
    }
}
