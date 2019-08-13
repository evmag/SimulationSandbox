package com.github.evmag.simulationsandbox.simulations;

import javafx.fxml.FXMLLoader;

public abstract class Simulation {
    public abstract void update();
    public abstract void render();
    public abstract void initialize();
    public abstract FXMLLoader getSettingsFXMLLoader();
}
