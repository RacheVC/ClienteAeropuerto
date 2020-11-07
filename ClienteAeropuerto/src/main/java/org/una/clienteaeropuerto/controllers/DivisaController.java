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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.una.clienteaeropuerto.App;
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
        
        txtMonto.setFocusTraversable(false);

    }

    @FXML
    private void CargarDivisas(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        CargarDivisasAutomaticamente();

    }

    public void CargarDivisasAutomaticamente() throws InterruptedException, ExecutionException, IOException {
        if ("Dólar Americano".equals(cmbTipoDivisa.getValue())) {
            DolarAmericano();
        }
        if ("Libra Esterlina".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDGBP().getRate()));
        }
        if ("Yen Japones".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDJPY().getRate()));
        }
        if ("Eurodolar".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDEUR().getRate()));
        }
        if ("Dólar Canadiense".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDCAD().getRate()));
        }
        if ("Franco Suizo".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDCHF().getRate()));
        }
        if ("Dólar neozelandes".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDNZD().getRate()));
        }
        if ("Dólar Australiano".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDAUD().getRate()));
        }
        if ("Colón Costarricense".equals(cmbTipoDivisa.getValue())) {
            CalcularTipoCambio(Float.valueOf(divisalist.getRates().getUSDCRC().getRate()));
        }
    }

    public void CargarDatos() {
        try {
            divisalist = (DivisaDTO) DivisaService.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(DivisaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DolarAmericano() throws InterruptedException, ExecutionException, IOException {
        float monto;
        monto = Float.valueOf(txtMonto.getText());
        LlenartxtField(monto);
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
    private void holdfdsf(InputMethodEvent event) {

    }

    @FXML
    private void BusquedaPorEscritura(KeyEvent event) throws InterruptedException, ExecutionException, IOException {

        if (!txtMonto.getText().isEmpty()) {
            CargarDivisasAutomaticamente();

        } else {
          this.Clear();
        }

    }

    private void Clear() {
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

    public void SoloNumerosEnteros(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);
            if (Character.isLetter(key)) {
                keyEvent.consume();
            }

        } catch (Exception ex) {
        }
    }

    @FXML
    private void ActionBtnLogin(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(App.class.getResource("Login.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

}
