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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.RolesDTO;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.RolesService;
import org.una.clienteaeropuerto.service.UsuarioService;
import org.una.clienteaeropuerto.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class CreacionUsuariosController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Button btnGuardar;

    RolesDTO rolesDTO = new RolesDTO();
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioService usuarioService = new UsuarioService();

    java.util.Date date2 = new java.util.Date();
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private ComboBox<RolesDTO> cbxRoles;
    @FXML
    private TextField txtJefe_id;

    RolesService rolesService = new RolesService();
    List<RolesDTO> rolesList = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        llenarCbRoles();
        funcionAppContext();
    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (!AppContext.getInstance().get("ed").equals("edit")) {
            try {
                usuarioDTO.setNombreCompleto(txtNombre.getText());
                usuarioDTO.setCedula(txtCedula.getText());
                usuarioDTO.setCorreo(txtCorreo.getText());
                usuarioDTO.setContrasenaEncriptada(txtContrasena.getText());
                usuarioDTO.setRoles(rolesDTO);
                usuarioService.add(usuarioDTO);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("El usuario fue creado con éxito.");
                alert.show();
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Error");
                alert.setHeaderText("El usuario no se ha podido crear.");
                alert.show();
            }
        } else {
            try {
                usuarioDTO = (UsuarioDTO) AppContext.getInstance().get("usuarioDTO");
                usuarioDTO.setNombreCompleto(txtNombre.getText());
                usuarioDTO.setCedula(txtCedula.getText());
                usuarioDTO.setCorreo(txtCorreo.getText());
                usuarioDTO.setContrasenaEncriptada(txtContrasena.getText());
                usuarioService.modify(usuarioDTO.getId(), usuarioDTO);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("El usuario fue modificado con éxito.");
                alert.show();
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
                alert.setTitle("Mensaje");
                alert.setHeaderText("El usuario no se ha podido modificar.");
                alert.show();
            }

        }

    }

    @FXML
    private void ActionCbxRoles(ActionEvent event) {
        if (cbxRoles.getSelectionModel().getSelectedItem() != null) {
            rolesDTO = (RolesDTO) cbxRoles.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void OnActionBtnAtras(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoUsuarios.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    private void llenarCbRoles() {
        try {
            rolesList = (List<RolesDTO>) rolesService.getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(CreacionUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(CreacionUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreacionUsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbxRoles.setItems(FXCollections.observableArrayList(rolesList));
    }

    private void funcionAppContext() {
        if (AppContext.getInstance().get("ed").equals("edit")) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO = (UsuarioDTO) AppContext.getInstance().get("usuarioDTO");
            txtCedula.setText(usuarioDTO.getCedula());
            txtContrasena.setText(usuarioDTO.getContrasenaEncriptada());
            txtCorreo.setText(usuarioDTO.getCorreo());
            txtNombre.setText(usuarioDTO.getNombreCompleto());
            cbxRoles.setValue(usuarioDTO.getRoles());
            usuarioDTO.getFecha_registro();
        }
    }

}
