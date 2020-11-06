/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.una.clienteaeropuerto.dto.AuthenticationRequest;
import org.una.clienteaeropuerto.dto.AuthenticationResponse;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Andres
 */
public class UsuarioService {

    private final String urlCreate = "http://localhost:8098/usuarios/";
    private final String urlFindAll = "http://localhost:8098/usuarios";
    private final String urlstringLogin = "http://localhost:8098/authentication/login";
    private final String urlstringCedula = "http://localhost:8098/usuarios/cedula/";
    private final String urlModify = "http://localhost:8098/usuarios/";
    private final String urlDelete = "http://localhost:8098/usuarios/";
    private final String urlFindNombre = "http://localhost:8098/usuarios/nombreCompleto/";

    UsuarioDTO datos = new UsuarioDTO();

    public UsuarioService() {
    }

    public List<UsuarioDTO> getAll() throws InterruptedException, ExecutionException, IOException {
        return ConnectionUtils.ListFromConnectionUsuario(urlFindAll, UsuarioDTO.class);
    }

    public void add(UsuarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlCreate, object);
    }

    public AuthenticationResponse Login(AuthenticationRequest object) throws InterruptedException, ExecutionException, IOException {
        return (AuthenticationResponse) ConnectionUtils.ObjectToConnectionLogin(urlstringLogin, object);

    }

    public static UsuarioService getInstance() {
        return UsuarioServiceHolder.INSTANCE;
    }

    public void modify(Long id, UsuarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnectionModify(urlModify + id, object);
    }

    public void delete(Long id) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnectionDelete(urlDelete + id, null);
    }

    private static class UsuarioServiceHolder {

        private static final UsuarioService INSTANCE = new UsuarioService();
    }

    public List<UsuarioDTO> finByCedula(String Cedula) throws IOException {

        return ConnectionUtils.ConnectionToObjectByCedula(urlstringCedula, Cedula);
    }
    
    public List<UsuarioDTO> finByNombre(String nombre) throws IOException {

        return ConnectionUtils.ConnectionToObjectByNombre(urlFindNombre, nombre);
    }

}
