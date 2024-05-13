/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.controller;

import org.pablomenendez.system.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

/**
 *
 * @author PEDRO
 */
public class MenuPrincipalController implements Initializable{
    private Main stage;
    @FXML
    MenuItem btnClientes,btnCargo, btnCompras,btnCategoriaProductos,btnTicketSoporte,btnDistribuidores,btnEmpleados;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnClientes){
            stage.menuClienteView();
        }else if(event.getSource() == btnCargo){
            stage.menuCargoView();
        }else if(event.getSource() == btnCompras){
            stage.menuComprasView();
        }else if(event.getSource() ==btnCategoriaProductos ){
            stage.menuCategoriaProducto();
        }else if(event.getSource() == btnTicketSoporte){
            stage.menuTicketSoporteView();
        }else if(event.getSource() == btnDistribuidores){
            stage.menuDistribuidoresView();
        }else if(event.getSource() == btnEmpleados){
            stage.menuEmpleadosView();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
