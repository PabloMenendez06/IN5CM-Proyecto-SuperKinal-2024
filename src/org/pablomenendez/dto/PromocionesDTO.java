/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.dto;

import org.pablomenendez.model.Promociones;

/**
 *
 * @author PEDRO
 */
public class PromocionesDTO {
    private static PromocionesDTO instance;
    private Promociones promocion;
    
    private PromocionesDTO(){
    
    }
    
    public static PromocionesDTO getPromocionDTO(){
        if(instance == null){
            instance = new PromocionesDTO();
        }
        
        return instance;
    }

    public Promociones getPromociones() {
        return promocion;
    }

    public void setPromociones(Promociones promocion) {
        this.promocion = promocion;
    }
}
