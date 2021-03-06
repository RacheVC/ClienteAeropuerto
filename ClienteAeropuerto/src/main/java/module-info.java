module org.una.clienteaeropuerto {
   
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.google.gson;
    requires filechooser;
    requires poi.ooxml;
    requires poi;
    requires java.desktop;
    requires javafx.swing;
    requires jasperreports;

    opens org.una.clienteaeropuerto.dto;
    opens org.una.clienteaeropuerto to javafx.fxml;
    opens org.una.clienteaeropuerto.utils to javafx.fxml;
    opens org.una.clienteaeropuerto.controllers to javafx.fxml;
    exports org.una.clienteaeropuerto;
    exports org.una.clienteaeropuerto.controllers;
    exports org.una.clienteaeropuerto.dto;
    exports org.una.clienteaeropuerto.utils;

}
