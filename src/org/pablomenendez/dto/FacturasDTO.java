/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.dto;

import org.pablomenendez.model.Facturas;

/**
 *
 * @author PEDRO
 */
public class FacturasDTO {
    private static FacturasDTO instance;
    private Facturas factura;
    
    private FacturasDTO(){
    
    }
    
    public static FacturasDTO getFacturaDTO(){
        if(instance == null){
            instance = new FacturasDTO();
        }
        
        return instance;
    }

    public Facturas getFactura() {
        return factura;
    }

    public void setFactura(Facturas factura) {
        this.factura = factura;
    }
    
}
