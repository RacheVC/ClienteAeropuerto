/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.dto;

/**
 *
 * @author Andres
 */
public class Monedas {
    
    private Euros EURUSD;

    public Monedas() {
    }

    public Monedas(Euros EURUSD) {
        this.EURUSD = EURUSD;
    }

    public Euros getEURUSD() {
        return EURUSD;
    }

    public void setEURUSD(Euros EURUSD) {
        this.EURUSD = EURUSD;
    }
    
    
}
