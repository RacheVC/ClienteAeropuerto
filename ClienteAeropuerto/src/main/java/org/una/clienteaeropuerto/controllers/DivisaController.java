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
import javafx.scene.control.TextField;
import org.una.clienteaeropuerto.dto.DivisaDTO;
import org.una.clienteaeropuerto.service.DivisaService;

/**
 * FXML Controller class
 *
 * @author Andres
 */

public class DivisaController implements Initializable {

    
 private DivisaDTO divisalist ;
    @FXML
    private ComboBox<String> cmbTipoDivisa;
    @FXML
    private TextField txtMonto;
    @FXML
    private TextField txtDolarAmericano;
    @FXML
    private TextField txtLibraEsterlina;
    @FXML
    private TextField txtYenJapones;
    @FXML
    private TextField txtEurodolar;
    @FXML
    private TextField txtDolarCanadiense;
    @FXML
    private TextField txtDolarNeozelandes;
    @FXML
    private TextField txtFrancoSuizo;
    @FXML
    private TextField txtDolarAustraliano;
    @FXML
    private TextField txtColonCostarricense;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    cmbTipoDivisa.setValue("Eurodolar");
    cmbTipoDivisa.getItems().addAll("Eurodolar","Yen","Dolar Americano");
// TODO
    }    

    @FXML
    private void Atras(ActionEvent event) {
        
    }

    @FXML
    private void CargarDivisas(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
        
        if(cmbTipoDivisa.getValue() == "Dolar Americano"){
           
            Dolares();
        }
    }
    
    public void Dolares() throws InterruptedException, ExecutionException, IOException{
         double monto;
            monto = Double.valueOf(txtMonto.getText());
            divisalist = (DivisaDTO) DivisaService.getInstance().getAll();
            
            txtDolarAmericano.setText(txtMonto.getText()); 
            txtLibraEsterlina.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDGBP().getRate())*monto)));
            txtYenJapones.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDJPY().getRate())*monto)));
            
            
            txtEurodolar.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDEUR().getRate())*monto)));
            txtDolarCanadiense.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDCAD().getRate())*monto)));
            txtDolarNeozelandes.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDNZD().getRate())*monto)));
            
            txtFrancoSuizo.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDCHF().getRate())*monto)));
            txtDolarAustraliano.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDAUD().getRate())*monto)));
            txtColonCostarricense.setText(String.valueOf((Double.valueOf(divisalist.getRates().getUSDCRC().getRate())*monto)));
            
            System.out.println(divisalist.getRates().getUSDCAD().getRate());
            System.out.println(divisalist.getRates().getUSDCAD().getRate());
    }
    
}
