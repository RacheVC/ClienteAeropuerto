/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private TextField txtAsunto;
    @FXML
    private TextArea txtMensaje;
    @FXML
    private RadioButton rbSi;
    @FXML
    private DatePicker dpCalendario;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnAgregarImagen;
    LoginController logincontroller = new LoginController();
    
    NotificacionDTO notificaciondto = new NotificacionDTO();
    NotificacionService notificacionservice = new NotificacionService();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
        int i=100;  
       
        notificaciondto.setEmisor("dwdwdw");
        notificaciondto.setEstado(true);
        notificaciondto.setMensaje(txtMensaje.getText());
        notificaciondto.setReceptor(txtReceptor.getText());
//        notificaciondto.setAsunto();
       
        
        notificacionservice.add(notificaciondto);
        
        
         Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void OnActionBtnAgregarImagen(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        int seleccion = fileChooser.showOpenDialog(areaTexto);
    }
    
}
