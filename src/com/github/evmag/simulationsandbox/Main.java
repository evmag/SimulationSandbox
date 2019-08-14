package com.github.evmag.simulationsandbox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private MainWindowController mainWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_window.fxml"));
        Parent root = fxmlLoader.load();
        mainWindowController = (MainWindowController) fxmlLoader.getController();
        primaryStage.setTitle("Simulation Sandbox");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        mainWindowController.stopThread();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
