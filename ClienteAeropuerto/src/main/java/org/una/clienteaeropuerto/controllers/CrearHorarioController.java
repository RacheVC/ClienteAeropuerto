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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.una.clienteaeropuerto.dto.Areas_trabajoDTO;
import org.una.clienteaeropuerto.dto.HorarioDTO;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.service.AreasTrabajoService;
import org.una.clienteaeropuerto.service.HorarioService;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

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
    @FXML
    private ComboBox<String> cbHoraEntrada;
    @FXML
    private ComboBox<String> cbMinutoEntrada;
    @FXML
    private ComboBox<String> cbHoraSalida;
    @FXML
    private ComboBox<String> cbMinutoSalida;

    DatePicker datepicker;

    HorarioService horarioService = new HorarioService();

    HorarioDTO horarioDTO = new HorarioDTO();

    java.util.Date date = new java.util.Date();

    java.util.Date date2 = new java.util.Date();

    Areas_trabajoDTO areas_trabajoDTO = new Areas_trabajoDTO();

    AreasTrabajoService areasTrabajoService = new AreasTrabajoService();

    List<Areas_trabajoDTO> areasTrabajoList = new ArrayList<>();

    CambiarVentana cambiarVentana = new CambiarVentana();

    TransaccionDTO transaccionDTO = new TransaccionDTO();
    TransaccionService transaccionService = new TransaccionService();
    java.util.Date date3 = new java.util.Date();

    VigenciaToken vigenciaToken = new VigenciaToken();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        llenarCbAreaTrabajo();
        FuncionAppContext();
    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            if (!AppContext.getInstance().get("ed").equals("edit")) {
                try {
                    crearHorario();
                    this.MensajeCreado();
                } catch (IOException | InterruptedException | ExecutionException e) {
                    this.MensajeCreadoFallido();
                }
            } else {
                try {
                    modificarUsuario();
                    this.MensajeEditado();
                } catch (IOException | InterruptedException | ExecutionException e) {
                    this.MensajeEditadoFallido();
                }
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    private void crearHorario() throws InterruptedException, ExecutionException, IOException {

        date.setHours(Integer.valueOf(cbHoraEntrada.getValue()));
        date.setMinutes(Integer.valueOf(cbMinutoEntrada.getValue()));
        date2.setHours(Integer.valueOf(cbHoraSalida.getValue()));
        date2.setMinutes(Integer.valueOf(cbMinutoSalida.getValue()));
        horarioDTO.setDiaEntrada(cbDiaEntrada.getValue());
        horarioDTO.setDiaSalida(cbDiaSalida.getValue());
        horarioDTO.setEstado(true);
        horarioDTO.setAreas_trabajo(areas_trabajoDTO);
        horarioDTO.setHora_entrada(date);
        horarioDTO.setHora_salida(date2);
        horarioService.add(horarioDTO);

        AgregarTransaccion("Se ha creado un horario");
    }

    private void modificarUsuario() throws InterruptedException, ExecutionException, IOException {

        horarioDTO = (HorarioDTO) AppContext.getInstance().get("horarioDTO");
        horarioDTO.setDiaEntrada(cbDiaEntrada.getValue());
        horarioDTO.setDiaSalida(cbDiaSalida.getValue());
        horarioDTO.setEstado(true);
        horarioService.modify(horarioDTO.getId(), horarioDTO);

        AgregarTransaccion("Se ha modificado un horario");
    }

    private void MensajeCreado() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El horario fue creado con éxito.");
        alert.show();

    }

    private void MensajeCreadoFallido() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("El horario no se ha podido crear.");
        alert.show();
    }

    private void MensajeEditado () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El horario fue modificado con éxito.");
        alert.show();

    }

    private void MensajeEditadoFallido () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El horario no se ha podido modificar.");
        alert.show();

    }

    @FXML
    private void OnActionBtnAtras(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("Horario", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
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
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(CrearHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbAreaTrabajo.setItems(FXCollections.observableArrayList(areasTrabajoList));
    }

    private void FuncionAppContext() {
        if (AppContext.getInstance().get("ed").equals("edit")) {
            HorarioDTO horarioDTO = new HorarioDTO();
            horarioDTO = (HorarioDTO) AppContext.getInstance().get("horarioDTO");
            RellenarCamposTable(horarioDTO);
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

    private void RellenarCamposTable(HorarioDTO horarioDTO1) {

        cbDiaEntrada.setValue(horarioDTO1.getDiaEntrada());
        cbDiaEntrada.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
        cbDiaSalida.setValue(horarioDTO1.getDiaSalida());
        cbDiaSalida.getItems().addAll("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado", "Domingo");
        cbHoraEntrada.setValue(String.valueOf(horarioDTO1.getHora_entrada().getHours()));
        cbHoraSalida.setValue(String.valueOf(horarioDTO1.getHora_salida().getHours()));
        cbMinutoEntrada.setValue(String.valueOf(horarioDTO1.getHora_entrada().getMinutes()));
        cbMinutoSalida.setValue(String.valueOf(horarioDTO1.getHora_salida().getMinutes()));
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

    private void AgregarTransaccion(String string) {

        try {
            transaccionDTO.setNombre(string);
            transaccionDTO.setUsuarios(AuthenticationSingleton.getInstance().getUsuario());
            transaccionDTO.setFecha_registro(date3);
            transaccionDTO.setEstado(true);

            transaccionService.add(transaccionDTO);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(CrearHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }
}
