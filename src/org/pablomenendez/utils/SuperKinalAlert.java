/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author PEDRO
 */
public class SuperKinalAlert {
  private static SuperKinalAlert instance;
  
  private SuperKinalAlert(){
      
  }
  
  public static SuperKinalAlert getInstance(){
      if(instance == null){
          instance = new SuperKinalAlert();
      }
      return instance;
  }
  
  public void mostrarAlertaInformacion(int code){
      if(code == 400){ // codigo 400 sirve para confirmacion de registro
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Confirmacion de Registro");
           alert.setHeaderText("Confirmacion de Registro");
           alert.setContentText("Registro realizado con exito");
           alert.showAndWait();
      }else if(code == 500 ){ // codigo 500 sirve para confirmacion de Actualizacion
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Edicion Registro");
          alert.setHeaderText("Registro");
          alert.setContentText("Edicion Realizada con exito");
          alert.showAndWait();
      }else if(code == 600){ // Codigo 600 sirve para alerta de campos pendientes
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Campos Pendientes");
          alert.setHeaderText("Campos Pendientes");
          alert.setContentText("Algunos campos necesarios para el registro estan vacios ");
          alert.showAndWait();
      }else if(code == 602){
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Usuario Incorrecto");
          alert.setHeaderText("Usuario Incorrecto");
          alert.setContentText("Verifique el Usuario");
          alert.showAndWait();
      }else if(code == 005){
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Contraseña Incorrecta");
          alert.setHeaderText("Contraseña Incorrecta");
          alert.setContentText("Verifique la Contraseña");
          alert.showAndWait();
      }
  }
  public Optional <ButtonType> mostrarAlertaConfirmacion(int code){
      Optional<ButtonType> action = null;
      if(code == 404){ // codigo 404 sirve para confirmar la eliminacion de un registro
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Eliminacion de Registro");
          alert.setHeaderText("Eliminacion de Registro");
          alert.setContentText("Desea confirmar la eliminacion del registro?");
         action = alert.showAndWait();
      }else if(code == 505){ // codigo 505 sirve para confirmar la edicion de registros
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Actualizacion de Registro");
          alert.setHeaderText("Actualizacion de Registro");
          alert.setContentText("Desea confirmar la actualizacion del registro?");
         action = alert.showAndWait();
      }
      return action;
  }
  
  
  public void alertaSaludo(String usuario){
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle(" Bienvenido");
           alert.setHeaderText("Bienvenido " + usuario);
           alert.showAndWait();
    
  }
  

}
