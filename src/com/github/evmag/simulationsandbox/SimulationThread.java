package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.Simulation;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class SimulationThread extends Thread implements Runnable{
    private static SimulationThread instance;
    private boolean running;
    private boolean paused;
    private int ups; // Updates per second

    private Simulation simulation;

    public SimulationThread(Simulation simulation) {
        this.running = true;
        this.paused = false;
        this.ups = 1;

        this.simulation = simulation;
        instance = this;
    }

    public static SimulationThread getInstance() {
        return instance; // TODO(EM): Handle case where instance is null
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

            if (!paused) {
                update();
            }
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

    public void exit() {
        running = false;
    }

    private void update() {
        simulation.update();
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

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
