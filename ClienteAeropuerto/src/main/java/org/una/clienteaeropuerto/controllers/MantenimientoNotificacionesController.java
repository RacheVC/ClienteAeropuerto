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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class MantenimientoNotificacionesController implements Initializable {

    @FXML
    private TableView<NotificacionDTO> tvewNotificacion;
    @FXML
    private TableColumn<NotificacionDTO, Object> clId;
    @FXML
    private TableColumn<NotificacionDTO, String> clFechaEnvio;
    @FXML
    private TableColumn<NotificacionDTO, String> clFechaLectura;
    @FXML
    private TableColumn<NotificacionDTO, String> clMensaje;
    @FXML
    private TableColumn<NotificacionDTO, String> clEmisor;
    @FXML
    private TableColumn<NotificacionDTO, String> clEstado;
    @FXML
    private TableColumn<NotificacionDTO, String> clReceptor;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;
    @FXML
    private ImageView imagendeprueba;

    private List<NotificacionDTO> notificacionlist = new ArrayList<NotificacionDTO>();

    private List<NotificacionDTO> notificacionlist2 = new ArrayList<NotificacionDTO>();

    private List<ImagenesDTO> imageneslist = new ArrayList<ImagenesDTO>();

    NotificacionDTO notificacionDTO = new NotificacionDTO();

    NotificacionService notificacionService = new NotificacionService();

    String str;

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

//        ValidacionPermisos();
        CargarInformacionNotificaciones();
        txtBusqueda.setFocusTraversable(false);
    }

    public void CargarInformacionNotificaciones() {

        try {
            notificacionlist = NotificacionService.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualizarTableView();

    }

    private void actualizarTableView() {
        notificacionlist2 = new ArrayList<>();
        for (int i = 0; i < notificacionlist.size(); i++) {
            if (notificacionlist.get(i).isEstado() == true) {
                notificacionlist2.add(notificacionlist.get(i));
                clId.setCellValueFactory(new PropertyValueFactory<>("id"));
                clEmisor.setCellValueFactory(new PropertyValueFactory<>("emisor"));
                clEstado.setCellValueFactory(per -> {
                    String estadoString;
                    if (per.getValue().isEstado()) {
                        estadoString = "Activo";
                    } else {
                        estadoString = "Inactivo";
                    }
                    return new ReadOnlyStringWrapper(estadoString);
                });
                clFechaEnvio.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_envio()));
                clFechaLectura.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_entrega()));
                clMensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
                clReceptor.setCellValueFactory(new PropertyValueFactory<>("receptor"));
                tvewNotificacion.getItems().clear();
                tvewNotificacion.setItems(FXCollections.observableArrayList(notificacionlist2));
            }
        }
    }

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            try {
                NotificacionService notificacionService = new NotificacionService();
                List<NotificacionDTO> notificacionList = new ArrayList<>();
                notificacionList = (List<NotificacionDTO>) notificacionService.finByEmisor(txtBusqueda.getText());
                tvewNotificacion.getItems().clear();
                tvewNotificacion.setItems(FXCollections.observableArrayList(notificacionList));
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

    @FXML
    private void accionCrearNotificacion(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            AppContext.getInstance().set("notificacionDTO", notificacionDTO);
            AppContext.getInstance().set("ed", "insertar");
            cambiarVentana.cambioVentana("CreacionNotificacion", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            AppContext.getInstance().set("notificacionDTO", notificacionDTO);
            AppContext.getInstance().set("ed", "edit");
            cambiarVentana.cambioVentana("CreacionNotificacion", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            if (notificacionDTO.isEstado() == true) {
                notificacionDTO.setEstado(false);
                notificacionService.modify(notificacionDTO.getId(), notificacionDTO);
                AgregarTransaccion();
                cambiarVentana.cambioVentana("MantenimientoNotificaciones", event);
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    @FXML
    private void accionSalirrNotificacion(ActionEvent event) throws IOException {
        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("Dashboard", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    public void CargarListaImagenes() {

        try {
            imageneslist = ImagenService.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String UnirPartesImagen(int id) {
        String partesUnidas = "";
        String parte;
        int cant = 0;
        for (int i = 0; i < imageneslist.size(); i++) {
            if (id == imageneslist.get(i).getNotificaciones().getId()) {
                parte = imageneslist.get(i).getImagen_Adjunta();
                partesUnidas = parte + partesUnidas;

            }
        }
        cant = partesUnidas.length();
        return partesUnidas;
    }

    @FXML
    private void accionGenerarReporte(ActionEvent event) {

    }

    @FXML
    private void MouseClickedTvewNotificacion(MouseEvent event) {

        if (tvewNotificacion.getSelectionModel().getSelectedItem() != null) {
            notificacionDTO = (NotificacionDTO) tvewNotificacion.getSelectionModel().getSelectedItem();
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
    private void KeyTypedTxtBuscar(KeyEvent event) {

        if (txtBusqueda.getText().isEmpty()) {

            actualizarTableView();

        }
    }

    private void AgregarTransaccion() {

        try {
            transaccionDTO.setNombre("Se ha inactivado inoformación de notificaciones");
            transaccionDTO.setFecha_registro(date3);
            transaccionDTO.setEstado(true);

            transaccionService.add(transaccionDTO);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }
}
