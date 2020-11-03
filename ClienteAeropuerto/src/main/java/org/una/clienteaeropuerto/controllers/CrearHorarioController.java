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
import javafx.scene.input.MouseEvent;
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

    HorarioService horarioService = new HorarioService();
    HorarioDTO horarioDTO = new HorarioDTO();

    Areas_trabajoDTO areas_trabajoDTO = new Areas_trabajoDTO();
    AreasTrabajoService areasTrabajoService = new AreasTrabajoService();
    List<Areas_trabajoDTO> areasTrabajoList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

        IniciarCombobox();

        if (AppContext.getInstance().get("ed").equals("edit")) {
            HorarioDTO horariosDTO = new HorarioDTO();
            horariosDTO = (HorarioDTO) AppContext.getInstance().get("horarioDTO");
            cbDiaEntrada.setValue(horariosDTO.getDia_Entrada());
            cbDiaSalida.setValue(horariosDTO.getDia_Salida());
            cbAreaTrabajo.setValue(horariosDTO.getAreas_trabajo());
        }
    }

    public void IniciarCombobox() {

        cbDiaEntrada.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
        cbDiaSalida.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (!AppContext.getInstance().get("ed").equals("edit")) {
            try {
                horarioDTO.setDia_Entrada(cbDiaEntrada.getValue());
                horarioDTO.setDia_Salida(cbDiaSalida.getValue());
                horarioDTO.setEstado(true);
                horarioDTO.setAreas_trabajo(areas_trabajoDTO);
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
    private void MouseAreaTrabajo(MouseEvent event) {
        if (cbAreaTrabajo.getSelectionModel().getSelectedItem() != null) {
            areas_trabajoDTO = (Areas_trabajoDTO) cbAreaTrabajo.getSelectionModel().getSelectedItem();
        }
    }
}
