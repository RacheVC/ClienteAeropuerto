/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class DashboardController implements Initializable {

    @FXML
    private Button btnControlEmpleados;
    @FXML
    private Button btnControlHorarios;
    @FXML
    private Button btnControlNotificaciones;
    @FXML
    private Button btnControlMarcas;
    @FXML
    private Button btnControlParametros;
    @FXML
    private Button btnReporteNotificaciones;
    @FXML
    private Button btnReporteHorasLaboradas;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lbNombreUsuario;

    UsuarioDTO usuarioDTO = new UsuarioDTO();

    CambiarVentana cambiarVentana = new CambiarVentana();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        capturarNombreUsuario();
//        ValidacionPermisos();
    }

    @FXML
    private void actionControlEmpleados(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("MantenimientoUsuarios", event);
    }

    @FXML
    private void onActionControlHorarios(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Horario", event);
    }

    @FXML
    private void actionControlNotificaciones(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("MantenimientoNotificaciones", event);
    }

    @FXML
    private void actionControlMarcas(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("ControlMarcasHorario", event);
    }

    @FXML
    private void actionControlParametros(ActionEvent event) {
    }

    @FXML
    private void actionReporteNotificaciones(ActionEvent event) {
    }

    @FXML
    private void actionReporteHorasLaboradas(ActionEvent event) {
    }

    @FXML
    private void actionCerrarSesion(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Login", event);
    }

    private void ValidacionPermisos() {

        if ("Administrador".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnControlNotificaciones.setDisable(true);
        } else if ("Gestor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnControlParametros.setDisable(true);
            btnControlNotificaciones.setDisable(true);
        } else if ("Auditor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnControlParametros.setDisable(true);
            btnControlNotificaciones.setDisable(true);
        }
    }

    private void capturarNombreUsuario() {

        lbNombreUsuario.setText(AuthenticationSingleton.getInstance().getUsuario().getNombreCompleto());
    }
}
