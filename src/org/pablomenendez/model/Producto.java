/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.model;

import java.sql.Blob;

/**
 *
 * @author PEDRO
 */
public class Producto {
     private int productosId,cantidadStock;
    private String nombreProducto,descripcionProducto;
    private double precioVentaUnitario,precioCompra,precioVentaMayor;
    private Blob imagen;
    private int distribuidorId;
    private String distribuidor;
    private int categoriaProductosId;
    private String CategoriaProdu;

    public Producto() {
    }

    public Producto(int productosId, String nombreProducto, String descripcionProducto, int cantidadStock, double precioVentaUnitario, double precioVentaMayor, double precioCompra, Blob imagen, String distribuidor, String CategoriaProdu) {
        this.productosId = productosId;
        this.cantidadStock = cantidadStock;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioVentaUnitario = precioVentaUnitario;
        this.precioCompra = precioCompra;
        this.precioVentaMayor = precioVentaMayor;
        this.imagen = imagen;
        this.distribuidor = distribuidor;
        this.CategoriaProdu = CategoriaProdu;
        
        
    }

    public int getProductosId() {
        return productosId;
    }

    public void setProductosId(int productosId) {
        this.productosId = productosId;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecioVentaUnitario() {
        return precioVentaUnitario;
    }

    public void setPrecioVentaUnitario(double precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVentaMayor() {
        return precioVentaMayor;
    }

    public void setPrecioVentaMayor(double precioVentaMayor) {
        this.precioVentaMayor = precioVentaMayor;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public String getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(String distribuidor) {
        this.distribuidor = distribuidor;
    }

    public int getCategoriaProductosId() {
        return categoriaProductosId;
    }

    public void setCategoriaProductosId(int categoriaProductosId) {
        this.categoriaProductosId = categoriaProductosId;
    }

    public String getCategoriaProdu() {
        return CategoriaProdu;
    }

    public void setCategoriaProdu(String CategoriaProdu) {
        this.CategoriaProdu = CategoriaProdu;
    }
    
    @Override
    public String toString() {
        return "Id: " + productosId + " | " + nombreProducto;
    }

}


