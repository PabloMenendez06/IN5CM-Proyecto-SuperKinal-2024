/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.dto;

import org.pablomenendez.model.DetalleFacturas;

/**
 *
 * @author PEDRO
 */
public class DetalleFacturasDTO {
    private static DetalleFacturasDTO instance;
    private DetalleFacturas detallefactura;
    
    private DetalleFacturasDTO(){
    
    }
    
    public static DetalleFacturasDTO getDetalleFacturaDTO(){
        if(instance == null){
            instance = new DetalleFacturasDTO();
        }
        
        return instance;
    }

    public DetalleFacturas getDetalleFactura() {
        return detallefactura;
    }

    public void setDetalleFactura(DetalleFacturas detallefactura) {
        this.detallefactura = detallefactura;
    }
   
}
