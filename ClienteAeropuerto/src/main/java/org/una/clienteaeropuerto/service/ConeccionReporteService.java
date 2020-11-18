/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.service;

import java.io.IOException;
import java.util.List;
import org.una.clienteaeropuerto.utils.ConnectionUtils;

/**
 *
 * @author Andres
 */
public class ConeccionReporteService {
    
      private final String urlFindByReporte = "http://localhost:8098/reportes/";
      

       
         public String finByNombre(String nombre) throws IOException {

        return ConnectionUtils.ConnectionToObjectByReporte(urlFindByReporte, nombre);
    }
         
          public static ConeccionReporteService getInstance() {
        return ConeccionReporteHolder.INSTANCE;
    }
            private static class ConeccionReporteHolder {

        private static final ConeccionReporteService INSTANCE = new ConeccionReporteService();
    }
          
}
