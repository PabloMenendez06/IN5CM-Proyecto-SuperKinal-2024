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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.ClienteDTO;
import org.pablomenendez.model.Cliente;
import org.pablomenendez.report.GenerarReporte;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class MenuClienteController implements Initializable {
    private Main stage;
    private int op;

    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
            
    /**
     * Initializes the controller class.
     */
    @FXML
    TableView tblClientes;
    @FXML
    TableColumn colClienteId,colNombre,colApellido,colTelefono,colDireccion,colNit;  
    @FXML
    Button btnRegresar,btnAgregar,btnActualizar,btnBorrar,btnBuscar,btnReporte;
    @FXML
    TextField tfClienteId;
    
     
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formClienteView(1);
        }else if(event.getSource() == btnActualizar){
            ClienteDTO.getClienteDTO().setCliente( (Cliente) tblClientes.getSelectionModel().getSelectedItem());
            stage.formClienteView(2);
        }else if(event.getSource() == btnBorrar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                borrarCliente(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getClienteId());
                cargarDatos();
            }
        }else if(event.getSource() == btnBuscar){
            tblClientes.getItems().clear();
            if(tfClienteId.getText().equals("")){
                cargarDatos();
            }else{
            op = 3;
            cargarDatos();
            }
        }else if(event.getSource() == btnReporte){
            GenerarReporte.getInstance().generarClientes();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
    }   
    public void cargarDatos(){
        if(op == 3){
            tblClientes.getItems().add(buscarCliente());
            op = 0;
        }else{
             tblClientes.setItems(listarClientes());
        }
        
        colClienteId.setCellValueFactory(new PropertyValueFactory<Cliente,Integer>("clienteId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente,String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente,String>("direccion"));
        colNit.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nit"));
    }

    public ObservableList<Cliente>listarClientes(){
        ArrayList<Cliente>clientes = new ArrayList<>();
        try{
            
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                clientes.add(new Cliente(clienteId,nombre,apellido,telefono,direccion,nit));
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
        
        
        return FXCollections.observableList(clientes);
    }
    
    public void borrarCliente(int clieId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "Call sp_eliminarCliente(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, clieId);
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
    
    public Cliente buscarCliente(){
        Cliente cliente = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarCliente(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfClienteId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                String nit = resultSet.getString("nit");
                
                cliente = new Cliente(clienteId,nombre,apellido,telefono,direccion,nit);
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
        return cliente;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
