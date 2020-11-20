/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.utils;

import java.util.Date;

/**
 *
 * @author Luis
 */
public class VigenciaTokenSingleton {
    
    private static Date SINGLETON = new Date();

    public static Date getInstance() {
        return SINGLETON;
    }

    public static void setInstance(Date date) {
        SINGLETON = date;
    }
}
