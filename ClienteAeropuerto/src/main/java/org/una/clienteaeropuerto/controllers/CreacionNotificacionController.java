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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.dto.TransaccionDTO;
import org.una.clienteaeropuerto.dto.UsuarioDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;
import org.una.clienteaeropuerto.service.TransaccionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.CambiarVentana;
import org.una.clienteaeropuerto.utils.VigenciaToken;

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

    NotificacionDTO notificacionDTO2 = new NotificacionDTO();

    UsuarioDTO usuariosDTO = new UsuarioDTO();

    NotificacionService notificacionservice = new NotificacionService();

    ImagenService imagenservice = new ImagenService();

    List<ImagenesDTO> listimagenes;

    static int residuo = 0;

    String str;

    private List<ImagenesDTO> imageneslist = new ArrayList<ImagenesDTO>();

    List<NotificacionDTO> notificacionList = new ArrayList<>();

    private boolean bandera = false;
    private boolean bandera2 = false;

    java.util.Date date = new java.util.Date();

    CambiarVentana cambiarVentana = new CambiarVentana();

    TransaccionDTO transaccionDTO = new TransaccionDTO();
    TransaccionService transaccionService = new TransaccionService();
    java.util.Date date3 = new java.util.Date();

    VigenciaToken vigenciaToken = new VigenciaToken();
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnAgregarImagen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dpFechaEntrega.setDisable(true);
        this.listimagenes = new ArrayList<>();
        CargarListaImagenes();
        funcionAppContext();
    }

    @FXML
    private void OnActionBtnGuardar(ActionEvent event) throws InterruptedException, ExecutionException, IOException, InstantiationException, IllegalAccessException {

        if (vigenciaToken.validarVigenciaToken() == true) {
            GuardarNotificacion();
            if (bandera == true) {
                GuardarImagen64();
                bandera = false;
            }
            MensajeCrear();
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
    }

    private void GuardarNotificacion() throws InterruptedException, ExecutionException, IOException {

        if (bandera2 == true) {
            date = java.sql.Date.valueOf(dpFechaEntrega.getValue());
            notificacionDTO.setFecha_entrega(date);
            bandera2 = false;
        }
        usuariosDTO.setId(AuthenticationSingleton.getInstance().getUsuario().getId());
        notificacionDTO.setEstado(true);
        notificacionDTO.setMensaje(txtMensaje.getText());
        notificacionDTO.setReceptor(txtReceptor.getText());
        notificacionDTO.setEmisor(AuthenticationSingleton.getInstance().getUsuario().getNombreCompleto());
        notificacionDTO.setUsuarios(usuariosDTO);
        notificacionservice.add(notificacionDTO);

        AgregarTransaccion("Se ha creado una notificación");
    }

    private void MensajeCrear() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El usuario fue creado con éxito.");
        alert.show();
    }

    @FXML
    private void OnActionBtnAgregarImagen(ActionEvent event) throws InterruptedException, ExecutionException, IOException, Exception {

        if (vigenciaToken.validarVigenciaToken() == true) {
            bandera = true;
            File file = this.GetFile();
            str = this.codificarArchivoBase64(file);
        } else {
            MensajeTokenVencido();
            cambiarVentana.cambioVentana("Login", event);
        }
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
        return selectedFile;
    }

    public String codificarArchivoBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String enconde = new String(Base64.getEncoder().encodeToString(fileContent));
            return enconde;
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

    private void GuardarImagen64() throws InterruptedException, ExecutionException, IOException {

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
                asignarIdNotificacion();
                imagen.setNotificaciones(notificacionDTO2);
                this.imagenservice.add(imagen);
            }
            if (totalcadena > 10000) {

                ImagenesDTO imagen = new ImagenesDTO();
                imagen.setImagen_Adjunta(str.substring(limite, limite2));
                imagen.setTotalPartes(cantidadRecorrido);
                imagen.setParte(cantidadRecorrido - i);
                asignarIdNotificacion();
                imagen.setNotificaciones(notificacionDTO2);
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
                imgNotificacion.setImage(DecodificarStringImagen(Integer.valueOf(String.valueOf(notificacionDTO.getId()))));
            } catch (IOException ex) {
                Logger.getLogger(CreacionNotificacionController.class.getName()).log(Level.SEVERE, null, ex);
            }

            btnAgregarImagen.setDisable(true);
            btnGuardar.setDisable(true);
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

    public Image DecodificarStringImagen(int id) throws IOException {

        String cadena = String.valueOf(UnirPartesImagen(id));

        byte[] bytes = Base64.getDecoder().decode(cadena);

        ByteArrayInputStream bos = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(bos);
        Image im = SwingFXUtils.toFXImage(bi, null);
        return im;
    }

    @FXML
    private void OnActionCbProgramarFechaEntrega(ActionEvent event) {

        if (cbProgramarFechaEntrega.isSelected()) {
            dpFechaEntrega.setDisable(false);
            bandera2 = true;
        } else {
            dpFechaEntrega.setDisable(true);
            bandera2 = false;
        }
    }

    private void asignarIdNotificacion() {

        try {
            notificacionList = notificacionservice.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(CreacionNotificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < notificacionList.size(); i++) {
            if (i == notificacionList.size() - 1) {
                notificacionDTO2.setId(notificacionList.get(i).getId());
            }
        }
    }

    @FXML
    private void OnActionBtnAtras(ActionEvent event) throws IOException {

        cambiarVentana.cambioVentana("MantenimientoNotificaciones", event);
    }

    private void AgregarTransaccion(String string) {

        try {
            transaccionDTO.setNombre(string);
            transaccionDTO.setUsuarios(AuthenticationSingleton.getInstance().getUsuario());
            transaccionDTO.setFecha_registro(date3);
            transaccionDTO.setEstado(true);

            transaccionService.add(transaccionDTO);
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(CreacionNotificacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void MensajeTokenVencido() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Su sesión ha caducado.");
        alert.show();
    }
}
