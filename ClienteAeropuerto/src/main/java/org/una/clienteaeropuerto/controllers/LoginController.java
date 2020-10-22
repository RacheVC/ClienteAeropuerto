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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.AuthenticationRequest;
import org.una.clienteaeropuerto.service.UsuarioService;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    
    private boolean band = true;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void IniciarSesion(ActionEvent event) throws IOException {
        
           try {
        
            AuthenticationRequest aure = new AuthenticationRequest(txtUserName.getText(),txtPassword.getText());
            
        UsuarioService.getInstance().Login(aure);
        
        
            
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("Usuario no autenticado.");
                alert.show();
                band = false;
                
        }
        
        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
                if(band == true){
                  Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("Usuario autenticado.");
                alert.show();  
                }
                
    
    }

    
}
