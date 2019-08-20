package com.github.evmag.simulationsandbox.simulations.gameoflife;

import com.github.evmag.simulationsandbox.SimulationCanvas;
import com.github.evmag.simulationsandbox.simulations.Simulation;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Random;

public class GameOfLifeMain extends Simulation {
    private static GameOfLifeMain currInstance;
    private final int GAP_FROM_EDGES = 10;
    private final SimulationCanvas simCanvas = SimulationCanvas.getInstance();

    private int numGridCols;
    private int numGridRows;
    private int nextNumGridCols;
    private int nextNumGridRows;

    private double cellSize;
    private boolean[][] currentGen;
    private boolean[][] nextGen;

    private Color gridColor;
    private Color cellColor;
    private boolean wrapOnEdges = false;
    private boolean drawGridLines = true;

    public GameOfLifeMain(int numGridCols, int numGridRows) {
        this.nextNumGridCols = numGridCols;
        this.nextNumGridRows = numGridRows;
        gridColor = Color.BLACK;
        cellColor = Color.BLUE;
        currInstance = this;
    }

    @Override
    public void initialize() {
        numGridCols = nextNumGridCols;
        numGridRows = nextNumGridRows;

        // Set cell size to cover one canvas dimension
        double maxCellSizeInX = (simCanvas.getWidth() - 2 * GAP_FROM_EDGES) / numGridCols;
        double maxCellSizeInY = (simCanvas.getHeight() - 2 * GAP_FROM_EDGES) / numGridRows;
        cellSize = Math.min(maxCellSizeInX, maxCellSizeInY);

        // Init cell arrays
        currentGen = new boolean[numGridCols][numGridRows];
        nextGen = new boolean[numGridCols][numGridRows];
        initRandomCells();
//        initDebugCells();
    }

    private void drawGridLines() {
//        simCanvas.setStrokeColor(Color.BLACK);
        simCanvas.setStrokeColor(gridColor);
        // Draw vertical lines
        double y1 = GAP_FROM_EDGES;
        double y2 = y1 + numGridRows * cellSize;
        double x;
        for (int i = 0; i <= numGridCols; i++) {
            x = GAP_FROM_EDGES + i * cellSize;
            simCanvas.drawLine(x, y1, x, y2);
        }

        // Draw horizontal lines
        double x1 = GAP_FROM_EDGES;
        double x2 = x1 + numGridCols * cellSize;
        double y;
        for (int i = 0; i <= numGridRows; i++) {
            y = GAP_FROM_EDGES + i * cellSize;
            simCanvas.drawLine(x1, y, x2, y);
        }
    }

    private void drawCells() {
//        simCanvas.setFillColor(Color.BLUE);
        simCanvas.setFillColor(cellColor);
        double xStart = GAP_FROM_EDGES;
        double yStart = GAP_FROM_EDGES;
        double x, y;

        for (int i = 0; i < numGridCols; i++) {
            for (int j = 0; j < numGridRows; j++) {
                if (currentGen[i][j]) {
                    x = xStart + i * cellSize;
                    y = yStart + j * cellSize;
                    simCanvas.drawFilledRect(x, y, cellSize, cellSize);
                }
            }
        }
    }

    private void initRandomCells() {
        Random random = new Random();
        for (int i = 0; i < numGridCols; i++) {
            for (int j = 0; j < numGridRows; j++) {
                currentGen[i][j] = random.nextBoolean();
            }
        }
    }

    private void initDebugCells() {
        if ((numGridCols < 10) || (numGridRows < 10)) {
            System.out.println("Grid too small for debug configuration!");
            return;
        }
        // Static block
        currentGen[1][1] = true;
        currentGen[1][2] = true;
        currentGen[2][1] = true;
        currentGen[2][2] = true;

        // Oscillator
        currentGen[6][1] = true;
        currentGen[6][2] = true;
        currentGen[6][3] = true;

    }

    private void updateCurrentGeneration(){
//          Update next generation according to game of life rules:
//              - Any live cell with fewer than two live neighbours dies, as if by underpopulation.
//              - Any live cell with two or three live neighbours lives on to the next generation.
//              - Any live cell with more than three live neighbours dies, as if by overpopulation.
//              - Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
        for (int i = 0; i < numGridCols; i++) {
            for (int j = 0; j < numGridRows; j++) {
                int liveNeighbours = getLiveNeighbours(i, j);
                if (liveNeighbours < 2 || liveNeighbours > 3) {
                    nextGen[i][j] = false;
                } else {
                    if (currentGen[i][j]) {
                        nextGen[i][j] = true;
                    } else {
                        nextGen[i][j] = (liveNeighbours == 3);
                    }
                }
            }
        }

        // Swap cell arrays
        boolean[][] tmpGen = currentGen;
        currentGen = nextGen;
        nextGen = tmpGen;
    }

    private int getLiveNeighbours(int i, int j) {
        // TODO: Create unit test
        int liveNeighbours = 0;

        int iMinus1 = (i > 0) ? i - 1 : numGridCols - 1;
        int iPlus1 = (i < numGridCols - 1) ? i + 1 : 0;
        int jMinus1 = (j > 0) ? j - 1 : numGridCols - 1;
        int jPlus1 = (j < numGridCols - 1) ? j + 1 : 0;

        // Check 8 neighbours
        // Check (-1, -1), (-1, 0), (-1, +1)
        if (wrapOnEdges || (i > 0)) {
            if (wrapOnEdges || (j > 0)) {
                if (currentGen[iMinus1][jMinus1]) {
                    liveNeighbours++;
                }
            }
            if (currentGen[iMinus1][j]) {
                liveNeighbours++;
            }
            if (wrapOnEdges || (j < numGridRows - 1)) {
                if (currentGen[iMinus1][jPlus1]) {
                    liveNeighbours++;
                }
            }
        }

        // Check (0, -1), (0, +1)
        if (wrapOnEdges || (j > 0)) {
            if (currentGen[i][jMinus1]) {
                liveNeighbours++;
            }
        }
        if (wrapOnEdges || (j < numGridRows - 1)) {
            if (currentGen[i][jPlus1]) {
                liveNeighbours++;
            }
        }

        // Check (+1, -1), (+1, 0), (+1, +1)
        if (wrapOnEdges || (i < numGridCols - 1)) {
            if (wrapOnEdges || (j > 0)) {
                if (currentGen[iPlus1][jMinus1]) {
                    liveNeighbours++;
                }
            }
            if (currentGen[iPlus1][j]) {
                liveNeighbours++;
            }
            if (wrapOnEdges || (j < numGridRows - 1)) {
                if (currentGen[iPlus1][jPlus1]) {
                    liveNeighbours++;
                }
            }
        }

        return liveNeighbours;
    }

    public void setCellColor(Color color) {
        cellColor = color;
    }

    public void setGridColor(Color color) {
        gridColor = color;
    }

    public void setDrawGridLines(boolean drawGridLines) {
        this.drawGridLines = drawGridLines;
    }

    public void setWrapOnEdges(boolean wrapOnEdges) {
        this.wrapOnEdges = wrapOnEdges;
    }

    public void setGridDimensions(int numGridCols, int numGridRows) {
        this.nextNumGridCols = numGridCols;
        this.nextNumGridRows = numGridRows;
    }

    public static GameOfLifeMain getCurrInstance() {
        return currInstance;
    }

    @Override
    public void update() {
        updateCurrentGeneration();

    }

    @Override
    public void render() {
//        simCanvas.clear(Color.LIGHTGRAY);
        drawCells();
        if (drawGridLines) {
            drawGridLines();
        }

    }

    @Override
    public FXMLLoader getSettingsFXMLLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL loaderLocation = getClass().getResource("game_of_life_settings_panel.fxml");
        fxmlLoader.setLocation(loaderLocation);
        return fxmlLoader;
    }
}
