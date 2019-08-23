package com.github.evmag.simulationsandbox.simulations.gameoflife;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private ColorPicker backgroundColor;
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
        numOfRows.focusedProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue) {
                setGridDimensions();
            }
        });
        numOfCols.focusedProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue) {
                setGridDimensions();
            }
        });
        setDefaultSettingsValues();
    }

    @FXML
    private void setColor(ActionEvent e) {
        Control source = (Control) e.getSource();
        switch (source.getId()) {
            case "cellColor" :
                gameOfLifeMainSimulation.setCellColor(cellColor.getValue());
                break;
            case "backgroundColor" :
                gameOfLifeMainSimulation.setBackgroundColor(backgroundColor.getValue());
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
        int gridRows = GameOfLifeConstants.DEFAULT_GRID_SIZE;
        int gridCols = GameOfLifeConstants.DEFAULT_GRID_SIZE;

        if (GameOfLifeConstants.GRID_SIZE_INTEGER_PATTERN.matcher(numOfRows.getCharacters()).matches()) {
            gridRows = Integer.parseInt(numOfRows.getText());
            gridRows = Math.max(gridRows, GameOfLifeConstants.MIN_GRID_SIZE);
            gridRows = Math.min(gridRows, GameOfLifeConstants.MAX_GRID_SIZE);
        }
        numOfRows.setText(String.valueOf(gridRows));

        if (GameOfLifeConstants.GRID_SIZE_INTEGER_PATTERN.matcher(numOfCols.getCharacters()).matches()) {
            gridCols = Integer.parseInt(numOfCols.getText());
            gridCols = Math.max(gridCols, GameOfLifeConstants.MIN_GRID_SIZE);
            gridCols = Math.min(gridCols, GameOfLifeConstants.MAX_GRID_SIZE);
        }
        numOfCols.setText(String.valueOf(gridCols));

        gameOfLifeMainSimulation.setGridDimensions(gridRows, gridCols);
    }

    private void setDefaultSettingsValues() {
        numOfCols.setText(String.valueOf(GameOfLifeConstants.DEFAULT_GRID_SIZE));
        numOfRows.setText(String.valueOf(GameOfLifeConstants.DEFAULT_GRID_SIZE));
        cellColor.setValue(GameOfLifeConstants.DEFAULT_CELL_COLOR);
        backgroundColor.setValue(GameOfLifeConstants.DEFAULT_BACKGROUND_COLOR);
        gridColor.setValue(GameOfLifeConstants.DEFAULT_GRID_LINE_COLOR);
    }
}
