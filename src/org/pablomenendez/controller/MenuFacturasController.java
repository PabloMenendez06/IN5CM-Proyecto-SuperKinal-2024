/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.FacturasDTO;
import org.pablomenendez.model.Facturas;
import org.pablomenendez.report.GenerarReporte;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class MenuFacturasController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblFacturas;
    
    @FXML
    TableColumn colFacturaId,colFecha,colHora,colCliente,colEmpleado,colTotal;
    
    @FXML
    Button btnRegresar,btnAgregar,btnActualizar,btnBorrar,btnBuscar,btnAgregarDF,btnReporte;
    
    @FXML
    TextField tfFacturaId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formFacturasView(1);
        }else if(event.getSource() == btnActualizar){
            FacturasDTO.getFacturaDTO().setFactura((Facturas)tblFacturas.getSelectionModel().getSelectedItem());
            stage.formFacturasView(2);
        }else if(event.getSource() == btnBorrar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarFactura(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblFacturas.getItems().clear();
            if(tfFacturaId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }else if(event.getSource() == btnAgregarDF){
            stage.formDetalleFacturaView(1);
        }else if(event.getSource() == btnReporte){
            GenerarReporte.getInstance().generarFactura(((Facturas)tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId());
        }
    }
    
    public void cargarDatos(){
        if(op == 3){
            tblFacturas.getItems().add(buscarFactura());
            op = 0;
            
        }else{
            tblFacturas.setItems(listarFacturas()); 

            colFacturaId.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("facturaId"));
            colFecha.setCellValueFactory(new PropertyValueFactory<Facturas, Date>("fecha"));
            colHora.setCellValueFactory(new PropertyValueFactory<Facturas, Time>("hora"));
            colCliente.setCellValueFactory(new PropertyValueFactory<Facturas, String>("Cliente"));
            colEmpleado.setCellValueFactory(new PropertyValueFactory<Facturas, String>("Empleado"));
            colTotal.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("total"));
        }
        
        
    }
    
    public ObservableList<Facturas> listarFacturas(){
        ArrayList<Facturas> facturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                Double total = resultSet.getDouble("total");
            
                facturas.add(new Facturas(facturaId, fecha, hora, cliente, empleado, total));
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
        
        
        return FXCollections.observableList(facturas);
    }
    
    public void eliminarFactura(int factId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,factId);
            statement.execute();
            
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
    }
    
    public Facturas buscarFactura(){
        Facturas factura = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfFacturaId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                Double total = resultSet.getDouble("total");
            
                factura = new Facturas(facturaId, fecha, hora, cliente, empleado, total);

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
        return factura;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }  
    
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }    
    
}
