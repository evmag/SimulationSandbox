package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

public class MainWindowController {
    @FXML
    private Canvas simulationCanvas;
    @FXML
    private StackPane canvasStackPane;
    @FXML
    private Button startButton;
    @FXML
    private ToggleButton pauseButton;
    @FXML
    private Button stopButton;

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


    public void stopThread() {
        if (simulationThread != null) {
            simulationThread.exit();
        }
    }

    @FXML
    private void handleControlButtons(ActionEvent e) {
        String sourceId = ((Control) e.getSource()).getId();
        switch (sourceId) {
            case "startButton":
                initializeThread();
                startButton.setDisable(true);
                pauseButton.setDisable(false);
                stopButton.setDisable(false);
                break;

            case "pauseButton":
                simulationThread.setPaused(pauseButton.isSelected());
                break;

            case "stopButton":
                stopThread();
                startButton.setDisable(false);
                pauseButton.setDisable(true);
                stopButton.setDisable(true);
                break;
        }

    }

    @FXML
    private void handleFileMenu(ActionEvent e) {
        String sourceId = ((MenuItem) e.getSource()).getId();
        switch (sourceId) {
            case "menuItemExit":
                Platform.exit();
                break;
        }
    }



}
