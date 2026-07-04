package mlq.mlqimplementacion_juego.Model;

public class Process {

    //Datos de archivo de entrada
    private String etiqueta;
    private double burstTime;
    private double arrivalTime;
    private int queue;
    private int priority;

    //Datos de archivo de salida
    private double remainingTime;
    private double completionTime;
    private double responseTime = -1;
    private double waitingTime;
    private double turnaroundTime;

    //Dato para proceso
    private boolean inReadyQueue = false;


    //Constructor de procesos
    public Process(String etiqueta, double burstTime, double arrivalTime, int queue, int priority) {
        this.etiqueta = etiqueta;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
        this.remainingTime = burstTime;

    }

    //===GETHER AND SETHER===

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public boolean isInReadyQueue() {
        return inReadyQueue;
    }

    public void setInReadyQueue(boolean inReadyQueue) {
        this.inReadyQueue = inReadyQueue;
    }
}