/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.dto;

import org.pablomenendez.model.Producto;

/**
 *
 * @author PEDRO
 */
public class ProductosDTO {
    private static ProductosDTO instance;
    private Producto producto;
    
    private ProductosDTO(){
    
    }
    
    public static ProductosDTO getProductoDTO(){
        if(instance == null){
            instance = new ProductosDTO();
        }
        
        return instance;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
}
