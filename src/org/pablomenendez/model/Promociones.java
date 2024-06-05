/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.model;

import java.sql.Date;

/**
 *
 * @author PEDRO
 */
public class Promociones {
    private int promocionesId;
    private double precioPromocion;
    private String descripcionPromocion;
    private Date fechaInicio,fechaFinalizacion;
    private int productosId;
    private String producto;

    public Promociones() {
    }

    public Promociones(int promocionesId, double precioPromocion, String descripcionPromocion, Date fechaInicio, Date fechaFinalizacion, String producto) {
        this.promocionesId = promocionesId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.producto = producto;
    }

    public int getPromocionesId() {
        return promocionesId;
    }

    public void setPromocionesId(int promocionesId) {
        this.promocionesId = promocionesId;
    }

    public double getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(double precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public int getProductosId() {
        return productosId;
    }

    public void setProductosId(int productosId) {
        this.productosId = productosId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Promociones{" + "promocionesId=" + promocionesId + ", precioPromocion=" + precioPromocion + ", descripcionPromocion=" + descripcionPromocion + ", fechaInicio=" + fechaInicio + ", fechaFinalizacion=" + fechaFinalizacion + ", productosId=" + productosId + ", producto=" + producto + '}';
    }

    
}


