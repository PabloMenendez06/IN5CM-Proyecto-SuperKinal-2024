/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pablomenendez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pablomenendez.controller.FormAsignarEncargadoController;
import org.pablomenendez.controller.FormCargoController;
import org.pablomenendez.controller.FormCategoriaProductoController;
import org.pablomenendez.controller.FormClienteController;
import org.pablomenendez.controller.FormComprasController;
import org.pablomenendez.controller.FormDetalleFacturasController;
import org.pablomenendez.controller.FormDistribuidoresController;
import org.pablomenendez.controller.FormEmpleadoController;
import org.pablomenendez.controller.FormFacturasController;
import org.pablomenendez.controller.FormLoginController;
import org.pablomenendez.controller.FormProductosController;
import org.pablomenendez.controller.FormPromocionesController;
import org.pablomenendez.controller.FormRegistrarUsuarioController;
import org.pablomenendez.controller.MenuCargoController;
import org.pablomenendez.controller.MenuCategoriaProductosController;
import org.pablomenendez.controller.MenuClienteController;
import org.pablomenendez.controller.MenuComprasController;
import org.pablomenendez.controller.MenuDistribuidoresController;
import org.pablomenendez.controller.MenuEmpleadosController;
import org.pablomenendez.controller.MenuFacturasController;
import org.pablomenendez.controller.MenuPrincipalController;
import org.pablomenendez.controller.MenuProductosController;
import org.pablomenendez.controller.MenuPromocionesController;
import org.pablomenendez.controller.MenuTicketSoporteController;

/**
 *
 * @author PEDRO
 */
public class Main extends Application {
    private final String URLVIEW = "/org/pablomenendez/view/";
    private Stage stage;
    private Scene scene;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Super Kinal Gragas App");
        formLoginView();

        stage.show();

    }
    
    public Initializable switchScene(String fxmlName, int width,int heigth)throws Exception {
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName );
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        
        scene = new Scene((AnchorPane)loader.load(file),width,heigth);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        
        return resultado;
    } 
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView =(MenuPrincipalController) switchScene("MenuPrincipalView.fxml",900,675);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuClienteView(){
        try{
            MenuClienteController menuClienteView =(MenuClienteController) switchScene("MenuClienteView.fxml",1200,750);
            menuClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void  formClienteView(int op){
        try{
            FormClienteController formClienteView =  (FormClienteController) switchScene("FormClienteView.fxml",500,750);
            formClienteView.setOp(op);
            formClienteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCargoView(){
        try{
            MenuCargoController menuCargoView =(MenuCargoController) switchScene("MenuCargoView.fxml",1200,750);
            menuCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCargoView(int op){
        try{
            FormCargoController formCargoView = (FormCargoController) switchScene("FormCargoView.fxml",500,750);
            formCargoView.setOp(op);
            formCargoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuComprasView(){
        try{
            MenuComprasController menuComprasview = (MenuComprasController) switchScene("MenuComprasView.fxml",1200,750);
            menuComprasview.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    public void formCompraView(int op){
        try{
            FormComprasController formComprasView = (FormComprasController) switchScene("FormComprasView.fxml",500,750);
            formComprasView.setOp(op);
            formComprasView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuCategoriaProducto(){
        try{
            MenuCategoriaProductosController menuCategoriaProductosView = (MenuCategoriaProductosController) switchScene("MenuCategoriaProductosView.fxml",1200,750);
            menuCategoriaProductosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formCategoriaProducto(int op){
        try{
            FormCategoriaProductoController formCategoriaProductoView = (FormCategoriaProductoController) switchScene("FormCategoriaProductoView.fxml",500,750);
            formCategoriaProductoView.setOp(op);
            formCategoriaProductoView.setStage(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView(){
        try{
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController) switchScene("MenuTicketSoporteView.fxml",1200,750);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuDistribuidoresView(){
        try{
            MenuDistribuidoresController menuCargosView = (MenuDistribuidoresController)switchScene("MenuDistribuidoresView.fxml", 1200, 750);
            menuCargosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formDistribuidoresView(int op){
        try{
            FormDistribuidoresController formDistribuidorView = (FormDistribuidoresController)switchScene("FormDistribuidoresView.fxml", 500, 750);
            formDistribuidorView.setOp(op);
            formDistribuidorView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleado = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml", 1200, 750);
            menuEmpleado.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formEmpleadosView(int op){
        try{
            FormEmpleadoController formEmpleado = (FormEmpleadoController)switchScene("FormEmpleadoView.fxml", 500, 750);
            formEmpleado.setOp(op);
            formEmpleado.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formAsignarEncargadoView(){
        try{
            FormAsignarEncargadoController formAsignarE = (FormAsignarEncargadoController)switchScene("FormAsignarEncargadoView.fxml", 500, 750);
            formAsignarE.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void menuProductosView(){
        try{
            MenuProductosController menuProductos = (MenuProductosController)switchScene("MenuProductosView.fxml", 1200, 750);
            menuProductos.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void formProductosView(int op){
        try{
            FormProductosController formProductos = (FormProductosController)switchScene("FormProductosView.fxml", 1200, 750);
            formProductos.setOp(op);
            formProductos.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuPromocionesView(){
        try{
            MenuPromocionesController menuPromociones = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml", 1200, 750);
            menuPromociones.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void formPromocionesView(int op){
        try{
            FormPromocionesController formPromociones = (FormPromocionesController)switchScene("FormPromocionesView.fxml", 500, 750);
            formPromociones.setOp(op);
            formPromociones.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
     public void menuFacturasView(){
        try{
            MenuFacturasController menuFacturas = (MenuFacturasController)switchScene("MenuFacturasView.fxml", 1200, 750);
            menuFacturas.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     public void formFacturasView(int op){
        try{
            FormFacturasController formFacturas = (FormFacturasController)switchScene("FormFacturasView.fxml", 500, 750);
            formFacturas.setOp(op);
            formFacturas.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     public void formDetalleFacturaView(int op){
        try{
            FormDetalleFacturasController formDetalleFactura = (FormDetalleFacturasController)switchScene("FormDetalleFacturasView.fxml", 500, 750);
            formDetalleFactura.setOp(op);
            formDetalleFactura.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     
     public void formLoginView(){
         try{
             FormLoginController formLoginView = (FormLoginController)switchScene("FormLoginView.fxml",500,750);
             formLoginView.setStage(this);
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
     }
     
     public void formRegistrarUsuarioView(){
         try{
             FormRegistrarUsuarioController formRegistrarUsuarioView = (FormRegistrarUsuarioController)switchScene("FormRegistrarUsuarioView.fxml",500,750);
             formRegistrarUsuarioView.setStage(this);
         }catch(Exception e){
             System.out.println(e.getMessage());
         }
     }
    
    
    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
