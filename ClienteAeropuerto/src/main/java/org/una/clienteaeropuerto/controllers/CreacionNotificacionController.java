/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class CreacionNotificacionController implements Initializable {

    @FXML
    private TextField txtReceptor;
    @FXML
    private TextField txtAsunto;
    @FXML
    private TextArea txtMensaje;
    @FXML
    private RadioButton rbSi;
    @FXML
    private DatePicker dpCalendario;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnAgregarImagen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) {
    }

    @FXML
    private void OnActionBtnAgregarImagen(ActionEvent event) {
    }
    
}
