package mlq.mlqimplementacion_juego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mlq.mlqimplementacion_juego.Utils.CambioEsenas;





public class
StartApp extends Application {
    private Stage ventana;
    private CambioEsenas esena;


    public static void main(String[] args){
        launch();
    }


    //Iniciacion de la ventana
    @Override
    public void start (Stage stage){
        ventana=stage;
        esena=new CambioEsenas(ventana);
        esena.setScene("/mlq/mlqimplementacion_juego/Mainview.fxml");

    }

}
