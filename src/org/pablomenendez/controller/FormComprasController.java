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
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.pablomenendez.dao.Conexion;
import org.pablomenendez.dto.ComprasDTO;
import org.pablomenendez.model.Compras;
import org.pablomenendez.system.Main;
import org.pablomenendez.utils.SuperKinalAlert;

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
    private static ResultSet resultSet = null;
    
   @FXML
    Button btnRegresarFMA,btnGuardar;
   
   @FXML
   TextField tfCompraId,tfFecha;
   
   @FXML
private void handleButtonAction(ActionEvent event) {
    if (event.getSource() == btnRegresarFMA) {
        ComprasDTO.getComprasDTO().setCompras(null);
        stage.menuComprasView();
    } else if (event.getSource() == btnGuardar) {
        
            stage.menuComprasView();
        if (op == 2) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarCompra();
                ComprasDTO.getComprasDTO().setCompras(null);
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.menuComprasView();
            } else {
                stage.menuComprasView();
            }
        }
    }
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ComprasDTO.getComprasDTO().getCompras() != null){
            cargarDatos(ComprasDTO.getComprasDTO().getCompras());
        }
        
    }    
    
    public void cargarDatos(Compras compra) {
    tfCompraId.setText(Integer.toString(compra.getCompraId()));

    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
    tfFecha.setText(formatoFecha.format(compra.getFechaCompra()));
}

    
    public void editarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "CALL sp_ActualizarCompra(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setString(2,tfFecha.getText());
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
