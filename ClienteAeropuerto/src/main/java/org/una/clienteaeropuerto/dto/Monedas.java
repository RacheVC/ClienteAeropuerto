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
    
    private InfoRate USDEUR;
    private InfoRate USDGBP;
    private InfoRate USDAUD;
    private InfoRate USDNZD;
    private InfoRate USDCAD;
    private InfoRate USDCHF;
    private InfoRate USDJPY;
    private InfoRate USDCRC;

    public Monedas(InfoRate USDEUR, InfoRate USDGBP, InfoRate USDAUD, InfoRate USDNZD, InfoRate USDCAD, InfoRate USDCHF, InfoRate USDJPY, InfoRate USDCRC) {
        this.USDEUR = USDEUR;
        this.USDGBP = USDGBP;
        this.USDAUD = USDAUD;
        this.USDNZD = USDNZD;
        this.USDCAD = USDCAD;
        this.USDCHF = USDCHF;
        this.USDJPY = USDJPY;
        this.USDCRC = USDCRC;
    }

    public InfoRate getUSDEUR() {
        return USDEUR;
    }

    public void setUSDEUR(InfoRate USDEUR) {
        this.USDEUR = USDEUR;
    }

    public InfoRate getUSDGBP() {
        return USDGBP;
    }

    public void setUSDGBP(InfoRate USDGBP) {
        this.USDGBP = USDGBP;
    }

    public InfoRate getUSDAUD() {
        return USDAUD;
    }

    public void setUSDAUD(InfoRate USDAUD) {
        this.USDAUD = USDAUD;
    }

    public InfoRate getUSDNZD() {
        return USDNZD;
    }

    public void setUSDNZD(InfoRate USDNZD) {
        this.USDNZD = USDNZD;
    }

    public InfoRate getUSDCAD() {
        return USDCAD;
    }

    public void setUSDCAD(InfoRate USDCAD) {
        this.USDCAD = USDCAD;
    }

    public InfoRate getUSDCHF() {
        return USDCHF;
    }

    public void setUSDCHF(InfoRate USDCHF) {
        this.USDCHF = USDCHF;
    }

    public InfoRate getUSDJPY() {
        return USDJPY;
    }

    public void setUSDJPY(InfoRate USDJPY) {
        this.USDJPY = USDJPY;
    }

    public InfoRate getUSDCRC() {
        return USDCRC;
    }

    public void setUSDCRC(InfoRate USDCRC) {
        this.USDCRC = USDCRC;
    }

    
    
    
    
    
    
    
}
