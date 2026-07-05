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
    private RadioButton radioEsquema1;

    @FXML
    private RadioButton radioEsquema2;

    @FXML
    private TextArea txtEstado;

    // variables globales para guardar el archivo q sube el usuario y la lista de
    // procesos
    private File archivoEntrada;
    private List<Process> procesos;

    private final FileManager fileManager = new FileManager();
    private final MLQScheduler scheduler = new MLQScheduler();

    // Metodo conexion con el boton cargar archivo
    // boton pa cargar el archivo txt, abre el explorador de archivos
    @FXML
    private void handleCargarArchivo() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos TXT", "*.txt"));

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                archivoEntrada = file;

                procesos = fileManager.readFile(file);

                lblArchivo.setText("Archivo cargado: " + file.getName());

                if (txtEstado != null) {
                    txtEstado.setText("Archivo cargado con éxito: " + file.getName() + "\nProcesos encontrados: "
                            + procesos.size() + "\n\nListo para ejecutar simulación.");
                }

            } catch (Exception e) {
                lblArchivo.setText("Error al leer archivo");
                if (txtEstado != null) {
                    txtEstado.setText("Error al leer archivo:\n" + e.getMessage());
                }
                e.printStackTrace();
            }
        }
    }

    // Metodo conexion con el boton ejecutar conectando la logica de mlq

    @FXML
    private void handleEjecutar() {

        if (procesos == null || procesos.isEmpty()) {
            // validacion para que node error si el usuario le da ejecutar sin cargar
            // archivo
            lblArchivo.setText("Primero carga un archivo");
            return;
        }

        try {

            SchedulerType type = getSelectedScheme();

            scheduler.execute(procesos, type);

            // Guardar archivo de salida (te pide donde guardarlo)
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
                        scheduler.getAverageTAT());

                lblArchivo.setText("Simulación completada y archivo generado");

                if (txtEstado != null) {
                    txtEstado.setText("¡Simulación completada con éxito!\n");
                    txtEstado.appendText("Archivo generado: " + output.getName() + "\n\n");
                    txtEstado.appendText("=== Promedios ===\n");
                    txtEstado.appendText(String.format("Waiting Time (WT): %.2f\n", scheduler.getAverageWT()));
                    txtEstado.appendText(String.format("Completion Time (CT): %.2f\n", scheduler.getAverageCT()));
                    txtEstado.appendText(String.format("Response Time (RT): %.2f\n", scheduler.getAverageRT()));
                    txtEstado.appendText(String.format("Turnaround Time (TAT): %.2f\n", scheduler.getAverageTAT()));
                }

            }

        } catch (Exception e) {
            lblArchivo.setText("Error en simulación");
            if (txtEstado != null) {
                txtEstado.setText("Error en simulación:\n" + e.getMessage());
            }
            e.printStackTrace();
        }
    }

    // Metodo pa saber q radio button marco el usuario (q esquema)

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