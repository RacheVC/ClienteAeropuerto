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
    
    private  Monedas rates;
    private String code;

    public DivisaDTO(Monedas rates, String code) {
        this.rates = rates;
        this.code = code;
    }

    public Monedas getRates() {
        return rates;
    }

    public void setRates(Monedas rates) {
        this.rates = rates;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DivisaDTO{" + "rates=" + rates + ", code=" + code + '}';
    }

    

    
    

    

   
    
    
}
