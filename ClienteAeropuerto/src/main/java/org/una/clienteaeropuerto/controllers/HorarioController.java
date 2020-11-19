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
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.una.clienteaeropuerto.dto.HorarioDTO;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.service.HorarioService;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;

/**
 * FXML Controller class
 *
 * @author Andres
 */
public class HorarioController implements Initializable {

    @FXML
    private TableView<HorarioDTO> tvewHorarios;
    @FXML
    private TableColumn<HorarioDTO, Object> clId;
    @FXML
    private TableColumn<HorarioDTO, String> clDiaEntrada;
    @FXML
    private TableColumn<HorarioDTO, String> clDiaSalida;
    @FXML
    private TableColumn<HorarioDTO, String> clAreaTrabajo;
    @FXML
    private TableColumn<HorarioDTO, String> clEstado;
    @FXML
    private TableColumn<HorarioDTO, String> clHoraEntrada;
    @FXML
    private TableColumn<HorarioDTO, String> clHoraSalida;
    @FXML
    private ComboBox<HorarioDTO> cbxFiltroDiaEntrada;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;

    private List<HorarioDTO> horariolist = new ArrayList<HorarioDTO>();

    private List<HorarioDTO> horariolist2 = new ArrayList<HorarioDTO>();

    HorarioDTO horarioDTO = new HorarioDTO();

    HorarioService horarioService = new HorarioService();

    CambiarVentana cambiarVentana = new CambiarVentana();

    TransaccionDTO transaccionDTO = new TransaccionDTO();
    TransaccionService transaccionService = new TransaccionService();
    java.util.Date date3 = new java.util.Date();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        ValidacionPermisos();
        CargarInformacionHorario();

    }

    @FXML
    private void accionCrearHorario(ActionEvent event) throws IOException {

        AppContext.getInstance().set("horarioDTO", horarioDTO);
        AppContext.getInstance().set("ed", "insertar");

        cambiarVentana.cambioVentana("CrearHorario", event);
    }

    @FXML
    private void accionModificarHorario(ActionEvent event) throws IOException {

        AppContext.getInstance().set("horarioDTO", horarioDTO);
        AppContext.getInstance().set("ed", "edit");

        cambiarVentana.cambioVentana("CrearHorario", event);

    }

    @FXML
    private void accionInactivarHorario(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (horarioDTO.isEstado() == true) {
            horarioDTO.setEstado(false);
            horarioService.modify(horarioDTO.getId(), horarioDTO);
            AgregarTransaccion();
            cambiarVentana.cambioVentana("Horario", event);
        }
    }

    @FXML
    private void accionSalirHorario(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Dashboard", event);
    }

    @FXML
    private void MouseTabla(MouseEvent event) {
        if (tvewHorarios.getSelectionModel().getSelectedItem() != null) {
            horarioDTO = (HorarioDTO) tvewHorarios.getSelectionModel().getSelectedItem();
        }
    }

    public void CargarInformacionHorario() {

        try {
            horariolist = HorarioService.getInstance().getAll();
            llenarCbxDiaEntrada();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(HorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarTableView();
    }

    private void actualizarTableView() {
        for (int i = 0; i < horariolist.size(); i++) {
            if (horariolist.get(i).isEstado() == true) {
                horariolist2.add(horariolist.get(i));
                clId.setCellValueFactory(new PropertyValueFactory<>("id"));
                clEstado.setCellValueFactory(per -> {
                    String estadoString;
                    if (per.getValue().isEstado()) {
                        estadoString = "Activo";
                    } else {
                        estadoString = "Inactivo";
                    }
                    return new ReadOnlyStringWrapper(estadoString);
                });
                clHoraEntrada.setCellValueFactory((param) -> new SimpleObjectProperty<>(param.getValue().getHora_entrada().getHours() + ":" + (param.getValue().getHora_entrada().getMinutes())));
                clHoraSalida.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getHora_salida().getHours() + ":" + (param.getValue().getHora_salida().getMinutes())));
                clDiaEntrada.setCellValueFactory(new PropertyValueFactory<>("diaEntrada"));
                clDiaSalida.setCellValueFactory(new PropertyValueFactory<>("diaSalida"));
                clAreaTrabajo.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getAreas_trabajo()));
                tvewHorarios.getItems().clear();
                tvewHorarios.setItems(FXCollections.observableArrayList(horariolist2));
            }
        }
    }

    @FXML
    private void actioncbxFiltroDiaEntrada(ActionEvent event) throws IOException {

        if (cbxFiltroDiaEntrada.getSelectionModel().getSelectedItem() != null) {
            HorarioDTO horarioDTO = new HorarioDTO();
            horarioDTO = (HorarioDTO) cbxFiltroDiaEntrada.getSelectionModel().getSelectedItem();
            HorarioService horarioService = new HorarioService();
            List<HorarioDTO> horarioList = new ArrayList<>();
            horarioList = (List<HorarioDTO>) horarioService.finByDiaEntrada(horarioDTO.getDiaEntrada());
            horarioList.get(0).getDiaEntrada();
            tvewHorarios.getItems().clear();
            tvewHorarios.setItems(FXCollections.observableArrayList(horarioList));
        }
    }

    private void llenarCbxDiaEntrada() throws InterruptedException, ExecutionException, IOException {

        HorarioService horarioService = new HorarioService();
        List<HorarioDTO> ListHorarioDTO = new ArrayList<>();
        ListHorarioDTO = (List<HorarioDTO>) horarioService.getAll();

        cbxFiltroDiaEntrada.setItems(FXCollections.observableArrayList(ListHorarioDTO));
    }

    private void ValidacionPermisos() {

        if ("Administrador".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))
                || "Gerente".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {

            btnCrear.setDisable(true);
            btnModificar.setDisable(true);

        } else if ("Auditor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {

            btnCrear.setDisable(true);
            btnModificar.setDisable(true);
            btnInactivar.setDisable(true);
        }
    }

    @FXML
    private void actionBtnMostrarTodos(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("Horario", event);
    }

    private void AgregarTransaccion() {

        try {
            transaccionDTO.setNombre("Se ha inactivado un horario");
            transaccionDTO.setFecha_registro(date3);
            transaccionDTO.setEstado(true);

            transaccionService.add(transaccionDTO);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(HorarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
