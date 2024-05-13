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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.model.Compras;
import org.pablomenendez.system.Main;

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
    Button btnRegresar,btnAgregar;
     @FXML
    TableColumn colCompraId,colFechaCompra,colTotalCompra;  
     @FXML
     TableView tblCompras;
             
    @FXML
    public void handleButtonAction(ActionEvent event){
          if(event.getSource() == btnRegresar){
              stage.menuPrincipalView();
          }else if(event.getSource() == btnAgregar){
              stage.formCompraView(1);
          }
     }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    
    public void cargarDatos(){
        tblCompras.setItems(listarCompras());
        
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compras,Integer>("compraId"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compras,Date>("fechaCompra"));
        colTotalCompra.setCellValueFactory(new PropertyValueFactory<Compras,Double>("totalCompra"));

    }
    
    public ObservableList<Compras>listarCompras(){
        ArrayList<Compras>compras = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
             while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                Date  fechaCompra = resultSet.getDate("fechaCompra");
                Double totalCompra = resultSet.getDouble("totalCompra");
                
                
                compras.add(new Compras(compraId,fechaCompra,totalCompra));
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
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    
    
     
    
}