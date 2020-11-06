/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
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
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;
import org.una.clienteaeropuerto.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class MantenimientoNotificacionesController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;
    @FXML
    private Button btnSalir;

    private List<NotificacionDTO> notificacionlist = new ArrayList<NotificacionDTO>();

    private List<ImagenesDTO> imageneslist = new ArrayList<ImagenesDTO>();

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
    String str;
    @FXML
    private Button btnSalir1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        CargarInformacionNotificaciones();
        System.out.println(notificacionlist);

        CargarListaImagenes();
        UnirPartesImagen(1);
       this.encodeFileToBase64();
    }
    
    public void CargarInformacionNotificaciones(){
        try {
            notificacionlist = NotificacionService.getInstance().getAll();
            System.out.println(notificacionlist.get(0).getEmisor());
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        clFechaLectura.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_lectura()));
        clMensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        clReceptor.setCellValueFactory(new PropertyValueFactory<>("receptor"));

        tvewNotificacion.getItems().clear();

        tvewNotificacion.setItems(FXCollections.observableArrayList(notificacionlist));
        str = (String) AppContext.getInstance().get("str");

    }

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("CreacionNotificacion.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) {
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
//        System.out.println(partesUnidas.charAt(10053));
//       System.out.println(partesUnidas.charAt(10054));
     
        return partesUnidas;
    }

    public void encodeFileToBase64() {
        System.err.println(UnirPartesImagen(1));
        String cadenahp =String.valueOf(UnirPartesImagen(1));
//        String cadenaunida = this.UnirPartesImagen(1);
        
            byte[] bytes = Base64.getDecoder().decode("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIDEzIGxhenkgZG9ncy4=");
//        byte image[] = Base64.getDecoder().decode(cadenaunida);
//        String encode = new String (image);
        System.out.println(bytes.toString());
//        return encode;
    }
    
   

}
