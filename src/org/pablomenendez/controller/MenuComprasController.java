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
import org.pablomenendez.dto.ComprasDTO;
import org.pablomenendez.model.Compras;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuComprasController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblCompras;
    
    @FXML
    TableColumn colCompraId,colFechaCompra,colTotalCompra;
    
    @FXML
    Button btnRegresar,btnAgregar,btnActualizar,btnBorrar,btnBuscar,btnAgregarDC;
    
    @FXML
    TextField tfCompraId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            agregarCompra();
            cargarDatos();
        }else if(event.getSource() == btnActualizar){
            ComprasDTO.getComprasDTO().setCompras((Compras)tblCompras.getSelectionModel().getSelectedItem());
            stage.formCompraView(2);
        }else if(event.getSource() == btnBorrar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarCompra(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getCompraId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblCompras.getItems().clear();
            if(tfCompraId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }else if(event.getSource() == btnAgregarDC){
            stage.formDetalleCompraView(1);
        }
    }
    
    
    
    public void agregarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_agregarCompra()";
            statement = conexion.prepareStatement(sql);
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
    public void cargarDatos(){
        if(op == 3){
            tblCompras.getItems().add(buscarCompra());
            op = 0;
            
        }else{
            tblCompras.setItems(listarCompras()); 

            colCompraId.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("compraId"));
            colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras, Date>("fechaCompra"));
            colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compras, Double>("total"));
        }
        
        
    }
    
    
    public ObservableList<Compras> listarCompras(){
        ArrayList<Compras> compras = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_ListarCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                Double total = resultSet.getDouble("totalCompra");
            
                compras.add(new Compras(compraId, fecha,total));
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
        
        
        return FXCollections.observableList(compras);
    }
    
    public void eliminarCompra(int compid){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_EliminarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,compid);
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            SuperKinalAlert.getInstance().mostrarAlertaInformacion(14);
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
    
    public Compras buscarCompra(){
        Compras compra = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_BuscarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfCompraId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                Date fecha = resultSet.getDate("fechaCompra");
                Double total = resultSet.getDouble("totalCompra");
            
                compra = new Compras(compraId, fecha,total);

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
        return compra;
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