/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.FacturasDTO;
import org.pablomenendez.model.Cliente;
import org.pablomenendez.model.Empleado;
import org.pablomenendez.model.Facturas;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class FormFacturasController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
   @FXML
    Button btnCancelar,btnGuardar;
   
   @FXML
   TextField tfFacturaId;
   
   @FXML
   ComboBox cmbClientes,cmbEmpleados;
   
   @FXML
private void handleButtonAction(ActionEvent event) {
    if (event.getSource() == btnCancelar) {
        FacturasDTO.getFacturaDTO().setFactura(null);
        stage.menuFacturasView();
    } else if (event.getSource() == btnGuardar) {
        if (op == 1) {
            agregarFactura();
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
            stage.menuFacturasView();
        } else if (op == 2) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarFactura();
                FacturasDTO.getFacturaDTO().setFactura(null);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.menuFacturasView();
            } else {
                stage.menuFacturasView();
            }
        }
    }
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(FacturasDTO.getFacturaDTO().getFactura() != null){
            cargarDatos(FacturasDTO.getFacturaDTO().getFactura());
        }
        cmbClientes.setItems(listarClientes());
        cmbEmpleados.setItems(listarEmpleados());
        
    }    
    
    public void cargarDatos(Facturas factura){
        tfFacturaId.setText(Integer.toString(factura.getFacturaId()));
    }
    
    public void agregarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_agregarFactura(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(2,((Empleado)cmbEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ActualizarFactura(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfFacturaId.getText()));
            statement.setInt(2,((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3,((Empleado)cmbEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
            
                clientes.add(new Cliente(clienteId, nombre, apellido, telefono, direccion, nit));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        
        return FXCollections.observableList(clientes);
    }
    
    public ObservableList<Empleado> listarEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaentrada = resultSet.getTime("horaDeEntrada");
                Time horaSalida = resultSet.getTime("horaDeSalida");
                String cargoId = resultSet.getString("cargo");
                String encargadoId = resultSet.getString("nombreEncargado");
            
                empleados.add(new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaentrada, horaSalida,cargoId,encargadoId));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                
                if(statement != null){
                    statement.close();
                }
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        
        return FXCollections.observableList(empleados);
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void setOp(int op) {
        this.op = op;
    }    
    
}
