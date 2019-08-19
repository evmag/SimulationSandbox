module SimulationSandbox {
    requires javafx.fxml;
    requires javafx.controls;

    exports com.github.evmag.simulationsandbox.simulations.gameoflife to  javafx.fxml;

    opens com.github.evmag.simulationsandbox;
    opens com.github.evmag.simulationsandbox.simulations.gameoflife;
}