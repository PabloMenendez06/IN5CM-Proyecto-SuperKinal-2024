/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.model;

/**
 *
 * @author PEDRO
 */
public class DetalleFacturas {
    private int detalleFacturaId;
    private int facturaId;
    private String factura;
    private int productoId;
    private String producto;

    public DetalleFacturas() {
    }

    public DetalleFacturas(int detalleFacturaId, String factura, String producto) {
        this.detalleFacturaId = detalleFacturaId;
        this.factura = factura;
        this.producto = producto;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetalleFacturasDTO{" + "detalleFacturaId=" + detalleFacturaId + ", facturaId=" + facturaId + ", factura=" + factura + ", productoId=" + productoId + ", producto=" + producto + '}';
    }
    
}
