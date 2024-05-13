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
import org.pablomenendez.dto.CargoDTO;
import org.pablomenendez.model.Cargo;
import org.pablomenendez.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuCargoController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TableView tblCargo;
    @FXML
    TableColumn colCargoId,colNombreCargo,colDescripcionCargo;
    @FXML
    Button  btnRegresar,btnAgregar,btnActualizar,btnBorrar,btnBuscar;
    @FXML
    TextField tfCargoId;
    
    
    
    
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }    
    @FXML
    public void handleButtonAction(ActionEvent event){
       if(event.getSource() == btnAgregar){
           stage.formCargoView(1);
       }else if(event.getSource() == btnActualizar){
           CargoDTO.getCargoDTO().setCargo((Cargo)tblCargo.getSelectionModel().getSelectedItem());
           
           stage.formCargoView(2);
       }else if(event.getSource() == btnRegresar){
           stage.menuPrincipalView();
       
       }else if(event.getSource() == btnBorrar){
           int carId = ((Cargo) tblCargo.getSelectionModel().getSelectedItem()).getCargoId();
           borrarCargo(carId);
           cargarDatos();
       }else if(event.getSource() == btnBuscar){
           tblCargo.getItems().clear();
           if(tfCargoId.getText().equals("")){
               cargarDatos();
           }else{
               op = 3;
               cargarDatos();
           }
       }
    }
    
    public void cargarDatos(){
        if(op == 3){
            tblCargo.getItems().add(buscarCargo());
            op = 0;
        }else{
            tblCargo.setItems(listarCargos());
        }

        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("descripcionCargo"));
        
    }
    
    public ObservableList<Cargo>listarCargos(){
        ArrayList<Cargo>cargos = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCargo()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId,nombreCargo,descripcionCargo));
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
        return FXCollections.observableList(cargos);
    }
    
     public void borrarCargo(int carId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, carId);
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
     
     public Cargo buscarCargo(){
         Cargo cargo = null;
         try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfCargoId.getText()));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                
                cargo = new Cargo(cargoId,nombreCargo,descripcionCargo);
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
         return cargo;
     }



    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
