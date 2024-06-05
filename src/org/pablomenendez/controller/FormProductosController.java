/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.ProductosDTO;
import org.pablomenendez.model.CategoriaProductos;
import org.pablomenendez.model.Distribuidor;
import org.pablomenendez.model.Producto;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class FormProductosController implements Initializable {
    private Main stage;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private List<File> files = null;
    
    @FXML
    Button btnCancelar,btnGuardar;
   
    @FXML
    TextField tfProductoId,tfNombreP,tfDescripcionP,tfCantidadStock,tfPrecioVU,tfPrecioVM,tfPrecioC;

    @FXML
    ComboBox cmbDistribuidores,cmbCategoriasP;
    
    @FXML
    ImageView imgCargar, imgMostrar;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnCancelar){
            ProductosDTO.getProductoDTO().setProducto(null);
            stage.menuProductosView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombreP.getText().equals("") && !tfCantidadStock.getText().equals("") && !tfPrecioVU.getText().equals("") && !tfPrecioVM.getText().equals("") && !tfPrecioC.getText().equals("")){
                    agregarProducto();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuProductosView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfNombreP.getText().equals("")){
                        tfNombreP.requestFocus();
                    }else if(tfCantidadStock.getText().equals("")){
                        tfCantidadStock.requestFocus();
                    }else if(tfPrecioVU.getText().equals("")){
                        tfPrecioVU.requestFocus();
                    }else if(tfPrecioVM.getText().equals("")){
                        tfPrecioVM.requestFocus();
                    }else if(tfPrecioC.getText().equals("")){
                        tfPrecioC.requestFocus();
                    }
                }
                
               
            }else if(op == 2){
                if(!tfNombreP.getText().equals("") && !tfCantidadStock.getText().equals("") && !tfPrecioVU.getText().equals("") && !tfPrecioVM.getText().equals("") && !tfPrecioC.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                        editarProducto();
                        ProductosDTO.getProductoDTO().setProducto(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        stage.menuProductosView();
                    }else{
                        stage.menuProductosView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfNombreP.getText().equals("")){
                        tfNombreP.requestFocus();
                    }else if(tfCantidadStock.getText().equals("")){
                        tfCantidadStock.requestFocus();
                    }else if(tfPrecioVU.getText().equals("")){
                        tfPrecioVU.requestFocus();
                    }else if(tfPrecioVM.getText().equals("")){
                        tfPrecioVM.requestFocus();
                    }else if(tfPrecioC.getText().equals("")){
                        tfPrecioC.requestFocus();
                    }
                }
                
            }
        }
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();
            FileInputStream file = new FileInputStream(files.get(0));
            Image image = new Image(file);
            imgCargar.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleOnDrag(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_agregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreP.getText());
            statement.setString(2, tfDescripcionP.getText());
            statement.setString(3, tfCantidadStock.getText());
            statement.setString(4, tfPrecioVU.getText());
            statement.setString(5, tfPrecioVM.getText());
            statement.setString(6, tfPrecioC.getText());

            if (files != null && !files.isEmpty() && files.get(0) != null) {
                InputStream img = new FileInputStream(files.get(0));
                statement.setBinaryStream(7, img);
            } else {
                statement.setBinaryStream(7, null); 
            }
            statement.setInt(8,((Distribuidor)cmbDistribuidores.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9,((CategoriaProductos)cmbCategoriasP.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
        }catch(Exception e){
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
    
    public void editarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_actualizarProducto(?,?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            statement.setString(2, tfNombreP.getText());
            statement.setString(3, tfDescripcionP.getText());
            statement.setString(4, tfCantidadStock.getText());
            statement.setString(5, tfPrecioVU.getText());
            statement.setString(6, tfPrecioVM.getText());
            statement.setString(7, tfPrecioC.getText());
            if (files != null && !files.isEmpty() && files.get(0) != null) {
                InputStream img = new FileInputStream(files.get(0));
                statement.setBinaryStream(8, img);
            } else {
                statement.setBinaryStream(8, null); 
            }
            statement.setInt(9,((Distribuidor)cmbDistribuidores.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(10,((CategoriaProductos)cmbCategoriasP.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
        }catch(Exception e){
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ProductosDTO.getProductoDTO().getProducto() != null){
            cargarDatos(ProductosDTO.getProductoDTO().getProducto());
        }
        cmbDistribuidores.setItems(listarDistribuidores());
        cmbCategoriasP.setItems(listarCategoriasP());
        
    }
    
    public void cargarDatos(Producto producto) {
        try {
            tfProductoId.setText(Integer.toString(producto.getProductosId()));
            tfNombreP.setText(producto.getNombreProducto());
            tfDescripcionP.setText(producto.getDescripcionProducto());
            tfCantidadStock.setText(Integer.toString(producto.getCantidadStock()));
            tfPrecioVU.setText(Double.toString(producto.getPrecioVentaUnitario()));
            tfPrecioVM.setText(Double.toString(producto.getPrecioVentaMayor()));
            tfPrecioC.setText(Double.toString(producto.getPrecioCompra()));

            if (producto.getImagen() != null) {
                InputStream file = producto.getImagen().getBinaryStream();
                Image image = new Image(file);
                imgMostrar.setImage(image);
            } else {
                imgMostrar.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    public ObservableList<Distribuidor> listarDistribuidores(){
        ArrayList<Distribuidor> distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
            
                distribuidores.add(new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
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
        
        
        return FXCollections.observableList(distribuidores);
    }
    
    public ObservableList<CategoriaProductos> listarCategoriasP(){
        ArrayList<CategoriaProductos> categoriaProductos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " CALL sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaPId = resultSet.getInt("categoriaproductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
            
                categoriaProductos.add(new CategoriaProductos(categoriaPId, nombreCategoria, descripcionCategoria));
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
