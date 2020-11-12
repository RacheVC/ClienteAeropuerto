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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;

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

    UsuarioDTO usuarioDTO = new UsuarioDTO();
    @FXML
    private Label lbNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        capturarNombreUsuario();
        ValidacionPermisos();
    }

    @FXML
    private void actionControlEmpleados(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoUsuarios.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void onActionControlHorarios(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("Horario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void actionControlNotificaciones(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void actionControlMarcas(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("ControlMarcasHorario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
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

        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
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
    
    private void capturarNombreUsuario(){
        
        AuthenticationSingleton.getInstance().getUsuario().getNombreCompleto();
        lbNombreUsuario.setText(AuthenticationSingleton.getInstance().getUsuario().getNombreCompleto());
    }
}
