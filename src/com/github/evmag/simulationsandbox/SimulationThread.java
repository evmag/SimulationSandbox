package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.Simulation;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;

import java.net.URL;

public class SimulationThread extends Thread implements Runnable{
    private boolean running;
    private boolean paused;
    private int ups; // Updates per second
    private long targetLoopTimeMillis; // Time in milliseconds per loop iteration for target UPS

    private Simulation simulation;
    private MainWindowController mainWindowController;

    public SimulationThread(MainWindowController mainWindowController) {
        this.running = false;
        this.paused = false;

        this.mainWindowController = mainWindowController; // TODO: handle this elsewhere?
    }

    public void startThread() {
        if (running) {
            System.out.println("Thread is already running..."); // TODO: handle this exception
            return;
        }

        running = true;
        paused = false;
        new Thread(this).start();
    }

    @Override
    public void run() {

        // Initialize canvas wrapper
        SimulationCanvas.getInstance().initialize();

        // Initialize simulation
        simulation.initialize();

        // Main loop
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
    }

    private void render() {
        Platform.runLater(() -> {
//            SimulationCanvas.getInstance().clear(Color.LIGHTGRAY);
            simulation.render();
        });
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        mainWindowController.setSettingsPane(simulation.getSettingsFXMLLoader());
    }

    public void setUPS(int ups) {
        this.ups = ups;
        this.targetLoopTimeMillis = 1000L / ups;
    }
}
