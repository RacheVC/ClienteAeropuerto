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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.ConeccionReporteService;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        capturarNombreUsuario();
//        ValidacionPermisos();
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
    private void actionControlParametros(ActionEvent event) {
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
