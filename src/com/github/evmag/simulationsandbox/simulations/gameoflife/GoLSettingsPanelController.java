package com.github.evmag.simulationsandbox.simulations.gameoflife;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

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
    private TextField numOfRows;
    @FXML
    private TextField numOfCols;

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
                gameOfLifeMainSimulation.setWrapOnEdges(wrapOnEdges.isSelected());
                break;
        }
    }

    @FXML
    private void setGridDimensions() {
        int defaultSize = 20; // TODO: make this a constant

        int gridRows;
        int gridCols;

        try {
            gridRows = Integer.parseInt(numOfRows.getText());
            gridCols = Integer.parseInt(numOfCols.getText());
        } catch (NumberFormatException e) {
            gridRows = defaultSize;
            gridCols = defaultSize;
        }

        gameOfLifeMainSimulation.setGridDimensions(gridRows, gridCols);

    }
}
