package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainWindowController {
    @FXML
    private Canvas simulationCanvas;
    @FXML
    private StackPane canvasStackPane;
    @FXML
    private ToggleButton pauseButton;

    private SimulationThread simulationThread;

    @FXML
    public void initialize() {

        simulationCanvas.widthProperty().bind(canvasStackPane.widthProperty());
        simulationCanvas.heightProperty().bind(canvasStackPane.heightProperty());
        SimulationCanvas.getInstance().setGraphicsContext(simulationCanvas.getGraphicsContext2D());


    }

    public void initializeThread() {
        GameOfLifeMain gol = new GameOfLifeMain(50,50);
        simulationThread = new SimulationThread(gol);
        new Thread(simulationThread).start();
    }


    public void stop() {
        simulationThread.exit();
    }

    @FXML
    private void handlePauseButton() {
        simulationThread.setPaused(pauseButton.isSelected());
    }



}
