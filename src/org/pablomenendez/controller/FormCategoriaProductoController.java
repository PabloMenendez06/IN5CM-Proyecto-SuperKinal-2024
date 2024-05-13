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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.CategoriaProductosDTO;
import org.pablomenendez.model.CategoriaProductos;
import org.pablomenendez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormCategoriaProductoController implements Initializable {
     private Main stage;
   private int op;
   
   private static Connection conexion = null;
   private static PreparedStatement statement = null;
    
    @FXML
    Button btnCancelar,btnGuardar;
    @FXML
    TextField  tfCategoriaProductoId,tfNombreCategoriaId,tfDescripcionCategoria;
    
    @FXML
     public void handleButtonAction(ActionEvent event){
         if(event.getSource() == btnCancelar){
             stage.menuCategoriaProducto();
             CategoriaProductosDTO.getCategoriaProductosDTO().setCategoriaProdcuto(null);
         }else if(event.getSource() == btnGuardar){
             if(op == 1){
             agregarCategoriaProducto();
             stage.menuCategoriaProducto();
             }else if(op == 2){
                 actualizarCategoriaProductos();
                 CategoriaProductosDTO.getCategoriaProductosDTO().setCategoriaProdcuto(null);
                 stage.menuCategoriaProducto();
             }
             
         }
    }
     
     


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(CategoriaProductosDTO.getCategoriaProductosDTO().getCategoriaProdcuto() != null){
            cargarDatos(CategoriaProductosDTO.getCategoriaProductosDTO().getCategoriaProdcuto());
        }
    }  
    
    public void cargarDatos(CategoriaProductos categoriaProductos){
        tfCategoriaProductoId.setText(Integer.toString(categoriaProductos.getCategoriaProductosId()));
        tfNombreCategoriaId.setText(categoriaProductos.getNombreCategoria());
        tfDescripcionCategoria.setText(categoriaProductos.getDescripcionCategoria());
    }
    
    
    
     public void agregarCategoriaProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_agregarCategoriaProductos(?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoriaId.getText());
            statement.setString(2, tfDescripcionCategoria.getText());
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
     
     public void actualizarCategoriaProductos(){
         try{
             conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_actualizarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaProductoId.getText()));
            statement.setString(2, tfNombreCategoriaId.getText());
            statement.setString(3, tfDescripcionCategoria.getText());
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
