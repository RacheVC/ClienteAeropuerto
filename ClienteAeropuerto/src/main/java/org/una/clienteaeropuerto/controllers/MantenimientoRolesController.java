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
import org.una.clienteaeropuerto.dto.RolesDTO;
import org.una.clienteaeropuerto.service.RolesService;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class MantenimientoRolesController implements Initializable {

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
    private TableView<RolesDTO> tvRoles;
    @FXML
    private TableColumn<RolesDTO, Object> tcId;
    @FXML
    private TableColumn<RolesDTO, String> tcNombre;
    @FXML
    private Button btnSalir;

    private List<RolesDTO> rolesList = new ArrayList<RolesDTO>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            rolesList = RolesService.getInstance().getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(MantenimientoRolesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MantenimientoRolesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoRolesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tvRoles.getItems().clear();

        tvRoles.setItems(FXCollections.observableArrayList(rolesList));

    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnCrear(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(App.class.getResource("CreacionRoles.fxml"));
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
