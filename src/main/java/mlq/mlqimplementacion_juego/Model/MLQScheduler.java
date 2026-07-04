package mlq.mlqimplementacion_juego.Model;

import java.util.ArrayList;
import java.util.List;

    // esta es la clase princiapl q hace la simulacion del MLQ
public class MLQScheduler {

    private List<Process> queue1 = new ArrayList<>();

    private List<Process> queue2 = new ArrayList<>();

    private List<Process> queue3 = new ArrayList<>();

    private double currentTime;

    // Promedios de la simulación
    private double averageWT;
    private double averageCT;
    private double averageRT;
    private double averageTAT;

    // metodo para correr todo depende del esquema q seleccionen en el fxml

    public void execute(List<Process> processes, SchedulerType schedulerType) {

        queue1.clear();
        queue2.clear();
        queue3.clear();
        currentTime = 0;

        // Reset process values
        for (Process p : processes) {
            p.setRemainingTime(p.getBurstTime());
            p.setInReadyQueue(false);
            p.setResponseTime(-1);
            p.setCompletionTime(0);
        }

        switch (schedulerType) {

            case RR1_RR3_SJF:
                executeRR1RR3SJF(processes);
                break;

            case RR2_RR3_STCF:
                executeRR2RR3STCF(processes);
                break;
        }

        calculateMetrics(processes);
    }

    /*
     * Esquema:
     * Cola 1 -> RR(1)
     * Cola 2 -> RR(3)
     * Cola 3 -> SJF
     */
    private void executeRR1RR3SJF(List<Process> processes) {

        while (!allProcessesFinished(processes)) {

            addArriveProcess(processes);
            if (!queue1.isEmpty()) {
                executeRoundRobin(queue1, 1);
            } else if (!queue2.isEmpty()) {
                executeRoundRobin(queue2, 3);
            } else if (!queue3.isEmpty()) {
                executeSJF(queue3, processes);
            } else {
                currentTime++;
            }
        }

    }

    /*
     * Esquema:
     * Cola 1 -> RR(2)
     * Cola 2 -> RR(3)
     * Cola 3 -> STCF
     */
    private void executeRR2RR3STCF(List<Process> processes) {

        while (!allProcessesFinished(processes)) {

            addArriveProcess(processes);
            if (!queue1.isEmpty()) {
                executeRoundRobin(queue1, 2);
            } else if (!queue2.isEmpty()) {
                executeRoundRobin(queue2, 3);
            } else if (!queue3.isEmpty()) {
                executeSTCF(queue3, processes);
            } else {
                currentTime++;
            }
        }

    }

    /*
     * Ejecuta Round Robin.
     */
    private void executeRoundRobin(List<Process> queue, int quantum) {
        if (queue.isEmpty()) {
            return;
        }

        Process process = queue.get(0);
        for (int i = 0; i < quantum; i++) {

            if (process.getRemainingTime() <= 0) {
                break;
            }
            executeOneTime(process);

            if (process.getQueue() == 2 && !queue1.isEmpty()) {
                break;
            }

        }

        if (process.getRemainingTime() <= 0) {
            queue.remove(0);
        } else {
            queue.remove(0);
            queue.add(process);
        }

    }

    /*
     * Ejecuta SJF.
     */
    private void executeSJF(List<Process> queue, List<Process> processes) {

        Process process = getNextSJF(queue);
        while (process.getRemainingTime() > 0) {
            executeOneTime(process);

            addArriveProcess(processes);

            if (!queue1.isEmpty() || !queue2.isEmpty()) {
                return;
            }
        }
        queue.remove(process);

    }

    /*
     * Ejecuta STCF.
     */
    private void executeSTCF(List<Process> queue, List<Process> processes) {

        Process process = getNextSTCF(queue);
        executeOneTime(process);
        addArriveProcess(processes);
        if (process.getRemainingTime() <= 0) {
            queue.remove(process);
        }
    }

    // funcion para calcular todas las variables q pide el pdf (WT CT etc)
    private void calculateMetrics(List<Process> processes) {

        double totalWT = 0;
        double totalCT = 0;
        double totalRT = 0;
        double totalTAT = 0;

        for (Process process : processes) {

            // Turnaround Time
            process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());

            // Waiting Time
            process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());

            totalWT += process.getWaitingTime();
            totalCT += process.getCompletionTime();
            totalRT += process.getResponseTime();
            totalTAT += process.getTurnaroundTime();

        }

        int totalProcesses = processes.size();

        averageWT = totalWT / totalProcesses;
        averageCT = totalCT / totalProcesses;
        averageRT = totalRT / totalProcesses;
        averageTAT = totalTAT / totalProcesses;

    }

    // funcion para meter los procesos a sus colas si ya es su tiempo de llegar
    private void addArriveProcess(List<Process> processes) {
        for (Process process : processes) {

            if (!process.isInReadyQueue() && process.getArrivalTime() <= currentTime) {

                switch (process.getQueue()) {
                    case 1:
                        queue1.add(process);
                        break;
                    case 2:
                        queue2.add(process);
                        break;
                    case 3:
                        queue3.add(process);
                        break;
                }
                process.setInReadyQueue(true);

            }
        }
    }

    // METODO encargada de
    // verrificar si el proceso se esta inicializando
    // consumir una unidad de timepo
    // verificar si se completo despues de consumir la unidad de timepo
    private void executeOneTime(Process process) {

        // guardamos el punto de entrada de un proceso
        if (process.getResponseTime() == -1) {
            process.setResponseTime(currentTime);
        }

        // Ejecutar una unidad de tiempo (Disminuir uno al timepo restante)
        process.setRemainingTime(process.getRemainingTime() - 1);

        currentTime++;

        if (process.getRemainingTime() <= 0) {
            process.setCompletionTime(currentTime);
        }

    }

    // METODO encargado de :
    // verificar si todos los procesos ya finalizaron
    // (si su timepo retsante es igual a 0)da true
    private boolean allProcessesFinished(List<Process> processes) {
        for (Process process : processes) {
            if (process.getRemainingTime() > 0) {
                return false;
            }
        }
        return true;
    }

    // METODO encargado de verificar cual es el proceso mas corto
    // no Exporpiativo
    private Process getNextSJF(List<Process> queue) {

        if (queue.isEmpty()) {
            return null;
        }
        Process porcesoMaxCorto = queue.get(0);

        for (Process process : queue) {
            if (process.getBurstTime() < porcesoMaxCorto.getBurstTime()) {
                porcesoMaxCorto = process;
            }
        }
        return porcesoMaxCorto;

    }

    // METODO encargado de verificar cual es el proceso con menor timepo restante
    // Exporpiativo
    private Process getNextSTCF(List<Process> queue) {

        if (queue.isEmpty()) {
            return null;
        }
        Process porcesoMaxCorto = queue.get(0);

        for (Process process : queue) {
            if (process.getRemainingTime() < porcesoMaxCorto.getRemainingTime()) {
                porcesoMaxCorto = process;
            }
        }
        return porcesoMaxCorto;

    }

    public double getAverageWT() {
        return averageWT;
    }

    public void setAverageWT(double averageWT) {
        this.averageWT = averageWT;
    }

    public double getAverageCT() {
        return averageCT;
    }

    public void setAverageCT(double averageCT) {
        this.averageCT = averageCT;
    }

    public double getAverageRT() {
        return averageRT;
    }

    public void setAverageRT(double averageRT) {
        this.averageRT = averageRT;
    }

    public double getAverageTAT() {
        return averageTAT;
    }

    public void setAverageTAT(double averageTAT) {
        this.averageTAT = averageTAT;
    }
}