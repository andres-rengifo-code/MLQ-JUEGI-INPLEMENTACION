module mlq.mlqimplementacion_juego {
    requires javafx.controls;
    requires javafx.fxml;


    opens mlq.mlqimplementacion_juego to javafx.fxml;
    opens mlq.mlqimplementacion_juego.Controller to javafx.fxml;
    opens mlq.mlqimplementacion_juego.Utils to javafx.fxml;
    opens mlq.mlqimplementacion_juego.Model to javafx.fxml;

    exports mlq.mlqimplementacion_juego;
    exports mlq.mlqimplementacion_juego.Controller;
    exports mlq.mlqimplementacion_juego.Utils;
    exports mlq.mlqimplementacion_juego.Model;
}