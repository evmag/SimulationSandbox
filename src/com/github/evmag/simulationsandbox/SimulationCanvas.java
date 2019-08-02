package com.github.evmag.simulationsandbox;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimulationCanvas {
    private static SimulationCanvas instance;

    private GraphicsContext g;
    private double width;
    private double height;

    private SimulationCanvas() {

    }

    public static SimulationCanvas getInstance() {
        if (instance == null) {
            instance = new SimulationCanvas();
        }
        return instance;
    }

    public void setGraphicsContext(GraphicsContext g) {
        this.g = g;

    }

    public void initialize() {
        // TODO(EM): Handle here the case of null g and throw exception
        width = g.getCanvas().getWidth();
        height = g.getCanvas().getHeight();
        clear(Color.LIGHTGRAY);
    }

    public void clear(Color color) {
        if (g == null) {
            System.out.println("Error: GraphicsContext is not set!");
            return;
        }
        g.setFill(color);
        g.fillRect(0, 0, width, height);
    }

    public void drawLine(double x1, double y1, double x2, double y2, Color color) {
        g.setStroke(color);
        g.strokeLine(x1, y1, x2, y2);
    }

    public void drawFilledRect(double x1, double y1, double width, double height, Color color) {
        g.setStroke(color);
        g.setFill(color);
        g.fillRect(x1, y1, width, height);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
