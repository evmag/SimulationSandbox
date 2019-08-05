package com.github.evmag.simulationsandbox;

import com.github.evmag.simulationsandbox.simulations.gameoflife.GameOfLifeMain;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private SimulationThread simulationThread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
        primaryStage.setTitle("Simulation Sandbox");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();

        GameOfLifeMain gol = new GameOfLifeMain(50,50);
        simulationThread = new SimulationThread(gol);
        new Thread(simulationThread).start();

        // Testing of having canvas drawing in UI thread using AnimationTimer
//        AnimationTimer canvasDrawing = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//                gol.render();
//            }
//        };
//        canvasDrawing.start();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        simulationThread.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
