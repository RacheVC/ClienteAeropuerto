/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.Areas_trabajoDTO;
import org.una.clienteaeropuerto.service.AreasTrabajoService;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class CreacionArea_TrabajoController implements Initializable {
    
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnGuardar;
    
    Areas_trabajoDTO areasTrabajoDTO = new Areas_trabajoDTO();
    AreasTrabajoService areasTrabajoService = new AreasTrabajoService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
        
        areasTrabajoDTO.setNombre(txtNombre.getText());
        areasTrabajoService.add(areasTrabajoDTO);
        
         Parent root = FXMLLoader.load(App.class.getResource("MantenimientoAreas_Trabajo.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }
    
}
