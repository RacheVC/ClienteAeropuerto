module org.una.clienteaeropuerto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    

    opens org.una.clienteaeropuerto to javafx.fxml;
    opens org.una.clienteaeropuerto.controllers to javafx.fxml;
    exports org.una.clienteaeropuerto;
    exports org.una.clienteaeropuerto.controllers;
  
}
