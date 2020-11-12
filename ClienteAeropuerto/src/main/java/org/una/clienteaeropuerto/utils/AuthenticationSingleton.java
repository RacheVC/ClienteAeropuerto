/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.clienteaeropuerto.utils;

import org.una.clienteaeropuerto.dto.AuthenticationResponse;

/**
 *
 * @author Andres
 */
public class AuthenticationSingleton {
    
      private static  AuthenticationResponse SINGLETON = new AuthenticationResponse();
   
    public static AuthenticationResponse getInstance(){
        return SINGLETON;
    }
    
     public static void  setInstance(AuthenticationResponse singleton){
        SINGLETON = singleton;
    }
   
}
