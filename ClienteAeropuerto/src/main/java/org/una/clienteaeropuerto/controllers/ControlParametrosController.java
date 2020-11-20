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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.una.clienteaeropuerto.dto.ParametroDTO;
import org.una.clienteaeropuerto.service.ParametrosService;

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
    
     private List<ParametroDTO> parametroslist = new ArrayList<ParametroDTO>();
     
      private List<ParametroDTO> parametroslist2 = new ArrayList<ParametroDTO>();

    ParametroDTO parametrosDTO = new ParametroDTO();

    ParametrosService parametrosService = new ParametrosService();
    @FXML
    private TableColumn<ParametroDTO, String> tcEstado;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
            
            
           
   
        
        

    }

    private void actualizarTableView() {
        parametroslist2 = new ArrayList<>();
        for (int i = 0; i < parametroslist.size(); i++) {
            if (parametroslist.get(i).i == true) {
                parametroslist2.add(parametroslist.get(i));
                clId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                tcEstado.setCellValueFactory(per -> {
                    String estadoString;
                    if (per.getValue().isEstado) {
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
                tvewNotificacion.setItems(FXCollections.observableArrayList(parametroslist2));
            }
        }
    }


    @FXML
    private void actionBtnCrear(ActionEvent event) {
    }

    @FXML
    private void actionBtnModificar(ActionEvent event) {
    }

    @FXML
    private void actionBtnInactivar(ActionEvent event) {
    }

    @FXML
    private void MouseTvUsuarios(MouseEvent event) {
    }

    @FXML
    private void actionBtnSalir(ActionEvent event) {
    }
    
}
