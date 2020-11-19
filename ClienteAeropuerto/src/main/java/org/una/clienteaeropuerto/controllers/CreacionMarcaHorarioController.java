/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import org.una.clienteaeropuerto.dto.Areas_trabajoDTO;
import org.una.clienteaeropuerto.dto.MarcaHorarioDTO;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.dto.Usuarios_AreasDTO;
import org.una.clienteaeropuerto.service.MarcasHorarioService;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.service.UsuariosAreasService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class CreacionMarcaHorarioController implements Initializable, Runnable {

    @FXML
    private CheckBox cbMarcaEntrada;
    @FXML
    private CheckBox cbMarcaSalida;
    @FXML
    private Label lbHora;

    String hora, minutos, segundos;
    Calendar calendario;
    Thread h1;

    MarcaHorarioDTO marcaHorarioDTO = new MarcaHorarioDTO();
    MarcasHorarioService marcasHorarioService = new MarcasHorarioService();

    Areas_trabajoDTO areas_trabajoDTO = new Areas_trabajoDTO();
    Usuarios_AreasDTO usuarios_AreasDTO = new Usuarios_AreasDTO();
    UsuariosAreasService usuariosAreasService = new UsuariosAreasService();
    private List<Usuarios_AreasDTO> usuariosAreasList = new ArrayList<Usuarios_AreasDTO>();

    java.util.Date date = new java.util.Date();
    java.util.Date date2 = new java.util.Date();

    CambiarVentana cambiarVentana = new CambiarVentana();

    TransaccionDTO transaccionDTO = new TransaccionDTO();
    TransaccionService transaccionService = new TransaccionService();
    java.util.Date date3 = new java.util.Date();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        h1 = new Thread(this);
        h1.start();

        funcionAppContext();
    }

    @FXML
    private void actionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (!AppContext.getInstance().get("ed").equals("edit")) {
            if (cbMarcaEntrada.isSelected()) {
                CrearMarcaEntrada();
                this.CrearMensaje();
            } else {
                this.FalloCrearMensaje();
            }
        } else {
            if (cbMarcaSalida.isSelected()) {
                CrearMarcaSalida();
                this.EditarMensaje();
            } else {
                this.FalloEditarMensaje();
            }
        }
    }

    private void CrearMarcaEntrada() throws InterruptedException, ExecutionException, IOException {

        CompararID();
        marcaHorarioDTO.setUsuariosAreas(usuarios_AreasDTO);
        marcaHorarioDTO.setEstado(true);
//        date2.setHours(00);
//        date2.setMinutes(Integer.valueOf(00));
//        marcaHorarioDTO.setMarca_salida(date2);
        marcasHorarioService.add(marcaHorarioDTO);
        
        AgregarTransaccion("Se creó una marca de entrada");
    }

    private void CrearMarcaSalida() throws InterruptedException, ExecutionException, IOException {

        CompararID();
        marcaHorarioDTO = (MarcaHorarioDTO) AppContext.getInstance().get("marcaHorarioDTO");
        marcaHorarioDTO.setUsuariosAreas(usuarios_AreasDTO);
        marcaHorarioDTO.setEstado(true);
        marcaHorarioDTO.setMarca_salida(date);
        marcasHorarioService.modify(marcaHorarioDTO.getId(), marcaHorarioDTO);
        
        AgregarTransaccion("Se creó una marca de salida");
    }

    private void CrearMensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("La marca de entrada se ha creado con éxito.");
        alert.show();

    }

    private void FalloCrearMensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("No ha realizado la marca de entrada.");
        alert.show();
    }

    private void EditarMensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("La marca de salida se ha creado con éxito.");
        alert.show();

    }

    private void FalloEditarMensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("No ha realizado la marca de salida.");
        alert.show();

    }

    private void calcularHora() {

        Calendar calendar = new GregorianCalendar();
        Date horaActual = new Date();
        calendar.setTime(horaActual);

        hora = calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendar.get(Calendar.HOUR_OF_DAY) : "0" + calendar.get(Calendar.HOUR_OF_DAY);
        minutos = calendar.get(Calendar.MINUTE) > 9 ? "" + calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE);
        segundos = calendar.get(Calendar.SECOND) > 9 ? "" + calendar.get(Calendar.SECOND) : "0" + calendar.get(Calendar.SECOND);
    }

    @Override
    public void run() {

        Thread hilo = Thread.currentThread();
        while (hilo == h1) {
            calcularHora();
            lbHora.setText(hora + ":" + minutos);
        }
    }

    private void funcionAppContext() {
        if (AppContext.getInstance().get("ed").equals("edit")) {
            MarcaHorarioDTO marcaHorarioDTO = new MarcaHorarioDTO();
            marcaHorarioDTO = (MarcaHorarioDTO) AppContext.getInstance().get("marcaHorarioDTO");
            marcaHorarioDTO.getMarca_entrada();
            cbMarcaEntrada.setDisable(true);
        } else {
            if (AppContext.getInstance().get("ed").equals("insertar")) {
                cbMarcaSalida.setDisable(true);
            }

        }
    }

    @FXML
    private void actionBtnAtras(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("ControlMarcasHorario", event);
    }

    private void CompararID() {

        try {
            usuariosAreasList = UsuariosAreasService.getInstance().getAll();
            System.out.println("aaaaaaa " + usuariosAreasList.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(CreacionMarcaHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(CreacionMarcaHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreacionMarcaHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < usuariosAreasList.size(); i++) {
            if (usuariosAreasList.get(i).getUsuarios().getId() == AuthenticationSingleton.getInstance().getUsuario().getId()) {
                usuarios_AreasDTO.setId(usuariosAreasList.get(i).getId());
                usuarios_AreasDTO.setAreas_trabajo(usuariosAreasList.get(i).getAreas_trabajo());
                usuarios_AreasDTO.setUsuarios(usuariosAreasList.get(i).getUsuarios());
            }
        }
    }

    private void AgregarTransaccion(String string) {
        
        try {
            transaccionDTO.setNombre(string);
            transaccionDTO.setUsuarios(AuthenticationSingleton.getInstance().getUsuario());
            transaccionDTO.setFecha_registro(date3);
            transaccionDTO.setEstado(true);

            transaccionService.add(transaccionDTO);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(CreacionMarcaHorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
