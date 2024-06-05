/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.pablomenendez.dto.PromocionesDTO;
import org.pablomenendez.model.Producto;
import org.pablomenendez.model.Promociones;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class FormPromocionesController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
   @FXML
    Button btnCancelar,btnGuardar;
   
   @FXML
   TextField tfPromocionId,tfPrecio,tfDescripcion,tfFechaI,tfFechaF;
   
   @FXML
   ComboBox cmbProductos;
   
   @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnCancelar){
            PromocionesDTO.getPromocionDTO().setPromociones(null);
            stage.menuPromocionesView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfPrecio.getText().equals("") && !tfFechaI.getText().equals("") && !tfFechaF.getText().equals("")){
                    agregarPromocion();
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(400);
                    stage.menuPromocionesView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfPrecio.getText().equals("")){
                        tfPrecio.requestFocus();
                    }else if(tfFechaI.getText().equals("")){
                        tfFechaI.requestFocus();
                    }else if(tfFechaF.getText().equals("")){
                        tfFechaF.requestFocus();
                    }
                }
                
               
            }else if(op == 2){
                if(!tfPrecio.getText().equals("") && !tfFechaI.getText().equals("") && !tfFechaF.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK){
                    editarPromocion();
                        PromocionesDTO.getPromocionDTO().setPromociones(null);
                        SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                        stage.menuPromocionesView();
                    }else{
                        stage.menuPromocionesView();
                    }
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInformacion(600);
                    if(tfPrecio.getText().equals("")){
                        tfPrecio.requestFocus();
                    }else if(tfFechaI.getText().equals("")){
                        tfFechaI.requestFocus();
                    }else if(tfFechaF.getText().equals("")){
                        tfFechaF.requestFocus();
                    }
                }
                
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(PromocionesDTO.getPromocionDTO().getPromociones() != null){
            cargarDatos(PromocionesDTO.getPromocionDTO().getPromociones());
        }
        cmbProductos.setItems(listarProductos());
        
    }
    
    public void cargarDatos(Promociones Promocion){
        tfPromocionId.setText(Integer.toString(Promocion.getPromocionesId()));
        tfPrecio.setText(Double.toString(Promocion.getPrecioPromocion()));
        tfDescripcion.setText(Promocion.getDescripcionPromocion());
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
        tfFechaI.setText(formatoFecha.format(Promocion.getFechaInicio()));
        tfFechaF.setText(formatoFecha.format(Promocion.getFechaFinalizacion()));
    }
    
    public void agregarPromocion(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromocion(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfPrecio.getText());
            statement.setString(2, tfDescripcion.getText());
            statement.setString(3, tfFechaI.getText());
            statement.setString(4, tfFechaF.getText());
            statement.setInt(5,((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductosId());
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
    
    public void editarPromocion(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_actualizarPromocion(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionId.getText()));
            statement.setString(2, tfPrecio.getText());
            statement.setString(3, tfDescripcion.getText());
            statement.setString(4, tfFechaI.getText());
            statement.setString(5, tfFechaF.getText());
            statement.setInt(6,((Producto)cmbProductos.getSelectionModel().getSelectedItem()).getProductosId());
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
    
    public ObservableList<Producto> listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " call sp_listarProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productosId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagenProducto = resultSet.getBlob("imagen");
                String distribuidor = resultSet.getString("distribuidor");
                String categoria = resultSet.getString("categoria");
            
                productos.add(new Producto(productoId, nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoria));
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
        
        
        return FXCollections.observableList(productos);
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
