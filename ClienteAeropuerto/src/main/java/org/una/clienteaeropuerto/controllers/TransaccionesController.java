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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class TransaccionesController implements Initializable {

    @FXML
    private Button btnInactivar;
    @FXML
    private TableView<TransaccionDTO> tvTransacciones;
    @FXML
    private TableColumn<TransaccionDTO, String> tcId;
    @FXML
    private TableColumn<TransaccionDTO, String> tcUsuario;
    @FXML
    private TableColumn<TransaccionDTO, String> tcTransaaccion;
    @FXML
    private TableColumn<TransaccionDTO, String> tcFechaRegistro;
    @FXML
    private TableColumn<TransaccionDTO, String> tcEstado;

    private List<TransaccionDTO> transaccionList = new ArrayList<TransaccionDTO>();

    private List<TransaccionDTO> transaccionList2 = new ArrayList<TransaccionDTO>();

    TransaccionService transaccionService = new TransaccionService();

    CambiarVentana cambiarVentana = new CambiarVentana();

    TransaccionDTO transaccionDTO = new TransaccionDTO();

    VigenciaToken vigenciaToken = new VigenciaToken();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cargarInformacionUsuarios();
    }

    @FXML
    private void actionBtnInactivar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            if (transaccionDTO.isEstado() == true) {
                transaccionDTO.setEstado(false);
                transaccionService.modify(transaccionDTO.getId(), transaccionDTO);
                cambiarVentana.cambioVentana("Transacciones", event);
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    @FXML
    private void MouseTvUsuarios(MouseEvent event) {

        if (tvTransacciones.getSelectionModel().getSelectedItem() != null) {
            transaccionDTO = (TransaccionDTO) tvTransacciones.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void actionBtnSalir(ActionEvent event) throws IOException, IOException {
        
        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("Dashboard", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    private void cargarInformacionUsuarios() {

        try {
            transaccionList = transaccionService.getInstance().getAll();
            System.out.println(transaccionList);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(TransaccionesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarTableView();

    }

    private void actualizarTableView() {

        for (int i = 0; i < transaccionList.size(); i++) {
            if (transaccionList.get(i).isEstado() == true) {
                transaccionList2.add(transaccionList.get(i));
                tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tcEstado.setCellValueFactory(per -> {
                    String estadoString;
                    if (per.getValue().isEstado()) {
                        estadoString = "Activo";
                    } else {
                        estadoString = "Inactivo";
                    }
                    return new ReadOnlyStringWrapper(estadoString);
                });
                tcUsuario.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getUsuarios().getNombreCompleto()));
                tcFechaRegistro.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_registro()));
                tcTransaaccion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                tvTransacciones.getItems().clear();
                tvTransacciones.setItems(FXCollections.observableArrayList(transaccionList2));
            }
        }
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }

}
