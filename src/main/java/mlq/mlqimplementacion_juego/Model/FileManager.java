package mlq.mlqimplementacion_juego.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    // Lee un archivo de entrada y devuelve la lista de procesos listos pa usarse
    public List<Process> readFile(File file) throws IOException {

        // Creamos una lista de procesos vacia
        List<Process> processes = new ArrayList<>();

        // BufferedReader permite leer el archivo línea por línea pa que sea mas facil
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                // Eliminar espacios al inicio y final
                line = line.trim();

                // Ignorar líneas vacías
                if (line.isEmpty()) {
                    continue;
                }

                // Ignorar comentarios
                if (line.startsWith("#")) {
                    continue;
                }

                // partimos la linea por los puntos y comas (;) pa sacar cada dato
                String[] data = line.split(";");

                String etiqueta = data[0].trim();
                double burstTime = Double.parseDouble(data[1].trim());
                double arrivalTime = Double.parseDouble(data[2].trim());
                int queue = Integer.parseInt(data[3].trim());
                int priority = Integer.parseInt(data[4].trim());

                // armamos el objeto proceso con toda la info que acabamos de leer
                Process process = new Process(
                        etiqueta,
                        burstTime,
                        arrivalTime,
                        queue,
                        priority);

                processes.add(process);
            }
        }

        return processes;
    }

    // Metodo para escribir los resultados de la simulacion en un archivo nuevo
    // Le pasamos donde guardar, la lista de procesos ya modificada y los promedios
    // globales
    public void writeFile(File file, List<Process> processes,
            double wt, double ct, double rt, double tat) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            // Encabezado pa que se entienda que es cada columna en el txt
            writer.write("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT");
            writer.newLine();

            // Ciclo para escribir los datos de cada proceso
            for (Process p : processes) {

                // concatenamos todos los datos con punto y coma pa escribirlos en el formato
                // correcto
                writer.write(
                        p.getEtiqueta() + ";" +
                                p.getBurstTime() + ";" +
                                p.getArrivalTime() + ";" +
                                p.getQueue() + ";" +
                                p.getPriority() + ";" +
                                p.getWaitingTime() + ";" +
                                p.getCompletionTime() + ";" +
                                p.getResponseTime() + ";" +
                                p.getTurnaroundTime());

                writer.newLine();
            }

            writer.newLine();

            // Promedios finales los ponemos hasta abajo pa que se vean ordenados
            writer.write(
                    "# WT=" + wt +
                            "; CT=" + ct +
                            "; RT=" + rt +
                            "; TAT=" + tat + ";");

        }
    }

}