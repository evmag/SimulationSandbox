package com.github.evmag.simulationsandbox;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainWindowController {
    @FXML
    private Canvas simulationCanvas;
    @FXML
    private StackPane canvasStackPane;

    @FXML
    public void initialize() {

        simulationCanvas.widthProperty().bind(canvasStackPane.widthProperty());
        simulationCanvas.heightProperty().bind(canvasStackPane.heightProperty());
        SimulationCanvas.getInstance().setGraphicsContext(simulationCanvas.getGraphicsContext2D());


    }

}
