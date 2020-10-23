/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class DashboardController implements Initializable {

    @FXML
    private Label txtUserName;
   LoginController user = new LoginController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.txtUserName.setText(user.CapturarUsuario());
        // TODO
    }

    @FXML
    private void OnActionAreasTrabajo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoAreas_Trabajo.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void OnActionControlUsuarios(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("ControlEmpleados.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void OnActionHorarios(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("ControlEmpleados.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void OnActionNotificaciones(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void OnActionTransacciones(ActionEvent event) {
//                  Parent root = FXMLLoader.load(App.class.getResource("ControlEmpleados.fxml"));
//        Scene creacionDocs = new Scene(root);
//
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(creacionDocs);
//        window.show();
    }

}
