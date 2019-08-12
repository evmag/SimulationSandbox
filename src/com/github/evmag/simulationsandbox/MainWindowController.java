package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
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
    @FXML
    private Slider upsSlider;

    private SimulationThread simulationThread;

    @FXML
    public void initialize() {

        simulationCanvas.widthProperty().bind(canvasStackPane.widthProperty());
        simulationCanvas.heightProperty().bind(canvasStackPane.heightProperty());
        SimulationCanvas.getInstance().setGraphicsContext(simulationCanvas.getGraphicsContext2D());

        upsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (simulationThread != null) {
                    simulationThread.setUPS(newValue.intValue());
                }
            }
        });

    }

    public void initializeThread() {
        GameOfLifeMain gol = new GameOfLifeMain(50,50);
        simulationThread = new SimulationThread(gol);
        simulationThread.setUPS((int) upsSlider.getValue());
        new Thread(simulationThread).start();
    }


    public void stopThread() {
        if (simulationThread != null) {
            simulationThread.exit();
            simulationThread = null;
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
