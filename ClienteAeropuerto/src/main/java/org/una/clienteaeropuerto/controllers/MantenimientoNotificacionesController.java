/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.NotificacionService;

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
    NotificacionService notificacionservice;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

                        
            notificacionlist = NotificacionService.getInstance().getAll();
            System.out.println(notificacionlist.get(0).getEmisor());
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clId.setCellValueFactory(new PropertyValueFactory<>("id"));
       clEmisor.setCellValueFactory(new PropertyValueFactory<>("emisor"));
       clEstado.setCellValueFactory(per -> {
            String estadoString;
            if(per.getValue().isEstado())
                estadoString = "Activo";
            else
                estadoString = "Inactivo";
            return new ReadOnlyStringWrapper(estadoString);
        });
//        clFechaEnvio.setCellValueFactory(data -> {
//            SimpleStringProperty property = new SimpleStringProperty();
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            property.setValue(dateFormat.format(data.getValue().getFecha_envio()));
//            return property;
//        });
        clFechaLectura.setCellValueFactory(new PropertyValueFactory<>("fecha_lectura"));
        clMensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        clReceptor.setCellValueFactory(new PropertyValueFactory<>("receptor"));
        
        tvewNotificacion.getItems().clear();
        
//        ColeccionesJpaController coleccionesJpa = new ColeccionesJpaController();
//        List<Colecciones> colecciones = new ArrayList<>();
//        colecciones = (List<Colecciones>) coleccionesJpa.getColecciones();
       
        tvewNotificacion.setItems(FXCollections.observableArrayList(notificacionlist));
        
            System.out.println(notificacionlist);
            // TODO   
                
            }
    
    
//    private void initTabla(){
//        colId.setCellValueFactory(new PropertyValueFactory("id"));
//        colEmisor.setCellValueFactory(new PropertyValueFactory("cedula"));
//        colReceptor.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));
//        colEstado.setCellValueFactory(per -> {
//            String estadoString;
//            if(per.getValue().isEstado())
//                estadoString = "Activo";
//            else
//                estadoString = "Inactivo";
//            return new ReadOnlyStringWrapper(estadoString);
//        });
//        colesJefe.setCellValueFactory(per -> {
//            String estadoString;
//            if(per.getValue().isEsJefe())
//                estadoString = "Sí";
//            else
//                estadoString = "No";
//            return new ReadOnlyStringWrapper(estadoString);
//        });
//        colRegistrado.setCellValueFactory(data -> {
//            SimpleStringProperty property = new SimpleStringProperty();
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            property.setValue(dateFormat.format(data.getValue().getFechaRegistro()));
//            return property;
//        });
//        colModificacion.setCellValueFactory(data -> {
//            SimpleStringProperty property = new SimpleStringProperty();
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            property.setValue(dateFormat.format(data.getValue().getFechaModificacion()));
//            return property;
//        });
//        colIdA.setCellValueFactory(new PropertyValueFactory("id"));
//        colCod.setCellValueFactory(data -> {
//            return new ReadOnlyStringWrapper(data.getValue().getPermiso().getCodigo());
//        });
//        colDes.setCellValueFactory(data -> {
//            return new ReadOnlyStringWrapper(data.getValue().getPermiso().getDescripcion());
//        });
//        colEstadoA.setCellValueFactory(per -> {
//            String estadoString;
//            if(per.getValue().isEstado())
//                estadoString = "Activo";
//            else
//                estadoString = "Inactivo";
//            return new ReadOnlyStringWrapper(estadoString);
//        });
//    }
}
