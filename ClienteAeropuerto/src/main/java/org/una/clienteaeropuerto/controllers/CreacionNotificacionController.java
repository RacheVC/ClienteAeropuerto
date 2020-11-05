/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
import javafx.scene.image.Image;
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
    List<ImagenesDTO> listimagenes;
    static int residuo = 0;
    @FXML
    private ImageView imgNotificacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.listimagenes = new ArrayList<>();

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
    private void OnActionBtnAgregarImagen(ActionEvent event) throws InterruptedException, ExecutionException, IOException, Exception {
        this.PostImage64();
    }

    public File GetFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            selectedFile.toPath();
            Image image = new Image("file:" + selectedFile.getAbsolutePath());
            imgNotificacion.setImage(image);
        }
        System.out.println(fileChooser);
        return selectedFile;
    }

    public String encodeFileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String enconde = new String(Base64.getEncoder().encodeToString(fileContent));
            return enconde;
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

    public void GetImage() throws InterruptedException, ExecutionException, IOException {
        this.listimagenes = this.imagenservice.getAll();
        for (int i = 0; i < this.listimagenes.size(); i++) {
            imagenservice.getAll().get(i).getNotificaciones().getId();

        }
    }

    public static void decoder(String base64Image, String pathFile) {
        try ( FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

    public void PostImage64() throws InterruptedException, ExecutionException, IOException {
        File file = this.GetFile();
        String str = this.encodeFileToBase64(file);
        int diferencia = 0;
        int totalcadena = str.length();
        int cantidadRecorrido = 0;
        int limite2 = 10000;
        int limite = 0;
        diferencia = totalcadena % 10000;

        if (diferencia == 0) {
            cantidadRecorrido = totalcadena / 100000;
            limite = (cantidadRecorrido * 10000) - 10000;
            limite2 = cantidadRecorrido * 10000;
        } else {
            cantidadRecorrido = (totalcadena / 10000) + 1;
            limite = (cantidadRecorrido * 10000) - 10000;
            limite2 = totalcadena;
        }

        for (int i = 0; i < cantidadRecorrido; i++) {
            if (totalcadena <= 10000) {
                ImagenesDTO imagen = new ImagenesDTO();
                imagen.setImagen_Adjunta(str.substring(0, totalcadena));
                imagen.setParte(cantidadRecorrido - i);
                imagen.setTotalPartes(cantidadRecorrido);
                this.imagenservice.add(imagen);
            }
            if (totalcadena > 10000) {

                ImagenesDTO imagen = new ImagenesDTO();
                imagen.setImagen_Adjunta(str.substring(limite, limite2));
                imagen.setTotalPartes(cantidadRecorrido);
                imagen.setParte(cantidadRecorrido - i);
                this.imagenservice.add(imagen);
                limite2 = limite - 1;
                limite = limite - 10000;
            }
        }
    }
}
