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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.una.clienteaeropuerto.dto.ParametroDTO;
import org.una.clienteaeropuerto.service.ParametrosService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

/**
 * FXML Controller class
 *
 * @author Andres
 */
public class ControlParametrosController implements Initializable {

    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;
    @FXML
    private TableView<ParametroDTO> tvParametros;
    @FXML
    private TableColumn<ParametroDTO, String> tcId;
    @FXML
    private TableColumn<ParametroDTO, String> tcNombre;
    @FXML
    private TableColumn<ParametroDTO, String> tcTiempoVigencia;
    @FXML
    private TableColumn<ParametroDTO, String> tcEstado;

    private List<ParametroDTO> parametroslist = new ArrayList<ParametroDTO>();

    private List<ParametroDTO> parametroslist2 = new ArrayList<ParametroDTO>();

    ParametroDTO parametrosDTO = new ParametroDTO();

    ParametrosService parametrosService = new ParametrosService();

    CambiarVentana cambiarVentana = new CambiarVentana();

    VigenciaToken vigenciaToken = new VigenciaToken();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ValidacionPermisos();
        CargarInformacionParametros();
    }

    public void CargarInformacionParametros() {

        try {
            parametroslist = ParametrosService.getInstance().getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlParametrosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(ControlParametrosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlParametrosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarTableView();
    }

    private void actualizarTableView() {
        parametroslist2 = new ArrayList<>();
        for (int i = 0; i < parametroslist.size(); i++) {
            if (parametroslist.get(i).isEstado() == true) {
                parametroslist2.add(parametroslist.get(i));
                tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                tcEstado.setCellValueFactory(per -> {
                    String estadoString;
                    if (per.getValue().isEstado()) {
                        estadoString = "Activo";
                    } else {
                        estadoString = "Inactivo";
                    }
                    return new ReadOnlyStringWrapper(estadoString);
                });
                tcTiempoVigencia.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getVigenciaEnMinutos() + " minutos"));
                tvParametros.getItems().clear();
                tvParametros.setItems(FXCollections.observableArrayList(parametroslist2));
            }
        }
    }

    @FXML
    private void actionBtnCrear(ActionEvent event) {
    }

    @FXML
    private void actionBtnModificar(ActionEvent event) throws IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            AppContext.getInstance().set("parametrosDTO", parametrosDTO);
            AppContext.getInstance().set("ed", "edit");
            cambiarVentana.cambioVentana("CreacionParametro", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    @FXML
    private void actionBtnInactivar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            if (parametrosDTO.isEstado() == true) {
                parametrosDTO.setEstado(false);
                parametrosService.modify(parametrosDTO.getId(), parametrosDTO);
                cambiarVentana.cambioVentana("ControlParametros", event);
            }
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }

    }

    @FXML
    private void MouseTvUsuarios(MouseEvent event) {
        if (tvParametros.getSelectionModel().getSelectedItem() != null) {
            parametrosDTO = (ParametroDTO) tvParametros.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    private void actionBtnSalir(ActionEvent event) throws IOException, IOException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            cambiarVentana.cambioVentana("Dashboard", event);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }

    private void ValidacionPermisos() {

        if (!"Administrador".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {
            btnModificar.setDisable(true);
        }
    }

}
