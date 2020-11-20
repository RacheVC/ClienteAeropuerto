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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.una.clienteaeropuerto.dto.ParametroDTO;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.service.ParametrosService;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class CreacionParametroController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtVigenciaMinutos;

    ParametroDTO parametroDTO = new ParametroDTO();

    ParametrosService parametrosService = new ParametrosService();

    TransaccionDTO transaccionDTO = new TransaccionDTO();

    TransaccionService transaccionService = new TransaccionService();

    java.util.Date date3 = new java.util.Date();

    VigenciaToken vigenciaToken = new VigenciaToken();

    CambiarVentana cambiarVentana = new CambiarVentana();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtVigenciaMinutos.addEventHandler(KeyEvent.KEY_TYPED, event -> SoloNumerosEnteros(event));
        funcionAppContext();
    }

    @FXML
    private void actionBtnGuardar(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            if (AppContext.getInstance().get("ed").equals("edit")) {
                try {
                    modificarParametro();
                    MensajeEditar();
                } catch (Exception e) {
                    EditarFalloMensaje();
                }
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    private void MensajeEditar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El parámetro fue modificado con éxito.");
        alert.show();

    }

    private void EditarFalloMensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El parámetro no se ha podido modificar.");
        alert.show();

    }

    @FXML
    private void actionBtnAtras(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("ControlParametros", event);
    }

    @FXML
    private void KeyTypedtxtVigenciaMinutos(KeyEvent event) {
    }

    private void modificarParametro() throws InterruptedException, ExecutionException, IOException {

        parametroDTO = (ParametroDTO) AppContext.getInstance().get("parametrosDTO");
        parametroDTO.setNombre(txtNombre.getText());
        parametroDTO.setVigenciaEnMinutos(Integer.valueOf(txtVigenciaMinutos.getText()));
        parametrosService.modify(parametroDTO.getId(), parametroDTO);
        AgregarTransaccion();
    }

    private void funcionAppContext() {

        if (AppContext.getInstance().get("ed").equals("edit")) {
            ParametroDTO parametroDTO = new ParametroDTO();
            parametroDTO = (ParametroDTO) AppContext.getInstance().get("parametrosDTO");
            txtVigenciaMinutos.setText(String.valueOf(parametroDTO.getVigenciaEnMinutos()));
            txtNombre.setText(parametroDTO.getNombre());;
        }
    }

    private void AgregarTransaccion() {

        try {
            transaccionDTO.setNombre("Se ha modificado un parámetro");
            transaccionDTO.setUsuarios(AuthenticationSingleton.getInstance().getUsuario());
            transaccionDTO.setFecha_registro(date3);
            transaccionDTO.setEstado(true);

            transaccionService.add(transaccionDTO);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(CreacionParametroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }

    public void SoloNumerosEnteros(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);
            if (Character.isLetter(key)) {
                keyEvent.consume();
            }

        } catch (Exception ex) {
        }
    }

}
