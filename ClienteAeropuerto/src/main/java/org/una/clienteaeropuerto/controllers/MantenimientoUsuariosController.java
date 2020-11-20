/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.service.UsuarioService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class MantenimientoUsuariosController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableView<UsuarioDTO> tvUsuarios;
    @FXML
    private TableColumn<UsuarioDTO, Object> clId;
    @FXML
    private TableColumn<UsuarioDTO, String> tcNombre;
    @FXML
    private TableColumn<UsuarioDTO, String> tcCedula;
    @FXML
    private TableColumn<UsuarioDTO, String> tcCorreo;
    @FXML
    private TableColumn<UsuarioDTO, String> tcEstado;
    @FXML
    private TableColumn<UsuarioDTO, String> tcFechaRegistro;
    @FXML
    private TableColumn<UsuarioDTO, String> tcEmpleadoId;
    @FXML
    private TableColumn<UsuarioDTO, String> tcRolId;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;
    @FXML
    private Button btnBuscar;

    private List<UsuarioDTO> usuariosList = new ArrayList<UsuarioDTO>();

    private List<UsuarioDTO> usuariosList2 = new ArrayList<UsuarioDTO>();

    UsuarioDTO usuarioDTO = new UsuarioDTO();

    UsuarioService usuarioService = new UsuarioService();

    CambiarVentana cambiarVentana = new CambiarVentana();

    TransaccionDTO transaccionDTO = new TransaccionDTO();

    TransaccionService transaccionService = new TransaccionService();

    java.util.Date date3 = new java.util.Date();

    VigenciaToken vigenciaToken = new VigenciaToken();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ValidacionPermisos();
        cargarInformacionUsuarios();
        txtBusqueda.setFocusTraversable(false);
    }

    @FXML
    private void actionBtnCrear(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            AppContext.getInstance().set("usuarioDTO", usuarioDTO);
            AppContext.getInstance().set("ed", "insertar");
            cambiarVentana.cambioVentana("CreacionUsuarios", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    @FXML
    private void actionBtnModificar(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            AppContext.getInstance().set("usuarioDTO", usuarioDTO);
            AppContext.getInstance().set("ed", "edit");
            cambiarVentana.cambioVentana("CreacionUsuarios", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    @FXML
    private void actionBtnInactivar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            if (usuarioDTO.isEstado() == true) {
                usuarioDTO.setEstado(false);
                encontrarFechaRegistro(usuarioDTO.getId());
                usuarioService.modify(usuarioDTO.getId(), usuarioDTO);
                AgregarTransaccion();
                cambiarVentana.cambioVentana("MantenimientoUsuarios", event);
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    private void encontrarFechaRegistro(Long id) {

        Date fechaEncontrada;
        for (int i = 0; i < usuariosList.size(); i++) {
            if (id == usuariosList.get(i).getId()) {
                fechaEncontrada = usuariosList.get(i).getFecha_registro();
                usuarioDTO.setFecha_registro(fechaEncontrada);
            }
        }
    }

    @FXML
    private void actionBtnSalir(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("Dashboard", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void MouseTvUsuarios(MouseEvent event) throws IOException {
        if (tvUsuarios.getSelectionModel().getSelectedItem() != null) {
            usuarioDTO = (UsuarioDTO) tvUsuarios.getSelectionModel().getSelectedItem();
        }
    }

    private void cargarInformacionUsuarios() {

        try {
            usuariosList = UsuarioService.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarTableView();
    }

    private void actualizarTableView() {

        for (int i = 0; i < usuariosList.size(); i++) {
            if (usuariosList.get(i).isEstado() == true) {
                usuariosList2.add(usuariosList.get(i));
                clId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tcEstado.setCellValueFactory(per -> {
                    String estadoString;
                    if (per.getValue().isEstado()) {
                        estadoString = "Activo";
                    } else {
                        estadoString = "Inactivo";
                    }
                    return new ReadOnlyStringWrapper(estadoString);
                });
                tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
                tcCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
                tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
                tcRolId.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getRoles()));
                tcFechaRegistro.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_registro()));
                tcEmpleadoId.setCellValueFactory(new PropertyValueFactory<>("empleado"));
                tvUsuarios.getItems().clear();
                tvUsuarios.setItems(FXCollections.observableArrayList(usuariosList2));
            }
        }
    }

    @FXML
    private void actionBtnBuscar(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            try {
                UsuarioService usuarioService = new UsuarioService();
                List<UsuarioDTO> UsuarioList = new ArrayList<>();
                UsuarioList = (List<UsuarioDTO>) usuarioService.finByCedula(txtBusqueda.getText());
                tvUsuarios.getItems().clear();
                tvUsuarios.setItems(FXCollections.observableArrayList(UsuarioList));
            } catch (Exception e) {
                System.out.println(e);
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Error");
                info.setContentText("Los datos no se han podido filtrar");
                info.showAndWait();
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
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
    private void KeyTypedtxtBusqueda(KeyEvent event) {

        if (txtBusqueda.getText().isEmpty()) {
            actualizarTableView();
        }

    }

    private void AgregarTransaccion() {
        try {
            transaccionDTO.setNombre("Se inactivó información de usuarios");
            transaccionDTO.setUsuarios(AuthenticationSingleton.getInstance().getUsuario());
            transaccionDTO.setFecha_registro(date3);
            transaccionDTO.setEstado(true);

            transaccionService.add(transaccionDTO);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(CreacionUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }
}
