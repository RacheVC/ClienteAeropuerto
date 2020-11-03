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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.una.clienteaeropuerto.dto.DivisaDTO;
import org.una.clienteaeropuerto.service.DivisaService;

/**
 * FXML Controller class
 *
 * @author Andres
 */

public class DivisaController implements Initializable {

    
 private List<DivisaDTO> divisalist = new ArrayList<DivisaDTO>();
    @FXML
    private ComboBox<String> cmbTipoDivisa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    cmbTipoDivisa.setValue("Eurodolar");
    cmbTipoDivisa.getItems().addAll("Eurodolar","Yen");
// TODO
    }    

    @FXML
    private void Atras(ActionEvent event) {
        
    }

    @FXML
    private void CargarDivisas(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
        if(cmbTipoDivisa.getValue() == "Eurodolar"){
            
            divisalist = DivisaService.getInstance().getAll();
            System.out.println(divisalist);
        }
    }
    
}
