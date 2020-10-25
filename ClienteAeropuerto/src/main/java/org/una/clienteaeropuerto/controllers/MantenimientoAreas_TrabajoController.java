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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.Areas_trabajoDTO;
import org.una.clienteaeropuerto.service.AreasTrabajoService;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class MantenimientoAreas_TrabajoController implements Initializable {

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
    private TableView<Areas_trabajoDTO> tvAreasTrabajo;
    @FXML
    private TableColumn<Areas_trabajoDTO, String> tcNombre;
    @FXML
    private Button btnSalir;

    private List<Areas_trabajoDTO> areaTrabajoList = new ArrayList<Areas_trabajoDTO>();
    @FXML
    private TableColumn<Areas_trabajoDTO, Object> tcId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            areaTrabajoList = AreasTrabajoService.getInstance().getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(MantenimientoAreas_TrabajoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MantenimientoAreas_TrabajoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoAreas_TrabajoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tvAreasTrabajo.getItems().clear();

        tvAreasTrabajo.setItems(FXCollections.observableArrayList(areaTrabajoList));

    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {

    }

    @FXML
    private void onActionBtnCrear(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("CreacionArea_Trabajo.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void onActionBtnModificar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnInactivar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
    }

}
