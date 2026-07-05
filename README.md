# Proyecto: Planificador de Procesos MLQ (Colas Multinivel)

Este proyecto es una implementación gráfica y funcional de un **Planificador de Procesos Multilevel Queue (MLQ)**, desarrollado como parte de la entrega para la materia de **Sistemas Operativos**.

## Descripción

La aplicación simula la gestión de procesos en un sistema operativo empleando múltiples colas, cada una con su propia prioridad y algoritmo de planificación. Permite visualizar cómo los procesos ingresan, esperan en las colas de listos, se ejecutan en la CPU, y finalmente terminan, calculando métricas importantes como el **Tiempo de Espera**, **Tiempo de Respuesta**, y **Tiempo de Retorno**.

### Características Principales
* Interfaz gráfica interactiva desarrollada con **JavaFX**.
* Implementación fiel de los algoritmos de planificación.
* Gestión de ráfagas de CPU, tiempos de llegada, y prioridades.
* Importación/Exportación de procesos (usando `FileManager`).
* Cálculos estadísticos y métricas precisas.

## Requisitos Previos

Para ejecutar el proyecto en tu entorno local, asegúrate de tener instalado:
* **Java Development Kit (JDK)** versión 17 o superior.
* **Apache Maven** (para la gestión de dependencias y construcción).

## Instrucciones de Ejecución

1. Clona el repositorio en tu máquina local:
   ```bash
   git clone git@github.com:andres-rengifo-code/MLQ-JUEGI-INPLEMENTACION.git
   ```
2. Accede al directorio del proyecto:
   ```bash
   cd MLQ-Implementacion_Juego
   ```
3. Limpia, compila y ejecuta la aplicación usando Maven:
   ```bash
   mvn clean javafx:run
   ```

*(Nota: Si usas IntelliJ IDEA, simplemente puedes abrir la carpeta del proyecto, dejar que Maven descargue las dependencias, y correr la clase `StartApp`)*.

## Estructura del Proyecto

* `Controller/`: Contiene los controladores de la interfaz gráfica (ej. `MainController`).
* `Model/`: Contiene la lógica del planificador (`MLQScheduler`), la entidad `Process`, constantes (`SchedulerType`), y la gestión de archivos (`FileManager`).
* `View/`: Contiene los archivos FXML de la interfaz visual.

## Autor
Andrés Felipe Rengifo Rodriguez
