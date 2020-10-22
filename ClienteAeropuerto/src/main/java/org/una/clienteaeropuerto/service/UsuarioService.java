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
     private final String urlstring = "http://localhost:8098/usuarios/";
    private final String urlstringLogin = "http://localhost:8098/authentication/login";
    private final String urlstringNombre = "http://localhost:8098/usuarios/Usuario%20admin";
    private final String urlstringCedula = "http://localhost:8098/usuarios/cedula/A";
    UsuarioDTO datos = new UsuarioDTO();

    private UsuarioService() {
    }

//    public List<UsuarioDTO> getAll() throws InterruptedException, ExecutionException, IOException {
//        return ConnectionUtils.ListFromConnection(urlstring, UsuarioDTO.class);
//    }

    public void add(UsuarioDTO object) throws InterruptedException, ExecutionException, IOException {
        ConnectionUtils.ObjectToConnection(urlstring, object);
    }

//    public List<UsuarioDTO> getUserById(Long id) throws IOException {
//        return ConnectionUtils.ListFromConnection(urlstring, UsuarioDTO.class);
//    }

    public List<UsuarioDTO> finByNombre(String Nombre) throws IOException {
        return ConnectionUtils.ConnectionToObjectByField(urlstring, Nombre);
    }

    public AuthenticationResponse Login(AuthenticationRequest object) throws InterruptedException, ExecutionException, IOException {
        return (AuthenticationResponse) ConnectionUtils.ObjectToConnectionLogin(urlstringLogin, object);
       
    }

    public static UsuarioService getInstance() {
        return UsuarioServiceHolder.INSTANCE;
    }

    private static class UsuarioServiceHolder {

        private static final UsuarioService INSTANCE = new UsuarioService();
    }

    public List<UsuarioDTO> finByCedula(String Cedula) throws IOException {
        Cedula = String.valueOf(datos.getCedula());
        return ConnectionUtils.ConnectionToObjectByCedula(urlstringCedula,Cedula);
    }

}
