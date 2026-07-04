package mlq.mlqimplementacion_juego.Model;


// Enumeración que representa los esquemas de planificación
// disponibles para el algoritmo MLQ.

public enum SchedulerType {

    /*
     * Cola 1 -> Round Robin (Quantum = 1)
     * Cola 2 -> Round Robin (Quantum = 3)
     * Cola 3 -> Shortest Job First (SJF)
     */
    RR1_RR3_SJF,


    /*
     * Cola 1 -> Round Robin (Quantum = 2)
     * Cola 2 -> Round Robin (Quantum = 3)
     * Cola 3 -> Shortest Time to Completion First (STCF)
     */
    RR2_RR3_STCF
}