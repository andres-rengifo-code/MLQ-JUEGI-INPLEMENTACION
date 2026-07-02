module mlq.mlqimplementacion_juego {
    requires javafx.controls;
    requires javafx.fxml;


    opens mlq.mlqimplementacion_juego to javafx.fxml;
    exports mlq.mlqimplementacion_juego;
}