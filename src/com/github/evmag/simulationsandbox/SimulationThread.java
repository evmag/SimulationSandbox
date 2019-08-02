package com.github.evmag.simulationsandbox;

import javafx.scene.paint.Color;

public class SimulationThread extends Thread implements Runnable{
    private boolean running;
    private int ups; // Updates per second
    private int counter = 0; // temporary way to stop the thread while testing TODO(EM): remove

    public SimulationThread() {
        this.running = true;
        this.ups = 2;
    }

    @Override
    public void run() {

        // Initialize canvas wrapper
        SimulationCanvas.getInstance().initialize();

        // Main loop
        long targetLoopTimeMillis = 1000L / ups;
        long startTime;
        long endTime;
        long deltaTimeMillis;
        while (running) {
            startTime = System.nanoTime();

            update();
            render();

            endTime = System.nanoTime();
            deltaTimeMillis = (endTime - startTime) / 1000L;

            if (deltaTimeMillis < targetLoopTimeMillis) {
                try {
                    Thread.sleep(targetLoopTimeMillis - deltaTimeMillis);
                } catch (InterruptedException e) {
                    // TODO(EM): handle exception
                }
            }

        }

    }

    private void update() {
        if (counter++ > 10) {
            running = false;
        }
//        System.out.println("Update called.");
    }

    private void render() {
        SimulationCanvas.getInstance().clear(Color.LIGHTGRAY);

        // Test code for SimulationCanvas TODO(EM): remove
        SimulationCanvas.getInstance().drawLine(10, 10, 200, 200, Color.RED);
        SimulationCanvas.getInstance().drawLine(200, 10, 10, 200, Color.RED);
        SimulationCanvas.getInstance().drawFilledRect(10, 200, 190, 100, Color.BLUE);
//        System.out.println("Render called.");
    }
}
