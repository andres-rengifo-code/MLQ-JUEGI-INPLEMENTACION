package mlq.mlqimplementacion_juego.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import mlq.mlqimplementacion_juego.Model.*;
import mlq.mlqimplementacion_juego.Model.Process;

import java.io.File;
import java.util.List;

public class MainController {

    @FXML
    private Label lblArchivo;

    @FXML
    private TableView<Process> tablaProcesos;

    @FXML
    private RadioButton radioEsquema1;

    @FXML
    private RadioButton radioEsquema2;

    @FXML
    private RadioButton radioEsquema3;

    private File archivoEntrada;
    private List<Process> procesos;

    private final FileManager fileManager = new FileManager();
    private final MLQScheduler scheduler = new MLQScheduler();

    // =========================
    // CARGAR ARCHIVO
    // =========================
    @FXML
    private void handleCargarArchivo() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos TXT", "*.txt")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                archivoEntrada = file;

                procesos = fileManager.readFile(file);

                lblArchivo.setText("Archivo cargado: " + file.getName());

                // Aquí podrías cargar la tabla si ya la tienes configurada
                tablaProcesos.getItems().setAll(procesos);

            } catch (Exception e) {
                lblArchivo.setText("Error al leer archivo");
                e.printStackTrace();
            }
        }
    }

    // =========================
    // EJECUTAR SIMULACIÓN
    // =========================
    @FXML
    private void handleEjecutar() {

        if (procesos == null || procesos.isEmpty()) {
            lblArchivo.setText("Primero carga un archivo");
            return;
        }

        try {

            SchedulerType type = getSelectedScheme();

            scheduler.execute(procesos, type);

            // Guardar archivo de salida
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("resultado.txt");

            File output = fileChooser.showSaveDialog(null);

            if (output != null) {

                fileManager.writeFile(
                        output,
                        procesos,
                        scheduler.getAverageWT(),
                        scheduler.getAverageCT(),
                        scheduler.getAverageRT(),
                        scheduler.getAverageTAT()
                );

                lblArchivo.setText("Simulación completada y archivo generado");

            }

        } catch (Exception e) {
            lblArchivo.setText("Error en simulación");
            e.printStackTrace();
        }
    }

    // =========================
    // SELECCION DE ESQUEMA
    // =========================
    private SchedulerType getSelectedScheme() {

        if (radioEsquema1.isSelected()) {
            return SchedulerType.RR1_RR3_SJF;
        }

        if (radioEsquema2.isSelected()) {
            return SchedulerType.RR2_RR3_STCF;
        }

        return SchedulerType.RR1_RR3_SJF;
    }
}