/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 *
 * @author Luis
 */
public class Cronometro extends Thread {

    Label tiempo;
    String min, seg = "";
    int segundos;
    int minutos;

    public Cronometro(Label lblTiempo) {
        tiempo = lblTiempo;
        segundos = 59;
        minutos = 2;
    }

    public void EjecutarCronometro() {
        segundos--;
        if (segundos == 0) {
            segundos = 59;
            minutos--;
        }

        seg = String.valueOf(segundos);
        min = String.valueOf(minutos);
        tiempo.setText(min + ":" + seg);

        if (minutos == 0) {
            segundos = 0;
            stop();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            EjecutarCronometro();
        } catch (InterruptedException ex) {
            Logger.getLogger(Cronometro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
