/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author PEDRO
 */
public class Facturas {
    private int facturaId;
    private Date fecha;
    private Time hora;
    private int clienteId;
    private String cliente;
    private int empleadoId;
    private String empleado;
    private double total;

    public Facturas() {
    }

    public Facturas(int facturaId, Date fecha, Time hora, String cliente, String empleado, double total) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.empleado = empleado;
        this.total = total;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Facturas{" + "facturaId=" + facturaId + ", fecha=" + fecha + ", hora=" + hora + ", clienteId=" + clienteId + ", cliente=" + cliente + ", empleadoId=" + empleadoId + ", empleado=" + empleado + ", total=" + total + '}';
    }
    
    
    
}
