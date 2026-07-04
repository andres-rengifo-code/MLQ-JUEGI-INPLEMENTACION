package mlq.mlqimplementacion_juego.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

     //Lee un archivo de entrada y devuelve la lista de procesos.

    public List<Process> readFile(File file) throws IOException {

        //Creamos una lista de procesos
        List<Process> processes = new ArrayList<>();

        // BufferedReader permite leer el archivo línea por línea.
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

                String[] data = line.split(";");

                String etiqueta = data[0].trim();
                int burstTime = Integer.parseInt(data[1].trim());
                int arrivalTime = Integer.parseInt(data[2].trim());
                int queue = Integer.parseInt(data[3].trim());
                int priority = Integer.parseInt(data[4].trim());

                Process process = new Process(
                        etiqueta,
                        burstTime,
                        arrivalTime,
                        queue,
                        priority
                );

                processes.add(process);
            }
        }

        return processes;
    }

    public void writeFile(File file, List<Process> processes,
                          double wt, double ct, double rt, double tat) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            // Encabezado
            writer.write("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT");
            writer.newLine();

            // Procesos
            for (Process p : processes) {

                writer.write(
                        p.getEtiqueta() + ";" +
                                p.getBurstTime() + ";" +
                                p.getArrivalTime() + ";" +
                                p.getQueue() + ";" +
                                p.getPriority() + ";" +
                                p.getWaitingTime() + ";" +
                                p.getCompletionTime() + ";" +
                                p.getResponseTime() + ";" +
                                p.getTurnaroundTime()
                );

                writer.newLine();
            }

            writer.newLine();

            // Promedios finales
            writer.write(
                    "# WT=" + wt +
                            "; CT=" + ct +
                            "; RT=" + rt +
                            "; TAT=" + tat + ";"
            );

        }
    }


}