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
import org.pablomenendez.dto.PromocionesDTO;
import org.pablomenendez.model.Promociones;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class MenuPromocionesController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblPromociones;
    
    @FXML
    TableColumn colPromocionId,colPrecio,colDescripcion,colFechaI,colFechaF,colProductoId;
    
    @FXML
    Button btnRegresar,btnAgregar,btnActualizar,btnBorrar,btnBuscar;
    
    @FXML
    TextField tfPromocionId;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formPromocionesView(1);
        }else if(event.getSource() == btnActualizar){
            PromocionesDTO.getPromocionDTO().setPromociones((Promociones)tblPromociones.getSelectionModel().getSelectedItem());
            stage.formPromocionesView(2);
        }else if(event.getSource() == btnBorrar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                eliminarPromocion(((Promociones)tblPromociones.getSelectionModel().getSelectedItem()).getPromocionesId());
                cargarDatos();
            }
        }else if (event.getSource() == btnBuscar){
            tblPromociones.getItems().clear();
            if(tfPromocionId.getText().equals("")){
                cargarDatos();
            
            }else{
                op = 3;
                cargarDatos();
            }
        }
    }
    
    public void cargarDatos(){
        if(op == 3){
            tblPromociones.getItems().add(buscarPromocion());
            op = 0;
        }else{
            tblPromociones.setItems(listarPromociones()); 
        }
            colPromocionId.setCellValueFactory(new PropertyValueFactory<Promociones, Integer>("promocionesId"));
            colPrecio.setCellValueFactory(new PropertyValueFactory<Promociones, Double>("precioPromocion"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("descripcionPromocion"));
            colFechaI.setCellValueFactory(new PropertyValueFactory<Promociones, Date>("fechaInicio"));
            colFechaF.setCellValueFactory(new PropertyValueFactory<Promociones, Date>("fechaFinalizacion"));
            colProductoId.setCellValueFactory(new PropertyValueFactory<Promociones, String>("producto"));;
        
    }
    
    public ObservableList<Promociones> listarPromociones(){
        ArrayList<Promociones> promociones = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = " call sp_listarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionesId");
                Double precioProm = resultSet.getDouble("precioPromocion");
                String descripcionProm = resultSet.getString("descripcionPromocion");
                Date fechaI = resultSet.getDate("fechaInicio");
                Date fechaF = resultSet.getDate("fechaFinalizacion");
                String productoId = resultSet.getString("Producto");
            
                promociones.add(new Promociones(promocionId,precioProm,descripcionProm,fechaI,fechaF,productoId));
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
        
        
        return FXCollections.observableList(promociones);
    }
    
    public void eliminarPromocion(int promId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,promId);
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
    
    public Promociones buscarPromocion(){
        Promociones promocion = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfPromocionId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int promocionId = resultSet.getInt("promocionesId");
                Double precioProm = resultSet.getDouble("precioPromocion");
                String descripcionProm = resultSet.getString("descripcionPromocion");
                Date fechaI = resultSet.getDate("fechaInicio");
                Date fechaF = resultSet.getDate("fechaFinalizacion");
                String productoId = resultSet.getString("Producto");
            
                promocion = new Promociones(promocionId, precioProm, descripcionProm, fechaI, fechaF, productoId);

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
        return promocion;
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
