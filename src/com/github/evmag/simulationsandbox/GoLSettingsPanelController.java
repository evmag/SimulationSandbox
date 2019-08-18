package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;

//TODO: Move to the correct package
public class GoLSettingsPanelController {
    private GameOfLifeMain gameOfLifeMainSimulation;

    @FXML
    private ColorPicker cellColor;
    @FXML
    private ColorPicker gridColor;

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
}
