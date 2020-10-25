/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.HorarioDTO;
import org.una.clienteaeropuerto.service.HorarioService;

/**
 * FXML Controller class
 *
 * @author Andres
 */
public class HorarioController implements Initializable {

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
    private TableView<HorarioDTO> tvewNotificacion;
    @FXML
    private TableColumn<HorarioDTO, Object> clId;
    @FXML
    private TableColumn<HorarioDTO, String> clFechaEnvio;
    @FXML
    private TableColumn<HorarioDTO, String> clFechaLectura;
    @FXML
    private TableColumn<HorarioDTO, String> clMensaje;
    @FXML
    private Button btnSalir;

    private List<HorarioDTO> horariolist = new ArrayList<HorarioDTO>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            horariolist = HorarioService.getInstance().getAll();

            // TODO
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(HorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        clId.setCellValueFactory(new PropertyValueFactory<>("id"));

        clFechaEnvio.setCellValueFactory(new PropertyValueFactory<>("Dia_Entrada"));
        clFechaLectura.setCellValueFactory(new PropertyValueFactory<>("Dia_Salida"));
        clMensaje.setCellValueFactory(new PropertyValueFactory<>("Usuarios_Areas"));

        tvewNotificacion.getItems().clear();

        tvewNotificacion.setItems(FXCollections.observableArrayList(horariolist));
        System.out.println(horariolist.get(0).getId());
        System.out.println(horariolist.get(0).getDia_Entrada());
        System.out.println(horariolist.get(0).getDia_Salida());

        System.out.println(horariolist.get(0).getUsuarios_Areas());

    }

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("CrearHorario.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionSalirrNotificacion(ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(App.class.getResource("Horario.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

}
