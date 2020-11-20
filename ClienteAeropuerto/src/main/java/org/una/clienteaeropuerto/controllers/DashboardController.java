/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.awt.event.ActionListener;
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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.ConeccionReporteService;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.CronometroSingleton;

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
    private Button btnReporteHorasLaboradas;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Label lbNombreUsuario;

    UsuarioDTO usuarioDTO = new UsuarioDTO();

    CambiarVentana cambiarVentana = new CambiarVentana();

    private List<String> reporteList = new ArrayList<String>();
    String reporte;
    ConeccionReporteService coneccionReporteService;

    Timeline cronoW;
    private int mili1;
    private int minuto1;
    private int segundo1;
    private int h, m, s, cs;
    @FXML
    private Label lblTiempo;
    
    DashboardController dashboardController = new DashboardController();
     DashboardController dashboardController2 = new DashboardController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cronoW();
        cronoW.play();
        CronometroSingleton.setInstance(dashboardController);
        capturarNombreUsuario();
//        ValidacionPermisos();
    }

    public ActionListener acciones = new ActionListener() {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            ++cs;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
        }
    };

    public void cronoW() {
        cronoW = new Timeline(new KeyFrame(Duration.ZERO, (ActionEvent z) -> {
            lblTiempo.setText(String.valueOf(minuto1 + " : " + segundo1));
            mili1++;
            if (mili1 == 1000) {
                if (minuto1 == 5) {
                    cronoW.pause();
                    Mensaje();
                } else {
                    segundo1++;
                    mili1 = 0;
                    if (segundo1 == 60) {
                        segundo1 = 0;
                        minuto1++;
                    }
                }
            }
        }), new KeyFrame(Duration.millis(1)));
        cronoW.setCycleCount(Animation.INDEFINITE);
    }

    private void Mensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El tiempo de su sesión ha finalizado.");
        alert.show();

    }

    @FXML
    private void actionControlEmpleados(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("MantenimientoUsuarios", event);
    }

    @FXML
    private void onActionControlHorarios(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Horario", event);
    }

    @FXML
    private void actionControlNotificaciones(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("MantenimientoNotificaciones", event);
    }

    @FXML
    private void actionControlMarcas(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("ControlMarcasHorario", event);
    }

    @FXML
    private void actionControlParametros(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("ControlParametros", event);
    }

    @FXML
    private void actionReporteNotificaciones(ActionEvent event) {

        try {

            try {
                reporte = ConeccionReporteService.getInstance().finByNombre("ReporteNotificaciones");
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.err.println(reporte);

            byte[] repor = Base64.getDecoder().decode(reporte);
            ByteArrayInputStream bta = null;
            ObjectInputStream ois = null;
            bta = new ByteArrayInputStream(repor);
            ois = new ObjectInputStream(bta);
            JasperPrint jp = (JasperPrint) ois.readObject();
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setTitle("Reporte de gastos de mantenimiento");
            jv.setVisible(true);
            jv.show();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionReporteHorasLaboradas(ActionEvent event) {
    }

    @FXML
    private void actionCerrarSesion(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Login", event);
    }

    private void ValidacionPermisos() {

        if ("Administrador".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnControlNotificaciones.setDisable(true);
        } else if ("Gestor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnControlParametros.setDisable(true);
            btnControlNotificaciones.setDisable(true);
        } else if ("Auditor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnControlParametros.setDisable(true);
            btnControlNotificaciones.setDisable(true);
        }
    }

    private void capturarNombreUsuario() {

        lbNombreUsuario.setText(AuthenticationSingleton.getInstance().getUsuario().getNombreCompleto());
    }

    @FXML
    private void actionBtnHistorialTransacciones(ActionEvent event) throws IOException, IOException {
        cambiarVentana.cambioVentana("Transacciones", event);
    }
}
