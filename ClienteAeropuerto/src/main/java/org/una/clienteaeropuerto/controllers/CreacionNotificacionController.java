/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class CreacionNotificacionController implements Initializable {

    @FXML
    private TextField txtReceptor;
    @FXML
    private TextArea txtMensaje;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnAgregarImagen;
    LoginController user = new LoginController();

    private Window stage;

    NotificacionDTO notificaciondto = new NotificacionDTO();
    NotificacionService notificacionservice = new NotificacionService();
    ImagenService imagenservice = new ImagenService();
    static int residuo = 0;
    @FXML
    private ImageView imgNotificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        notificaciondto.setEstado(true);
        notificaciondto.setMensaje(txtMensaje.getText());
        notificaciondto.setReceptor(txtReceptor.getText());

        notificacionservice.add(notificaciondto);

        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void OnActionBtnAgregarImagen(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

    }

    public File GetFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            selectedFile.toPath();
        }
        System.out.println(fileChooser + "aaaaaaaaaaaaaaaaaaaa");
        return selectedFile;
    }

    private static String encodeFileToBase64(File file) {
        try {

            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);

        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }

    }

    public void MetodoGuardarBase64() {
        String sobrante;

        File result = this.GetFile();
        String str = this.encodeFileToBase64(result);
        int totalcadena = str.length();
        System.out.println(totalcadena);
        if (residuo <= 1000) {
            if (totalcadena <= 10000) {
                ImagenesDTO imagen = new ImagenesDTO();
                imagen.setImagen_Adjunta(str.substring(0, totalcadena));
                System.out.println(imagen);
            }

        }
        if (residuo > 1000) {
            if (totalcadena > 10000) {
                for (int i = 0; i < totalcadena; i++) {
                    if (totalcadena > 10000) {
                        totalcadena = totalcadena - 10000;
                        ImagenesDTO imagen = new ImagenesDTO();
                        imagen.setImagen_Adjunta(str.substring(0, 10000));
                        System.out.println(imagen);
                        residuo = totalcadena % 10000;

                    }
                }

            }

        }
    }

}
