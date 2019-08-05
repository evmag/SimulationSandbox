package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.Simulation;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class SimulationThread extends Thread implements Runnable{
    private boolean running;
    private int ups; // Updates per second
    private int counter = 0; // temporary way to stop the thread while testing TODO(EM): remove

    private Simulation simulation;

    public SimulationThread(Simulation simulation) {
        this.running = true;
        this.ups = 5;

        this.simulation = simulation;
    }

    @Override
    public void run() {

        // Initialize canvas wrapper
        SimulationCanvas.getInstance().initialize();

        // Initialize simulation
        simulation.initialize();

        // Main loop
        long targetLoopTimeMillis = 1000L / ups;
        long startTime;
        long endTime;
        long deltaTimeMillis;
        double currUps;
        while (running) {
            startTime = System.nanoTime();

            update();
            render();

            deltaTimeMillis = (System.nanoTime() - startTime) / 1_000_000L;

            while (deltaTimeMillis < targetLoopTimeMillis) {
                try {
                    Thread.sleep(targetLoopTimeMillis - deltaTimeMillis);
                } catch (InterruptedException e) {
                    // TODO(EM): handle exception
                }
                deltaTimeMillis = (System.nanoTime() - startTime) / 1000L;
            }
            endTime = System.nanoTime();
//            currUps = 1.0E9/(endTime - startTime);
//            System.out.println("Current UPS: " + currUps);
        }

    }

    private void update() {
        simulation.update();
        if (counter++ > 100) {
            running = false;
        }
//        System.out.println("Update called.");
    }

    private void render() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                SimulationCanvas.getInstance().clear(Color.LIGHTGRAY);
                simulation.render();
            }
        });


        // Test code for SimulationCanvas TODO(EM): remove
//        SimulationCanvas.getInstance().drawLine(10, 10, 200, 200, Color.RED);
//        SimulationCanvas.getInstance().drawLine(200, 10, 10, 200, Color.RED);
//        SimulationCanvas.getInstance().drawFilledRect(10, 200, 190, 100, Color.BLUE);
//        System.out.println("Render called.");
    }
}
