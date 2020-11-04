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
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.HorarioDTO;
import org.una.clienteaeropuerto.service.HorarioService;
import org.una.clienteaeropuerto.utils.AppContext;

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
    private TableView<HorarioDTO> tvewHorarios;
    @FXML
    private TableColumn<HorarioDTO, Object> clId;
    @FXML
    private TableColumn<HorarioDTO, String> clDiaEntrada;
    @FXML
    private TableColumn<HorarioDTO, String> clDiaSalida;
    @FXML
    private TableColumn<HorarioDTO, String> clAreaTrabajo;
    @FXML
    private TableColumn<HorarioDTO, String> clEstado;

    private List<HorarioDTO> horariolist = new ArrayList<HorarioDTO>();

    private List<HorarioDTO> horariolist2 = new ArrayList<HorarioDTO>();

    HorarioDTO horarioDTO = new HorarioDTO();

    HorarioService horarioService = new HorarioService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            horariolist = HorarioService.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(HorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarTableView();

    }

    @FXML
    private void accionBuscarHorario(ActionEvent event) {

    }

    @FXML
    private void accionCrearHorario(ActionEvent event) throws IOException {
        AppContext.getInstance().set("horarioDTO", horarioDTO);
        AppContext.getInstance().set("ed", "insertar");

        Parent root = FXMLLoader.load(App.class.getResource("CrearHorario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionModificarHorario(ActionEvent event) throws IOException {
        AppContext.getInstance().set("horarioDTO", horarioDTO);
        AppContext.getInstance().set("ed", "edit");

    }

    @FXML
    private void accionInactivarHorario(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
        if (horarioDTO.isEstado() == true) {
            horarioDTO.setEstado(false);
            horarioService.modify(horarioDTO.getId(), horarioDTO);
            
            Parent root = FXMLLoader.load(App.class.getResource("HorarioController.fxml"));
            Scene creacionDocs = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(creacionDocs);
            window.show();
        }
    }

    @FXML
    private void accionSalirHorario(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("Dashboard.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void MouseTabla(MouseEvent event) {
        if (tvewHorarios.getSelectionModel().getSelectedItem() != null) {
            horarioDTO = (HorarioDTO) tvewHorarios.getSelectionModel().getSelectedItem();
        }
    }

    private void actualizarTableView() {
        for (int i = 0; i < horariolist.size(); i++) {
            if (horariolist.get(i).isEstado() == true) {
                horariolist2.add(horariolist.get(i));
                clId.setCellValueFactory(new PropertyValueFactory<>("id"));
                clEstado.setCellValueFactory(per -> {
                    String estadoString;
                    if (per.getValue().isEstado()) {
                        estadoString = "Activo";
                    } else {
                        estadoString = "Inactivo";
                    }
                    return new ReadOnlyStringWrapper(estadoString);
                });
                clDiaEntrada.setCellValueFactory(new PropertyValueFactory<>("dia_Entrada"));
                clDiaSalida.setCellValueFactory(new PropertyValueFactory<>("dia_Salida"));
                clAreaTrabajo.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getAreas_trabajo()));
                tvewHorarios.getItems().clear();
                tvewHorarios.setItems(FXCollections.observableArrayList(horariolist2));
            }
        }
    }

}
