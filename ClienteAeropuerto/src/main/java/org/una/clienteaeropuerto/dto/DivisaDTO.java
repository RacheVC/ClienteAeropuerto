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
public class DivisaDTO {
    
    private  Euros EURUSD;
    
    private int code;

    public DivisaDTO(Euros EURUSD, int code) {
        this.EURUSD = EURUSD;
        this.code = code;
    }

    public Euros getEURUSD() {
        return EURUSD;
    }

    public void setEURUSD(Euros EURUSD) {
        this.EURUSD = EURUSD;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
   
    
}
