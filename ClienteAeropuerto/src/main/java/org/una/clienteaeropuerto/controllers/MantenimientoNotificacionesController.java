/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;
import org.una.clienteaeropuerto.utils.AppContext;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class MantenimientoNotificacionesController implements Initializable {

    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;
    @FXML
    private Button btnSalir;

    private List<NotificacionDTO> notificacionlist = new ArrayList<NotificacionDTO>();

    private List<ImagenesDTO> imageneslist = new ArrayList<ImagenesDTO>();

    @FXML
    private TableView<NotificacionDTO> tvewNotificacion;
    @FXML

    private TableColumn<NotificacionDTO, Object> clId;
    @FXML
    private TableColumn<NotificacionDTO, String> clFechaEnvio;
    @FXML
    private TableColumn<NotificacionDTO, String> clFechaLectura;
    @FXML
    private TableColumn<NotificacionDTO, String> clMensaje;
    @FXML
    private TableColumn<NotificacionDTO, String> clEmisor;
    @FXML
    private TableColumn<NotificacionDTO, String> clEstado;
    @FXML
    private TableColumn<NotificacionDTO, String> clReceptor;
    String str;
    @FXML
    private Button btnGenerarReporte;
    @FXML
    private ImageView imagensirva;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CargarInformacionNotificaciones();
        System.out.println(notificacionlist);

        CargarListaImagenes();
        UnirPartesImagen(1);
        try {
            this.encodeFileToBase64();
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CargarInformacionNotificaciones() {
        try {
            notificacionlist = NotificacionService.getInstance().getAll();
            System.out.println(notificacionlist.get(0).getEmisor());
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        clId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clEmisor.setCellValueFactory(new PropertyValueFactory<>("emisor"));
        clEstado.setCellValueFactory(per -> {
            String estadoString;
            if (per.getValue().isEstado()) {
                estadoString = "Activo";
            } else {
                estadoString = "Inactivo";
            }
            return new ReadOnlyStringWrapper(estadoString);
        });
        RellenarTableView();
        str = (String) AppContext.getInstance().get("str");

    }

    private void RellenarTableView() {
        clFechaEnvio.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_envio()));
        clFechaLectura.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_lectura()));
        clMensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        clReceptor.setCellValueFactory(new PropertyValueFactory<>("receptor"));
        tvewNotificacion.getItems().clear();
        tvewNotificacion.setItems(FXCollections.observableArrayList(notificacionlist));
    }

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("CreacionNotificacion.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionSalirrNotificacion(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("Dashboard.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    public void CargarListaImagenes() {

        try {
            imageneslist = ImagenService.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String UnirPartesImagen(int id) {
        String partesUnidas = "";
        String parte;
        int cant = 0;
        for (int i = 0; i < imageneslist.size(); i++) {
            if (id == imageneslist.get(i).getNotificaciones().getId()) {
                parte = imageneslist.get(i).getImagen_Adjunta();
                partesUnidas = parte + partesUnidas;

            }

        }
        cant = partesUnidas.length();
        return partesUnidas;
    }
//
public void encodeFileToBase64() throws IOException {
        System.err.println(UnirPartesImagen(1));
        String cadena = String.valueOf(UnirPartesImagen(1));
//        String cadenaunida = this.UnirPartesImagen(1);

        byte[] bytes = Base64.getDecoder().decode(cadena);
////        byte image[] = Base64.getDecoder().decode(cadenaunida);
////        String encode = new String (image);
//          Image imagen;
//          
//        System.out.println(bytes);
//        return encode;

        ByteArrayInputStream bos = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(bos);
       Image im = SwingFXUtils.toFXImage(bi, null);
        imagensirva.setImage(im);

    }

//    private BufferedImage createImageFromBytes(byte[] imageData) {
//       ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
//       try {
//           return ImageIO.read();
//       } catch (IOException e) {
//           throw new RuntimeException(e);
//       }
//}
//    
//        public void decoder(String base64Image, String pathFile) {
//        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
//            // Converting a Base64 String into Image byte array
//            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
//            imageOutFile.write(imageByteArray);
//            BufferedImage image = null;
//            InputStream in = new ByteArrayInputStream(imageByteArray);
//            image = ImageIO.read(stream);
//            convertToFxImage(image);
//        } catch (FileNotFoundException e) {
//            System.out.println("Image not found" + e);
//        } catch (IOException ioe) {
//            System.out.println("Exception while reading the Image " + ioe);
//        }
//    }

    @FXML
    private void accionGenerarReporte(ActionEvent event) {
    }

}
