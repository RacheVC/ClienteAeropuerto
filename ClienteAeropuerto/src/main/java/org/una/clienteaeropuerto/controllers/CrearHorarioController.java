/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.HorarioDTO;
import org.una.clienteaeropuerto.service.HorarioService;

/**
 * FXML Controller class
 *
 * @author Andres
 */
public class CrearHorarioController implements Initializable {

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnGuardar1;
    @FXML
    private ComboBox<String> cbDiaEntrada;
    @FXML
    private ComboBox<String> cbDiaSalida;
    @FXML
    private ComboBox<String> cbAreaTrabajo;
    
    HorarioService horarioService = new HorarioService();
    HorarioDTO horariodto = new HorarioDTO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        IniciarCombobox();
    }

    public void IniciarCombobox() {

        cbDiaEntrada.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
        cbDiaSalida.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");

    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        horariodto.setDia_Entrada(cbDiaEntrada.getValue());
        horariodto.setDia_Salida(cbDiaSalida.getValue());
        horarioService.add(horariodto);

        Parent root = FXMLLoader.load(App.class.getResource("Horario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }
}
