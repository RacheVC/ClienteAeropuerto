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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.una.clienteaeropuerto.dto.AuthenticationRequest;
import org.una.clienteaeropuerto.dto.AuthenticationResponse;
import org.una.clienteaeropuerto.dto.ParametroDTO;
import org.una.clienteaeropuerto.service.ParametrosService;
import org.una.clienteaeropuerto.service.UsuarioService;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaTokenSingleton;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class LoginController implements Initializable {

    @FXML
    public TextField txtUserName;
    @FXML
    private PasswordField txtPassword;

    private boolean band = true;

    CambiarVentana cambiarVentana = new CambiarVentana();
    
    java.util.Date date = new java.util.Date();

    List<ParametroDTO> parametrosList = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    private void cargarHoraVigenciaToken(){
        
        try {
            parametrosList = ParametrosService.getInstance().getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void IniciarSesion(ActionEvent event) throws IOException {

        try {
            AuthenticationRequest aure = new AuthenticationRequest(txtUserName.getText(), txtPassword.getText());
            AuthenticationResponse autenticationresponse = UsuarioService.getInstance().Login(aure);
            AuthenticationSingleton.setInstance(autenticationresponse);
            cargarHoraVigenciaToken();
            date.setMinutes(date.getMinutes() + parametrosList.get(0).getVigenciaEnMinutos());
            VigenciaTokenSingleton.setInstance(date);
        } catch (IOException | InterruptedException | ExecutionException e) {
            this.MensajeNoAutorizado();
            band = false;
        }
        if (band == true) {
            this.MensajeAutorizado();
            cambiarVentana.cambioVentana("Dashboard", event);
        }
    }

    private void MensajeNoAutorizado() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("Usuario no autenticado.");
        alert.show();
    }

    private void MensajeAutorizado() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Usuario autenticado.");
        alert.show();
    }

    @FXML
    private void ActionBtnTipoCambio(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Divisa", event);
    }
}
