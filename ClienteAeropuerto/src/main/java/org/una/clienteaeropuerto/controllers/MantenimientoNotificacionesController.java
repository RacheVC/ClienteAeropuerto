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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;

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

    private List<NotificacionDTO> notificacionlist = new ArrayList<NotificacionDTO>();

    private List<NotificacionDTO> notificacionlist2 = new ArrayList<NotificacionDTO>();

    private List<ImagenesDTO> imageneslist = new ArrayList<ImagenesDTO>();

    NotificacionDTO notificacionDTO = new NotificacionDTO();

    NotificacionService notificacionService = new NotificacionService();

    String str;
    @FXML
    private ImageView imagendeprueba;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        ValidacionPermisos();
        CargarInformacionNotificaciones();
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
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) throws IOException {

        AppContext.getInstance().set("notificacionDTO", notificacionDTO);
        AppContext.getInstance().set("ed", "insertar");

        Parent root = FXMLLoader.load(App.class.getResource("CreacionNotificacion.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) throws IOException {

        AppContext.getInstance().set("notificacionDTO", notificacionDTO);
        AppContext.getInstance().set("ed", "edit");

        Parent root = FXMLLoader.load(App.class.getResource("CreacionNotificacion.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (notificacionDTO.isEstado() == true) {
            notificacionDTO.setEstado(false);
            notificacionService.modify(notificacionDTO.getId(), notificacionDTO);

            Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
            Scene creacionDocs = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(creacionDocs);
            window.show();
        }
    }

    @FXML
    private void accionSalirrNotificacion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("Dashboard.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
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

}
