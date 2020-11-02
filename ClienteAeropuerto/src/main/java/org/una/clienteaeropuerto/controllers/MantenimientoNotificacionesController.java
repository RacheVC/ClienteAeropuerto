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
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.NotificacionService;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class MantenimientoNotificacionesController implements Initializable {

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
    private Button btnSalir;

    private List<NotificacionDTO> notificacionlist = new ArrayList<NotificacionDTO>();
    @FXML
    private TableView<NotificacionDTO> tvewNotificacion;
    @FXML

    private TableColumn<NotificacionDTO, Object> clId;
    @FXML
    private TableColumn<NotificacionDTO, String> clFechaEnvio;
    @FXML
    private TableColumn<NotificacionDTO, String> clFechaLectura;
    @FXML
    private TableColumn<NotificacionDTO, String> clMensaje;
    @FXML
    private TableColumn<NotificacionDTO, String> clEmisor;
    @FXML
    private TableColumn<NotificacionDTO, String> clEstado;
    @FXML
    private TableColumn<NotificacionDTO, String> clReceptor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            notificacionlist = NotificacionService.getInstance().getAll();
            System.out.println(notificacionlist.get(0).getEmisor());
        } catch (InterruptedException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        clId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clEmisor.setCellValueFactory(new PropertyValueFactory<>("emisor"));
        clEstado.setCellValueFactory(per -> {
            String estadoString;
            if (per.getValue().isEstado()) {
                estadoString = "Activo";
            } else {
                estadoString = "Inactivo";
            }
            return new ReadOnlyStringWrapper(estadoString);
        });

        clFechaEnvio.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_envio()));
        clFechaLectura.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_lectura()));
        clMensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        clReceptor.setCellValueFactory(new PropertyValueFactory<>("receptor"));

        tvewNotificacion.getItems().clear();

        tvewNotificacion.setItems(FXCollections.observableArrayList(notificacionlist));

        System.out.println(notificacionlist);
        // TODO   

    }

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("CreacionNotificacion.fxml"));
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
    private void accionSalirrNotificacion(ActionEvent event) {
    }
}
