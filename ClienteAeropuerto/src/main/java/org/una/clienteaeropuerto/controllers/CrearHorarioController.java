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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.Areas_trabajoDTO;
import org.una.clienteaeropuerto.dto.HorarioDTO;
import org.una.clienteaeropuerto.service.AreasTrabajoService;
import org.una.clienteaeropuerto.service.HorarioService;
import org.una.clienteaeropuerto.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Andres
 */
public class CrearHorarioController implements Initializable {

    @FXML
    private ComboBox<String> cbDiaEntrada;
    @FXML
    private ComboBox<String> cbDiaSalida;
    @FXML
    private ComboBox<Areas_trabajoDTO> cbAreaTrabajo;
    DatePicker datepicker;
    HorarioService horarioService = new HorarioService();
    HorarioDTO horarioDTO = new HorarioDTO();
    java.util.Date date = new java.util.Date();
    java.util.Date date2 = new java.util.Date();
    Areas_trabajoDTO areas_trabajoDTO = new Areas_trabajoDTO();
    AreasTrabajoService areasTrabajoService = new AreasTrabajoService();
    List<Areas_trabajoDTO> areasTrabajoList = new ArrayList<>();
    @FXML
    private ComboBox<String> cbHoraEntrada;
    @FXML
    private ComboBox<String> cbMinutoEntrada;
    @FXML
    private ComboBox<String> cbHoraSalida;
    @FXML
    private ComboBox<String> cbMinutoSalida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        llenarCbAreaTrabajo();
        funcionAppContext();
    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (!AppContext.getInstance().get("ed").equals("edit")) {
            try {
                date.setHours(Integer.valueOf(cbHoraEntrada.getValue()));
                date.setMinutes(Integer.valueOf(cbMinutoEntrada.getValue()));
                date2.setHours(Integer.valueOf(cbHoraSalida.getValue()));
                date2.setMinutes(Integer.valueOf(cbMinutoSalida.getValue()));
                horarioDTO.setDia_Entrada(cbDiaEntrada.getValue());
                horarioDTO.setDia_Salida(cbDiaSalida.getValue());
                horarioDTO.setEstado(true);
                horarioDTO.setAreas_trabajo(areas_trabajoDTO);
                horarioDTO.setHora_entrada(date);
                horarioDTO.setHora_salida(date2);
                horarioService.add(horarioDTO);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("El horario fue creado con éxito.");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Error");
                alert.setHeaderText("El horario no se pudo crear.");
                alert.show();
                System.out.println(e);
            }

        } else {
            try {
                horarioDTO = (HorarioDTO) AppContext.getInstance().get("horarioDTO");
                horarioDTO.setDia_Entrada(cbDiaEntrada.getValue());
                horarioDTO.setDia_Salida(cbDiaSalida.getValue());
                horarioDTO.setEstado(true);
                horarioService.modify(horarioDTO.getId(), horarioDTO);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("El horario fue modificado con éxito.");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Error");
                alert.setHeaderText("El horario no se pudo modificar.");
                alert.show();
            }

        }

    }

    @FXML
    private void OnActionBtnAtras(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("Horario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void actionCbxAreaTrabajo(ActionEvent event) {
        if (cbAreaTrabajo.getSelectionModel().getSelectedItem() != null) {
            areas_trabajoDTO = (Areas_trabajoDTO) cbAreaTrabajo.getSelectionModel().getSelectedItem();
        }
    }

    private void llenarCbAreaTrabajo() {
        try {
            areasTrabajoList = (List<Areas_trabajoDTO>) areasTrabajoService.getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(CrearHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(CrearHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrearHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbAreaTrabajo.setItems(FXCollections.observableArrayList(areasTrabajoList));
    }

    private void funcionAppContext() {
        if (AppContext.getInstance().get("ed").equals("edit")) {
            HorarioDTO horarioDTO = new HorarioDTO();
            horarioDTO = (HorarioDTO) AppContext.getInstance().get("horarioDTO");
            cbDiaEntrada.setValue(horarioDTO.getDia_Entrada());
            cbDiaEntrada.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
            cbDiaSalida.setValue(horarioDTO.getDia_Salida());
            cbDiaSalida.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
            cbHoraEntrada.setValue(String.valueOf(horarioDTO.getHora_entrada().getHours()));
            cbHoraSalida.setValue(String.valueOf(horarioDTO.getHora_salida().getHours()));
            cbMinutoEntrada.setValue(String.valueOf(horarioDTO.getHora_entrada().getMinutes()));
            cbMinutoSalida.setValue(String.valueOf(horarioDTO.getHora_salida().getMinutes()));
            LLenarComboboxHoras();
            horarioDTO.setEstado(true);
            cbAreaTrabajo.setValue(horarioDTO.getAreas_trabajo());
        } else {
            if (AppContext.getInstance().get("ed").equals("insertar")) {
                LLenarComboboxHoras();
                cbDiaEntrada.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
                cbDiaSalida.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
            }
        }
    }

    public void LLenarComboboxHoras() {
        for (int i = 1; i < 25; i++) {
            if (i < 10) {
                cbHoraEntrada.getItems().addAll("0" + String.valueOf(i));
                cbHoraSalida.getItems().addAll("0" + String.valueOf(i));
            } else {
                cbHoraEntrada.getItems().addAll(String.valueOf(i));
                cbHoraSalida.getItems().addAll(String.valueOf(i));
            }

        }

        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                cbMinutoEntrada.getItems().addAll("0" + String.valueOf(i));
                cbMinutoSalida.getItems().addAll("0" + String.valueOf(i));
            } else {
                cbMinutoEntrada.getItems().addAll(String.valueOf(i));
                cbMinutoSalida.getItems().addAll(String.valueOf(i));
            }

        }
    }

    @FXML
    private void actioncbHoraEntrada(ActionEvent event) {
    }

    @FXML
    private void actioncbMinutoEntrada(ActionEvent event) {
    }

    @FXML
    private void actioncbHoraSalida(ActionEvent event) {
    }

    @FXML
    private void actioncbMinutoSalida(ActionEvent event) {
    }

}
