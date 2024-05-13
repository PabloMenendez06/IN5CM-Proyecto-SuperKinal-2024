/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.dto;

import org.pablomenendez.model.CategoriaProductos;

/**
 *
 * @author PEDRO
 */
public class CategoriaProductosDTO {
    private static CategoriaProductosDTO instance;
    private CategoriaProductos categoriaProdcuto;
    
    private CategoriaProductosDTO(){
        
    }
    
    public static CategoriaProductosDTO getCategoriaProductosDTO(){
        if(instance == null){
            instance = new CategoriaProductosDTO();
        }
        return instance;
    }

    public CategoriaProductos getCategoriaProdcuto() {
        return categoriaProdcuto;
    }

    public void setCategoriaProdcuto(CategoriaProductos categoriaProdcuto) {
        this.categoriaProdcuto = categoriaProdcuto;
    }
    
}
