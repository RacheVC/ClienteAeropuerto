/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

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
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.una.clienteaeropuerto.utils.CambiarVentana;

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

    CambiarVentana cambiarVentana = new CambiarVentana();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        llenarComboBoxTipoDivisa();

        txtMonto.addEventHandler(KeyEvent.KEY_TYPED, event -> SoloNumerosEnteros(event));

        CargarDatos();

        txtMonto.setFocusTraversable(false);

    }

    private void llenarComboBoxTipoDivisa() {
        cmbTipoDivisa.setValue("Eurodolar");
        cmbTipoDivisa.getItems().addAll("Dólar Americano", "Libra Esterlina", "Yen Japones", "Eurodolar", "Dólar Canadiense", "Franco Suizo", "Dólar neozelandes", "Dólar Australiano", "Colón Costarricense");
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

        cambiarVentana.cambioVentana("Login", event);
    }

    @FXML
    private void GenerarReporteDivisas(ActionEvent event) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reporte Divisas");

        Object[][] bookData = {
            {"Moneda", cmbTipoDivisa.getValue(), "Monto"},
            {"Dólar Americano", txtMonto.getText(), txtDolarAmericano.getText()},
            {"Yen Japones", txtMonto.getText(), txtYenJapones.getText()},
            {"Eurodolar", txtMonto.getText(), txtYenJapones.getText()},
            {"Dólar Canadiense", txtMonto.getText(), txtDolarCanadiense.getText()},
            {"Franco Suizo", txtMonto.getText(), txtFrancoSuizo.getText()},
            {"Dólar neozelandes", txtMonto.getText(), txtDolarNeozelandes.getText()},
            {"Dólar Australiano", txtMonto.getText(), txtDolarAustraliano.getText()},
            {"Colón Costarricense", txtMonto.getText(), txtColonCostarricense.getText()},};

        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }

        try ( FileOutputStream outputStream = new FileOutputStream("ReporteDivisas.xlsx")) {
            workbook.write(outputStream);
            MensajeConfirmacionReporte();
        }

    }

    public void MensajeConfirmacionReporte() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El reporte se ha realizado de manera correcta.");
        alert.show();
    }

}
