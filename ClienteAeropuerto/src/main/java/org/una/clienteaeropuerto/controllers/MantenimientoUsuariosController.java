/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.una.clienteaeropuerto.dto.UsuarioDTO;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class MantenimientoUsuariosController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;
    @FXML
    private TableView<?> tvewNotificacion;
    @FXML
    private TableColumn<UsuarioDTO, Object> clId;
    @FXML
    private TableColumn<UsuarioDTO, Date> clFechaEnvio;
    @FXML
    private TableColumn<?, ?> clFechaLectura;
    @FXML
    private TableColumn<?, ?> clMensaje;
    @FXML
    private TableColumn<?, ?> clEmisor;
    @FXML
    private TableColumn<?, ?> clEstado;
    @FXML
    private TableColumn<?, ?> clReceptor;
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionSalirrNotificacion(ActionEvent event) {
    }
    
}
