/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.utils;

/**
 *
 * @author Luis
 */
public class VigenciaToken {

    java.util.Date date2 = new java.util.Date();

    public boolean validarVigenciaToken() {

        if (date2.getTime() > VigenciaTokenSingleton.getInstance().getTime()) {
            return false;
        } else {
            return true;
        }
    }
}
