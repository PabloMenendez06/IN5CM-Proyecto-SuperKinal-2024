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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.CategoriaProductosDTO;
import org.pablomenendez.model.CategoriaProductos;
import org.pablomenendez.system.Main;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class MenuCategoriaProductosController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    @FXML
    TableView tblCategoriaProductos;
    @FXML
    Button btnRegresar,btnAgregar,btnBorrar,btnActualizar,btnBuscar;
    @FXML
    TableColumn colCategoriaProductosId,colNombreCategoria,colDescripcionCategoria;
    @FXML
    TextField tfCategoriaProductoId;
    
    @FXML
     public void handleButtonAction(ActionEvent event){
          if(event.getSource() == btnRegresar){
              stage.menuPrincipalView();
          }else if(event.getSource() == btnAgregar){
              stage.formCategoriaProducto(1);
              
          }else if(event.getSource() == btnBorrar){
              borrarCategoriaProducto(((CategoriaProductos)tblCategoriaProductos.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
              cargarDatos();
          }else if(event.getSource() == btnActualizar){
              CategoriaProductosDTO.getCategoriaProductosDTO().setCategoriaProdcuto((CategoriaProductos)tblCategoriaProductos.getSelectionModel().getSelectedItem());
              stage.formCategoriaProducto(2);
          }else if(event.getSource() == btnBuscar){
              tblCategoriaProductos.getItems().clear();
              if(tfCategoriaProductoId.getText().equals("")){
                  cargarDatos();
              }else{
                  op =3;
                  cargarDatos();
              }
          }
     }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }    
    
    public void cargarDatos(){
        if(op == 3){
            tblCategoriaProductos.getItems().add(buscarCategoriaProductos());
            op = 0;
        }else{
            tblCategoriaProductos.setItems(listarCategoriaProductos());
        }
        
        colCategoriaProductosId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos,Integer>("categoriaProductosId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos,String >("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos,String >("descripcionCategoria"));
        
        
    }
    
    
    public ObservableList<CategoriaProductos>listarCategoriaProductos(){
        ArrayList<CategoriaProductos>categoriaProductos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProductos.add(new CategoriaProductos(categoriaProductosId,nombreCategoria,descripcionCategoria));
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
        return FXCollections.observableList(categoriaProductos);
    }
    
    
    public void borrarCategoriaProducto(int categProdId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_eliminarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, categProdId);
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
    
     public CategoriaProductos buscarCategoriaProductos(){
        CategoriaProductos categoriaProductos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaProductoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");

                
                categoriaProductos = new CategoriaProductos(categoriaProductosId,nombreCategoria,descripcionCategoria);
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
        return categoriaProductos;
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
