/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.NotificacionService;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class CreacionNotificacionController implements Initializable {

    @FXML
    private TextField txtReceptor;
    @FXML
    private TextArea txtMensaje;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnAgregarImagen;
    LoginController user = new LoginController();

    private Window stage;

    NotificacionDTO notificaciondto = new NotificacionDTO();
    NotificacionService notificacionservice = new NotificacionService();
    @FXML
    private ImageView imgNotificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
        int i = 100;

        notificaciondto.setEstado(true);
        notificaciondto.setMensaje(txtMensaje.getText());
        notificaciondto.setReceptor(txtReceptor.getText());

        notificacionservice.add(notificaciondto);

        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void OnActionBtnAgregarImagen(ActionEvent event) {

        int result;
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
        File imgFile = fc.showOpenDialog(stage);
        if (imgFile != null) {
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            imgNotificacion.setImage(image);
        }
    }
}
