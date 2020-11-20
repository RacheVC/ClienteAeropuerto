/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.ConeccionReporteService;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class DashboardController implements Initializable {

    @FXML
    private Button btnControlEmpleados;
    @FXML
    private Button btnControlHorarios;
    @FXML
    private Button btnControlNotificaciones;
    @FXML
    private Button btnControlMarcas;
    @FXML
    private Button btnControlParametros;
    @FXML
    private Button btnReporteNotificaciones;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lbNombreUsuario;

    UsuarioDTO usuarioDTO = new UsuarioDTO();

    CambiarVentana cambiarVentana = new CambiarVentana();

    String reporte;
    ConeccionReporteService coneccionReporteService;

    VigenciaToken vigenciaToken = new VigenciaToken();
    @FXML
    private Button btnTransacciones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        capturarNombreUsuario();
        ValidacionPermisos();
    }

    private void Mensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El tiempo de su sesión ha finalizado.");
        alert.show();

    }

    @FXML
    private void actionControlEmpleados(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("MantenimientoUsuarios", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void onActionControlHorarios(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("Horario", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void actionControlNotificaciones(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("MantenimientoNotificaciones", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void actionControlMarcas(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("ControlMarcasHorario", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void actionControlParametros(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("ControlParametros", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void actionReporteNotificaciones(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            try {
                try {
                    reporte = ConeccionReporteService.getInstance().finByNombre("ReporteNotificaciones");
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }

                byte[] repor = Base64.getDecoder().decode(reporte);
                ByteArrayInputStream bta = null;
                ObjectInputStream ois = null;
                bta = new ByteArrayInputStream(repor);
                ois = new ObjectInputStream(bta);
                JasperPrint jp = (JasperPrint) ois.readObject();
                JasperViewer jv = new JasperViewer(jp, false);
                jv.setTitle("Reporte de notificaciones");
                jv.setVisible(true);
                jv.show();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void actionCerrarSesion(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Login", event);
    }

    private void ValidacionPermisos() {

        if ("Administrador".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnTransacciones.setDisable(true);
            btnControlNotificaciones.setDisable(true);
        } else if ("Gestor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnTransacciones.setDisable(true);
            btnControlParametros.setDisable(true);
            btnControlNotificaciones.setDisable(true);
        } else if ("Auditor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnControlParametros.setDisable(true);
            btnControlNotificaciones.setDisable(true);
        } else if ("Gerente".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnTransacciones.setDisable(true);
        }
    }

    private void capturarNombreUsuario() {

        lbNombreUsuario.setText(AuthenticationSingleton.getInstance().getUsuario().getNombreCompleto());
    }

    @FXML
    private void actionBtnHistorialTransacciones(ActionEvent event) throws IOException, IOException {
        cambiarVentana.cambioVentana("Transacciones", event);
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }
}
