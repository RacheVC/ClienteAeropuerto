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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.UsuarioService;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class MantenimientoUsuariosController implements Initializable {

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
    private Button btnSalir;
    
    private List<UsuarioDTO> usuariosList = new ArrayList<UsuarioDTO>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            usuariosList = UsuarioService.getInstance().getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(MantenimientoUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MantenimientoUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        tcFechaRegistro.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFechaRegistro()));
        tcEmpleadoId.setCellValueFactory(new PropertyValueFactory<>("empleadoId"));
        tcRolId.setCellValueFactory(new PropertyValueFactory<>("rolesId"));
        
        tvUsuarios.getItems().clear();

        tvUsuarios.setItems(FXCollections.observableArrayList(usuariosList));
    }    

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionSalirrNotificacion(ActionEvent event) {
    }

    
}
