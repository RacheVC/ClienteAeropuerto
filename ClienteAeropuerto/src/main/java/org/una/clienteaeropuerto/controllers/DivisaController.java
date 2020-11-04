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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.una.clienteaeropuerto.dto.DivisaDTO;
import org.una.clienteaeropuerto.service.DivisaService;

/**
 * FXML Controller class
 *
 * @author Andres
 */
public class DivisaController implements Initializable {

    private DivisaDTO divisalist;
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
        cmbTipoDivisa.getItems().addAll("Dólar Americano", "Libra Esterlina", "Yen Japones", "Eurodolar", "Dólar Canadiense", "Franco Suizo", "Dólar neozelandes", "Dólar Australiano", "Colón Costarricense");

        txtMonto.addEventHandler(KeyEvent.KEY_TYPED, event -> SoloNumerosEnteros(event));
        CargarDatos();
// TODO

    }

    @FXML
    private void CargarDivisas(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        CargarDivisasAutomaticamente();

    }

    public void CargarDivisasAutomaticamente() throws InterruptedException, ExecutionException, IOException {

        if (cmbTipoDivisa.getValue() == "Dólar Americano") {

            DolarAmericano();
        }

        if (cmbTipoDivisa.getValue() == "Libra Esterlina") {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDGBP().getRate()));
        }

        if (cmbTipoDivisa.getValue() == "Yen Japones") {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDJPY().getRate()));
        }

        if (cmbTipoDivisa.getValue() == "Eurodolar") {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDEUR().getRate()));
        }

        if (cmbTipoDivisa.getValue() == "Dólar Canadiense") {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDCAD().getRate()));
        }

        if (cmbTipoDivisa.getValue() == "Franco Suizo") {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDCHF().getRate()));
        }

        if (cmbTipoDivisa.getValue() == "Dólar neozelandes") {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDNZD().getRate()));
        }

        if (cmbTipoDivisa.getValue() == "Dólar Australiano") {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDAUD().getRate()));
        }

        if (cmbTipoDivisa.getValue() == "Colón Costarricense") {

            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDCRC().getRate()));
        }
    }

    public void CargarDatos() {
        try {
            divisalist = (DivisaDTO) DivisaService.getInstance().getAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(DivisaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DivisaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DivisaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DolarAmericano() throws InterruptedException, ExecutionException, IOException {
        float monto;
        monto = Float.valueOf(txtMonto.getText());
        LlenartxtField(monto);
        System.out.println(divisalist.getRates().getUSDCAD().getRate());
        System.out.println(divisalist.getRates().getUSDCAD().getRate());
    }

    public void CalcularTipoCambio(float floatdivisa) {
        float monto;
        float montoEnDolar;

        monto = Float.valueOf(txtMonto.getText());
        System.err.println(monto);
        montoEnDolar = monto / floatdivisa;
        LlenartxtField(montoEnDolar);

    }

    public void LlenartxtField(float monto) {
        txtDolarAmericano.setText(String.valueOf(monto));
        txtLibraEsterlina.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDGBP().getRate()) * monto)));
        txtYenJapones.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDJPY().getRate()) * monto)));

        txtEurodolar.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDEUR().getRate()) * monto)));
        txtDolarCanadiense.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDCAD().getRate()) * monto)));
        txtDolarNeozelandes.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDNZD().getRate()) * monto)));

        txtFrancoSuizo.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDCHF().getRate()) * monto)));
        txtDolarAustraliano.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDAUD().getRate()) * monto)));
        txtColonCostarricense.setText(String.valueOf((Float.valueOf(divisalist.getRates().getUSDCRC().getRate()) * monto)));
    }

    @FXML
    private void Atras(ActionEvent event) {
    }

    @FXML
    private void holdfdsf(InputMethodEvent event) {

    }

    @FXML
    private void BusquedaPorEscritura(KeyEvent event) throws InterruptedException, ExecutionException, IOException {

        if (!txtMonto.getText().isEmpty()) {
            CargarDivisasAutomaticamente();

        } else {
            txtColonCostarricense.clear();
            txtDolarAmericano.clear();
            txtDolarAustraliano.clear();
            txtDolarCanadiense.clear();
            txtDolarNeozelandes.clear();
            txtEurodolar.clear();
            txtFrancoSuizo.clear();
            txtLibraEsterlina.clear();
            txtMonto.clear();
            txtYenJapones.clear();
        }

    }

    public void SoloNumerosEnteros(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);
            if (Character.isLetter(key)) {
                keyEvent.consume();
            }

        } catch (Exception ex) {
        }
    }

}
