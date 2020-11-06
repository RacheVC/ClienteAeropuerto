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
import org.una.clienteaeropuerto.dto.MarcaHorarioDTO;
import org.una.clienteaeropuerto.service.MarcasHorarioService;
import org.una.clienteaeropuerto.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class ControlMarcasHorarioController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnInactivar;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<MarcaHorarioDTO> tvMarcasHorario;
    @FXML
    private TableColumn<MarcaHorarioDTO, String> tcId;
    @FXML
    private TableColumn<MarcaHorarioDTO, String> tcMarcaEntrada;
    @FXML
    private TableColumn<MarcaHorarioDTO, String> tcMarcaSalida;
    @FXML
    private TableColumn<MarcaHorarioDTO, String> tcAreaTrabajo;
    @FXML
    private TableColumn<MarcaHorarioDTO, String> tcEstado;

    private List<MarcaHorarioDTO> marcasList = new ArrayList<MarcaHorarioDTO>();
    private List<MarcaHorarioDTO> marcasList2 = new ArrayList<MarcaHorarioDTO>();

    MarcaHorarioDTO marcaHorarioDTO = new MarcaHorarioDTO();
    MarcasHorarioService marcasHorarioService = new MarcasHorarioService();
    @FXML
    private Button btnInsertarHoraEntrada;
    @FXML
    private Button btnInsertarHoraSalida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            marcasList = MarcasHorarioService.getInstance().getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlMarcasHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ControlMarcasHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlMarcasHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarTableView();

    }

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {

    }

    @FXML
    private void accionInactivar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (marcaHorarioDTO.isEstado() == true) {
            marcaHorarioDTO.setEstado(false);
            marcasHorarioService.modify(marcaHorarioDTO.getId(), marcaHorarioDTO);

            Parent root = FXMLLoader.load(App.class.getResource("ControlMarcasHorario.fxml"));
            Scene creacionDocs = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(creacionDocs);
            window.show();
        }
    }

    @FXML
    private void actionBtnInsertarHoraEntrada(ActionEvent event) throws IOException {

        AppContext.getInstance().set("marcaHorarioDTO", marcaHorarioDTO);
        AppContext.getInstance().set("ed", "insertar");

        Parent root = FXMLLoader.load(App.class.getResource("CreacionMarcaHorario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void actionBtnInsertarHoraSalida(ActionEvent event) throws IOException {

        AppContext.getInstance().set("marcaHorarioDTO", marcaHorarioDTO);
        AppContext.getInstance().set("ed", "edit");

        Parent root = FXMLLoader.load(App.class.getResource("CreacionMarcaHorario.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionSalir(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("Dashboard.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void MouseTvMarcas(MouseEvent event) {
        if (tvMarcasHorario.getSelectionModel().getSelectedItem() != null) {
            marcaHorarioDTO = (MarcaHorarioDTO) tvMarcasHorario.getSelectionModel().getSelectedItem();
        }
    }

    private void actualizarTableView() {
        for (int i = 0; i < marcasList.size(); i++) {
            if (marcasList.get(i).isEstado() == true) {
                marcasList2.add(marcasList.get(i));
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
                tcMarcaEntrada.setCellValueFactory((param) -> new SimpleObjectProperty<>(param.getValue().getMarca_entrada().getHours() + ":" + (param.getValue().getMarca_entrada().getMinutes())));
                tcMarcaSalida.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getMarca_salida().getHours() + ":" + (param.getValue().getMarca_salida().getMinutes())));
                tcAreaTrabajo.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getAreas_trabajo()));

                tvMarcasHorario.getItems().clear();
                tvMarcasHorario.setItems(FXCollections.observableArrayList(marcasList2));
            }
        }
    }
}
