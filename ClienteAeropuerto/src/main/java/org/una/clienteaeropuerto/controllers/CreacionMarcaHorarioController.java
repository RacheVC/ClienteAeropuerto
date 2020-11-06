/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.Areas_trabajoDTO;
import org.una.clienteaeropuerto.dto.MarcaHorarioDTO;
import org.una.clienteaeropuerto.service.AreasTrabajoService;
import org.una.clienteaeropuerto.service.MarcasHorarioService;
import org.una.clienteaeropuerto.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class CreacionMarcaHorarioController implements Initializable, Runnable {

    @FXML
    private CheckBox cbMarcaEntrada;
    @FXML
    private CheckBox cbMarcaSalida;
    @FXML
    private Label lbHora;

    String hora, minutos, segundos;
    Calendar calendario;
    Thread h1;

    MarcaHorarioDTO marcaHorarioDTO = new MarcaHorarioDTO();
    MarcasHorarioService marcasHorarioService = new MarcasHorarioService();
    @FXML
    private ComboBox<Areas_trabajoDTO> cbxAreaTrabajo;

    Areas_trabajoDTO areas_trabajoDTO = new Areas_trabajoDTO();
    AreasTrabajoService areasTrabajoService = new AreasTrabajoService();
    List<Areas_trabajoDTO> areasTrabajoList = new ArrayList<>();

    java.util.Date date = new java.util.Date();
    java.util.Date date2 = new java.util.Date();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        h1 = new Thread(this);
        h1.start();

        llenarCbAreaTrabajo();

        funcionAppContext();
    }

    @FXML
    private void actionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (!AppContext.getInstance().get("ed").equals("edit")) {
            try {
                marcaHorarioDTO.setAreas_trabajo(areas_trabajoDTO);
                marcaHorarioDTO.setEstado(true);
                date2.setHours(00);
                date2.setMinutes(Integer.valueOf(00));
                marcaHorarioDTO.setMarca_salida(date2);
                marcasHorarioService.add(marcaHorarioDTO);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("La marca se ha creado con éxito.");
                alert.show();
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Error");
                alert.setHeaderText("La marca no se ha podido crear.");
                alert.show();
            }
        } else {
            try {
                marcaHorarioDTO = (MarcaHorarioDTO) AppContext.getInstance().get("marcaHorarioDTO");
                marcaHorarioDTO.setAreas_trabajo(areas_trabajoDTO);
                marcaHorarioDTO.setEstado(true);
                marcaHorarioDTO.setMarca_salida(date);
                marcasHorarioService.modify(marcaHorarioDTO.getId(), marcaHorarioDTO);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("La marca fue modificado con éxito.");
                alert.show();
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("El marca no se ha podido modificar.");
                alert.show();
            }
        }
    }

    private void calcularHora() {

        Calendar calendar = new GregorianCalendar();
        Date horaActual = new Date();
        calendar.setTime(horaActual);

        hora = calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendar.get(Calendar.HOUR_OF_DAY) : "0" + calendar.get(Calendar.HOUR_OF_DAY);
        minutos = calendar.get(Calendar.MINUTE) > 9 ? "" + calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE);
        segundos = calendar.get(Calendar.SECOND) > 9 ? "" + calendar.get(Calendar.SECOND) : "0" + calendar.get(Calendar.SECOND);
    }

    @Override
    public void run() {

        Thread hilo = Thread.currentThread();
        while (hilo == h1) {
            calcularHora();
            lbHora.setText(hora + ":" + minutos);
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

        cbxAreaTrabajo.setItems(FXCollections.observableArrayList(areasTrabajoList));
    }

    private void funcionAppContext() {
        if (AppContext.getInstance().get("ed").equals("edit")) {
            MarcaHorarioDTO marcaHorarioDTO = new MarcaHorarioDTO();
            marcaHorarioDTO = (MarcaHorarioDTO) AppContext.getInstance().get("marcaHorarioDTO");
            marcaHorarioDTO.getMarca_entrada();
            cbMarcaEntrada.setDisable(true);
        } else {
            if (AppContext.getInstance().get("ed").equals("insertar")) {
                cbMarcaSalida.setDisable(true);
                cbxAreaTrabajo.setDisable(true);
            }

        }
    }

    @FXML
    private void actionCbxAreaTrabajo(ActionEvent event) {
        if (cbxAreaTrabajo.getSelectionModel().getSelectedItem() != null) {
            areas_trabajoDTO = (Areas_trabajoDTO) cbxAreaTrabajo.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void actionBtnAtras(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("ControlMarcasHorario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }
}
