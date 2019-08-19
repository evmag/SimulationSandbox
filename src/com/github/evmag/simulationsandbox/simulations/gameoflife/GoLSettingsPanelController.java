package com.github.evmag.simulationsandbox.simulations.gameoflife;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;

public class GoLSettingsPanelController {
    private GameOfLifeMain gameOfLifeMainSimulation;

    @FXML
    private ColorPicker cellColor;
    @FXML
    private ColorPicker gridColor;
    @FXML
    private CheckBox drawGridLines;
    @FXML
    private CheckBox wrapOnEdges;

    @FXML
    private void initialize() {
        gameOfLifeMainSimulation = GameOfLifeMain.getCurrInstance();
    }

    @FXML
    private void setColor(ActionEvent e) {
        Control source = (Control) e.getSource();
        switch (source.getId()) {
            case "cellColor" :
                gameOfLifeMainSimulation.setCellColor(cellColor.getValue());
                break;
            case "gridColor" :
                gameOfLifeMainSimulation.setGridColor(gridColor.getValue());
                break;
        }
    }

    @FXML
    private void setBooleanSettings(ActionEvent e) {
        CheckBox source = (CheckBox) e.getSource();
        switch (source.getId()) {
            case "drawGridLines":
                gameOfLifeMainSimulation.setDrawGridLines(drawGridLines.isSelected());
                break;
            case "wrapOnEdges":
                break;
        }
    }
}
