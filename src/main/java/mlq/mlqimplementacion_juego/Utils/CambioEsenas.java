package mlq.mlqimplementacion_juego.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CambioEsenas {

    // creamos un variable tipo esenario
    private Stage Ventana;

    //constructor
    public CambioEsenas(Stage stage) {
        this.Ventana = stage;
    }

    //Metodo: Cambio de ecenas de la ventana
    public void setScene(String Paths){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths));
        try {
            Parent pane =loader.load();
            Scene scene = new Scene(pane);
            Ventana.setScene(scene);
            Ventana.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
