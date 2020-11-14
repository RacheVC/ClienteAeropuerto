/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;

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
    private ImageView imgNotificacion;
    @FXML
    private DatePicker dpFechaEntrega;
    @FXML
    private CheckBox cbProgramarFechaEntrega;

    LoginController user = new LoginController();

    private Window stage;

    NotificacionDTO notificacionDTO = new NotificacionDTO();

    NotificacionService notificacionservice = new NotificacionService();

    ImagenService imagenservice = new ImagenService();

    List<ImagenesDTO> listimagenes;

    static int residuo = 0;
    String str;

    private List<ImagenesDTO> imageneslist = new ArrayList<ImagenesDTO>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.listimagenes = new ArrayList<>();
        CargarListaImagenes();
        funcionAppContext();

    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException {
        UsuarioDTO usuariodto = new UsuarioDTO();

        notificacionDTO.setEstado(true);
        notificacionDTO.setMensaje(txtMensaje.getText());
        notificacionDTO.setReceptor(txtReceptor.getText());
        notificacionDTO.setEmisor(AuthenticationSingleton.getInstance().getUsuario().getNombreCompleto());
//        notificacionDTO.setUsuarios((AuthenticationSingleton.getInstance().getUsuario().getId()));
        notificacionservice.add(notificacionDTO);
        PostImage64();
        Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
        Scene creacionDocs = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();

    }

    @FXML
    private void OnActionBtnAgregarImagen(ActionEvent event) throws InterruptedException, ExecutionException, IOException, Exception {

        File file = this.GetFile();
        str = this.encodeFileToBase64(file);
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
            System.err.println("++++++++++++++++++++++" + enconde);
            return enconde;
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

    private void PostImage64() throws InterruptedException, ExecutionException, IOException {

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
                limite2 = limite;
                limite = limite - 10000;
            }
        }
    }

    private void funcionAppContext() {

        if (AppContext.getInstance().get("ed").equals("edit")) {
            NotificacionDTO notificacionDTO = new NotificacionDTO();
            notificacionDTO = (NotificacionDTO) AppContext.getInstance().get("notificacionDTO");
            txtMensaje.setText(notificacionDTO.getMensaje());
            txtReceptor.setText(notificacionDTO.getReceptor());
            notificacionDTO.setEstado(true);
            notificacionDTO.getId();

            try {
                imgNotificacion.setImage(encodeFileToBase64(Integer.valueOf(String.valueOf(notificacionDTO.getId()))));
            } catch (IOException ex) {
                Logger.getLogger(CreacionNotificacionController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
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

    public Image encodeFileToBase64(int id) throws IOException {

        String cadena = String.valueOf(UnirPartesImagen(id));

        byte[] bytes = Base64.getDecoder().decode(cadena);

        ByteArrayInputStream bos = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(bos);
        Image im = SwingFXUtils.toFXImage(bi, null);
        //       imagensirva.setImage(im);
        return im;
    }

    @FXML
    private void OnActionCbProgramarFechaEntrega(ActionEvent event) {

        if (cbProgramarFechaEntrega.isSelected()) {
            dpFechaEntrega.setDisable(false);
        } else {
            dpFechaEntrega.setDisable(true);
        }

    }
}
