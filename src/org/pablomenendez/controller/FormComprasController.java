/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.ComprasDTO;
import org.pablomenendez.model.Compras;
import org.pablomenendez.system.Main;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class FormComprasController implements Initializable {
    private Main stage;
    private int op;
    
   private static Connection conexion = null;
   private static PreparedStatement statement = null;
   
   @FXML
   Button btnGuardar,btnCancelar;
   @FXML
   TextField tfFechaCompra,tfTotalCompra,tfCompraId;
   
   @FXML
   public void handleButtonAction(ActionEvent event){
       if(event.getSource() == btnCancelar){
           stage.menuComprasView();
           ComprasDTO.getComprasDTO().setCompras(null);
       }else if(event.getSource() == btnGuardar){
           if(op == 1){
               agregarCompra();
               stage.menuComprasView();
           }else if(op == 2){
               actualizarCompra();
               ComprasDTO.getComprasDTO().setCompras(null);
               stage.menuComprasView();
           }
       }
   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ComprasDTO.getComprasDTO().getCompras() != null){
            
        }
    }    
    
    public void cargarDatos(Compras compras){
        tfCompraId.setText(Integer.toString(compras.getCompraId()));
    }
    
    public void agregarCompra(){
       
        try{
        
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_agregarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setDouble(1,Double.parseDouble(tfTotalCompra.getText())); 
             statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            
        }
    }
    
    public void actualizarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_actualizarCompra(?,?)";
            statement = conexion.prepareCall(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setDouble(2,Double.parseDouble (tfTotalCompra.getText()));
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            
        }finally{
            try{
                if(statement !=null){
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
