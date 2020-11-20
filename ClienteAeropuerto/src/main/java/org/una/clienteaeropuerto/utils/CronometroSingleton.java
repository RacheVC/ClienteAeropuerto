/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.utils;

import java.awt.event.ActionListener;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import org.una.clienteaeropuerto.controllers.DashboardController;

/**
 *
 * @author Luis
 */
public class CronometroSingleton extends Thread {

    Timeline cronoW;
    private int mili1;
    private int minuto1;
    private int segundo1;
    private int h, m, s, cs;

    public ActionListener acciones = new ActionListener() {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {

            ++cs;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
        }
    };

    public void cronoW() {
        cronoW = new Timeline(new KeyFrame(Duration.ZERO, (ActionEvent z) -> {
            lblTiempo.setText(String.valueOf(minuto1 + " : " + segundo1));
            mili1++;
            if (mili1 == 1000) {
                if (minuto1 == 5) {
                    cronoW.pause();
                    Mensaje();
                } else {
                    segundo1++;
                    mili1 = 0;
                    if (segundo1 == 60) {
                        segundo1 = 0;
                        minuto1++;
                    }
                }
            }
        }), new KeyFrame(Duration.millis(1)));
        cronoW.setCycleCount(Animation.INDEFINITE);
    }

     private void Mensaje() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
        alert.setTitle("Mensaje");
        alert.setHeaderText("El tiempo de su sesión ha finalizado.");
        alert.show();

    }
//    private static DashboardController SINGLETON = new DashboardController();
//    
//    public static DashboardController getInstance() {
//        return SINGLETON;
//    }
//
//    public static void setInstance(DashboardController singleton) {
//        SINGLETON = singleton;
//    }
}
