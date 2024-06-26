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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.model.Cliente;
import org.pablomenendez.model.TicketSoporte;
import org.pablomenendez.system.Main;

/**
 * FXML Controller class
 *
 * @author PEDRO
 */
public class MenuTicketSoporteController implements Initializable {
    Main stage;
    
    private Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfTicketId;
    @FXML
    TextArea taDescripcion;
    @FXML
    ComboBox cmbEstatus,cmbCliente,cmbFactura;
    @FXML
    TableView tblTickets;
    @FXML
    Button btnGuardar,btnVaciar,btnRegresar;
    @FXML
    TableColumn colTicketId,colDescripcion,colEstatus,colCliente,colFacturaId;

   @FXML
   public void handleButtonAction(ActionEvent event){
       if(event.getSource() == btnRegresar){
           stage.menuPrincipalView();
       }else if(event.getSource() == btnGuardar){
           if(tfTicketId.getText().equals("")){
               agregarTicket();
                cargarDatos();
           }else{
               actualizarTicket();
               cargarDatos();
           }
       }else if(event.getSource() == btnVaciar){
           vaciarFormulario();
       }
   }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarCmbEstatus();
        cargarCmbFactura();
        cmbCliente.setItems(listarClientes());
    }    
    
    public void cargarDatos(){
        tblTickets.setItems(listarTickets());
        colTicketId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
       colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcionTicket"));
       colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("estatus"));
       colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("cliente"));
       colFacturaId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("facturaId"));
    }
    
    public void cargarCmbEstatus(){
        cmbEstatus.getItems().add("En proceso");
        cmbEstatus.getItems().add("Finalizado");
    }
    
    public void cargarCmbFactura(){
        cmbFactura.getItems().add("1");
    }
    
    public void vaciarFormulario(){
        tfTicketId.clear();
        taDescripcion.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbFactura.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarFormulario(){
        TicketSoporte ts =(TicketSoporte) tblTickets.getSelectionModel().getSelectedItem();
        if(ts !=null){
            tfTicketId.setText(Integer.toString(ts.getTicketSoporteId()));
            taDescripcion.setText(ts.getDescripcionTicket());
            cmbEstatus.getSelectionModel().select(0);
            cmbCliente.getSelectionModel().select(obtenerIdexCliente());
            cmbFactura.getSelectionModel().select(0);
        }
    }
    
    public int obtenerIdexCliente(){
        int index = 0;
        String clienteTbl =  ((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getCliente();
        for(int i = 0; i <= cmbCliente.getItems().size() ;i++){
            String clienteCmb = cmbCliente.getItems().get(i).toString();
            
            if(clienteTbl.equals(clienteCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }
            
    
    public  ObservableList<TicketSoporte>listarTickets (){
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarTicketSoporte() ";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int ticketId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getNString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                int facturaId = resultSet.getInt("facturaId");
                
                tickets.add(new TicketSoporte(ticketId,descripcion,estatus,cliente,facturaId));
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
        return FXCollections.observableList(tickets);
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
    
    public void agregarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarTicketSoporte(?,?,?) ";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, taDescripcion.getText());
            statement.setInt(2, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, Integer.parseInt(cmbFactura.getSelectionModel().getSelectedItem().toString()));
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
    
    public void actualizarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_actualizarTicketSoporte(?,?,?,?,?) ";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt( tfTicketId.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, (cmbEstatus.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(4, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, Integer.parseInt(cmbFactura.getSelectionModel().getSelectedItem().toString()));
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

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
