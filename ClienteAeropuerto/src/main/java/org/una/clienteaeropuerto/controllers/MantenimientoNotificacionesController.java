/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import org.apache.poi.ss.usermodel.Color;
import org.una.clienteaeropuerto.App;
import org.una.clienteaeropuerto.dto.ImagenesDTO;
import org.una.clienteaeropuerto.dto.NotificacionDTO;
import org.una.clienteaeropuerto.service.ImagenService;
import org.una.clienteaeropuerto.service.NotificacionService;
import org.una.clienteaeropuerto.utils.AppContext;
import org.una.clienteaeropuerto.utils.AuthenticationSingleton;
import org.una.clienteaeropuerto.utils.Imagen;

/**
 * FXML Controller class
 *
 * @author rache
 */
public class MantenimientoNotificacionesController implements Initializable {

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
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnInactivar;

    private List<NotificacionDTO> notificacionlist = new ArrayList<NotificacionDTO>();

    private List<NotificacionDTO> notificacionlist2 = new ArrayList<NotificacionDTO>();

    private List<ImagenesDTO> imageneslist = new ArrayList<ImagenesDTO>();

    NotificacionDTO notificacionDTO = new NotificacionDTO();

    NotificacionService notificacionService = new NotificacionService();
    private Image image ;

    String str;
    @FXML
    private ImageView imagendeprueba;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        ValidacionPermisos();
        CargarInformacionNotificaciones();
        CargarListaImagenes();
        UnirPartesImagen(1);
        addButtonToTable();
        try {
            CargarTableViewImagenes();
        } catch (IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CargarInformacionNotificaciones() {

        try {
            notificacionlist = NotificacionService.getInstance().getAll();
        } catch (InterruptedException | ExecutionException | IOException ex) {
            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        actualizarTableView();
    }
    
    public void CargarTableViewImagenes() throws IOException{
//       ImageView imview = null;
////        Imagen imagenclase = null;
////        imview = new ImageView(encodeFileToBase64());
////       imagenclase.setImageView(imview);
////        
////       
////        TableColumn<Imagen, ImageView> firstColumn = new TableColumn<Imagen, ImageView>("Images");
////        firstColumn.setCellValueFactory(new PropertyValueFactory<Imagen, ImageView>("image"));
////        firstColumn.setPrefWidth(60);
////
////        /* add column to the tableview and set its items */
////        tvwImage.getColumns().add(firstColumn);
////        tvwImage.setItems(imgList);
////
////        /* add TableView to the layout */
////        layout.setCenter(tvwImage);
////        return layout;
////////////////////////////////////////////////////////////////////
//        Image img;
//        img = encodeFileToBase64();
//        imagendeprueba.setImage(img);
//        imagendeprueba.setFitHeight(50);
//        imagendeprueba.setFitWidth(50);
////        
////        BorderPane layout = new BorderPane();
//
//        /* layout -> center */
//        
//
//        
//            /* initialize two CustomImage objects and add them to the observable list */
//        ObservableList<Imagen> imgList = FXCollections.observableArrayList();
//        Imagen imagen = new Imagen(new ImageView(img));
//        imgList.addAll(imagen);
//       
//
//        /* initialize and specify table column */
//        
//        clEmisor.setCellValueFactory(new PropertyValueFactory<>("imageView"));
//        clImage.setPrefWidth(60);
//       
////      firstColumn.setPrefWidth(60);
//
//        /* add column to the tableview and set its items */
//        tvwImage.getColumns().add(clImage);
//        tvwImage.setItems(imgList);
////
////        /* add TableView to the layout */
//
//
////       
////        image = new Image(encodeFileToBase64());
       
    }

    private void actualizarTableView() {

        for (int i = 0; i < notificacionlist.size(); i++) {
            if (notificacionlist.get(i).isEstado() == true) {
                notificacionlist2.add(notificacionlist.get(i));
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
                clFechaEnvio.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_envio()));
                clFechaLectura.setCellValueFactory((param) -> new SimpleObjectProperty(param.getValue().getFecha_entrega()));
                clMensaje.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
                clReceptor.setCellValueFactory(new PropertyValueFactory<>("receptor"));
                tvewNotificacion.getItems().clear();
                tvewNotificacion.setItems(FXCollections.observableArrayList(notificacionlist2));
            }
        }
    }

    @FXML
    private void accionBuscarNotificacion(ActionEvent event) {
    }

    @FXML
    private void accionCrearNotificacion(ActionEvent event) throws IOException {

        AppContext.getInstance().set("notificacionDTO", notificacionDTO);
        AppContext.getInstance().set("ed", "insertar");

        Parent root = FXMLLoader.load(App.class.getResource("CreacionNotificacion.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionModificarNotificacion(ActionEvent event) throws IOException {

        AppContext.getInstance().set("notificacionDTO", notificacionDTO);
        AppContext.getInstance().set("ed", "edit");

        Parent root = FXMLLoader.load(App.class.getResource("CreacionNotificacion.fxml"));
        Scene creacionDocs = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(creacionDocs);
        window.show();
    }

    @FXML
    private void accionInactivarNotificacion(ActionEvent event) throws InterruptedException, ExecutionException, IOException {

        if (notificacionDTO.isEstado() == true) {
            notificacionDTO.setEstado(false);
            notificacionService.modify(notificacionDTO.getId(), notificacionDTO);

            Parent root = FXMLLoader.load(App.class.getResource("MantenimientoNotificaciones.fxml"));
            Scene creacionDocs = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(creacionDocs);
            window.show();
        }
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

    public Image encodeFileToBase64() throws IOException {
     
        String cadena = String.valueOf(UnirPartesImagen(1));

        byte[] bytes = Base64.getDecoder().decode(cadena);

        ByteArrayInputStream bos = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(bos);
        Image im = SwingFXUtils.toFXImage(bi, null);
        //       imagensirva.setImage(im);
        return im;
    }

    private void addButtonToTable() {
      
        TableColumn<NotificacionDTO, Void> colBtn = new TableColumn("Imagenes");

        Callback<TableColumn<NotificacionDTO, Void>, TableCell<NotificacionDTO, Void>> cellFactory = new Callback<TableColumn<NotificacionDTO, Void>, TableCell<NotificacionDTO, Void>>() {
            @Override
            public TableCell<NotificacionDTO, Void> call(final TableColumn<NotificacionDTO, Void> param) {
                final TableCell<NotificacionDTO, Void> cell = new TableCell<NotificacionDTO, Void>() {

                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            NotificacionDTO data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
//                        btn.backgroundProperty(Color.blue);
                        btn.setPrefWidth(30);
                        btn.setPrefHeight(30);
                        ImageView imv = null;
                       
                        try {
                            imv = new ImageView(encodeFileToBase64());
                        } catch (IOException ex) {
                            Logger.getLogger(MantenimientoNotificacionesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        imv.setFitHeight(30);
                        imv.setFitWidth(30);
                    
                            btn.setGraphic(imv);
                       
                          
                        
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        System.out.println(".updateItem()");
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                          
                        } else {
                            
                            setGraphic(btn);
                            
                           
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tvewNotificacion.getColumns().add(colBtn);

    }

    @FXML
    private void accionGenerarReporte(ActionEvent event) {

    }

    @FXML
    private void MouseClickedTvewNotificacion(MouseEvent event) {

        if (tvewNotificacion.getSelectionModel().getSelectedItem() != null) {
            notificacionDTO = (NotificacionDTO) tvewNotificacion.getSelectionModel().getSelectedItem();
        }
    }

    private void ValidacionPermisos() {

        if ("Administrador".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))
                || "Gerente".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {

            btnCrear.setDisable(true);
            btnModificar.setDisable(true);

        } else if ("Auditor".equals(String.valueOf(AuthenticationSingleton.getInstance().getUsuario().getRoles()))) {

            btnCrear.setDisable(true);
            btnModificar.setDisable(true);
            btnInactivar.setDisable(true);
        }
    }

}
