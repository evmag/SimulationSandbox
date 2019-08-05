package com.github.evmag.simulationsandbox.simulations.gameoflife;

import com.github.evmag.simulationsandbox.SimulationCanvas;
import com.github.evmag.simulationsandbox.simulations.Simulation;
import javafx.scene.paint.Color;

import java.util.Random;

public class GameOfLifeMain extends Simulation {
    private final int GAP_FROM_EDGES = 10;
    private final SimulationCanvas simCanvas = SimulationCanvas.getInstance();

    private final int gridWidth;
    private final int gridHeight;

    private double cellSize;
    private boolean[][] currentGen;
    private boolean[][] nextGen;

    public GameOfLifeMain(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    @Override
    public void initialize() {
        // Set cell size to cover one canvas dimension
        double maxCellSizeInX = (simCanvas.getWidth() - 2 * GAP_FROM_EDGES) / gridWidth;
        double maxCellSizeInY = (simCanvas.getHeight() - 2 * GAP_FROM_EDGES) / gridHeight;
        cellSize = Math.min(maxCellSizeInX, maxCellSizeInY);

        // Init cell arrays
        currentGen = new boolean[gridWidth][gridHeight];
        nextGen = new boolean[gridWidth][gridHeight];
        initRandomCells();
//        initDebugCells();
    }

    private void drawGridLines() {
        simCanvas.setStrokeColor(Color.BLACK);
        // Draw vertical lines
        double y1 = GAP_FROM_EDGES;
        double y2 = y1 + gridHeight * cellSize;
        double x;
        for (int i = 0; i <= gridWidth; i++) {
            x = GAP_FROM_EDGES + i * cellSize;
            simCanvas.drawLine(x, y1, x, y2);
        }

        // Draw horizontal lines
        double x1 = GAP_FROM_EDGES;
        double x2 = x1 + gridWidth * cellSize;
        double y;
        for (int i = 0; i <= gridHeight; i++) {
            y = GAP_FROM_EDGES + i * cellSize;
            simCanvas.drawLine(x1, y, x2, y);
        }
    }

    private void drawCells() {
        simCanvas.setFillColor(Color.BLUE);
        double xStart = GAP_FROM_EDGES;
        double yStart = GAP_FROM_EDGES;
        double x, y;

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
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
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                currentGen[i][j] = random.nextBoolean();
            }
        }
    }

    private void initDebugCells() {
        if ((gridWidth < 10) || (gridHeight < 10)) {
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
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
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
        int liveNeighbours = 0;

        // Check 8 neighbours - no wrapping
        // Check (-1, -1), (-1, 0), (-1, +1)
        if (i > 0) {
            if (j > 0) {
                if (currentGen[i-1][j-1]) {
                    liveNeighbours++;
                }
            }
            if (currentGen[i-1][j]) {
                liveNeighbours++;
            }
            if (j < gridHeight - 1) {
                if (currentGen[i-1][j+1]) {
                    liveNeighbours++;
                }
            }
        }

        // Check (0, -1), (0, +1)
        if (j > 0) {
            if (currentGen[i][j-1]) {
                liveNeighbours++;
            }
        }
        if (j < gridHeight - 1) {
            if (currentGen[i][j+1]) {
                liveNeighbours++;
            }
        }

        // Check (+1, -1), (+1, 0), (+1, +1)
        if (i < gridWidth - 1) {
            if (j > 0) {
                if (currentGen[i+1][j-1]) {
                    liveNeighbours++;
                }
            }
            if (currentGen[i+1][j]) {
                liveNeighbours++;
            }
            if (j < gridHeight - 1) {
                if (currentGen[i+1][j+1]) {
                    liveNeighbours++;
                }
            }
        }

        return liveNeighbours;
    }

    @Override
    public void update() {
        updateCurrentGeneration();

    }

    @Override
    public void render() {
//        simCanvas.clear(Color.LIGHTGRAY);
        drawCells();
        drawGridLines();
    }
}
