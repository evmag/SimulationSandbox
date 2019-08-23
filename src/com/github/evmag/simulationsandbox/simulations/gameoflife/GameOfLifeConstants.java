package com.github.evmag.simulationsandbox.simulations.gameoflife;

import javafx.scene.paint.Color;

import java.util.regex.Pattern;

class GameOfLifeConstants {
    static final int DEFAULT_GRID_SIZE = 50;
    static final int MIN_GRID_SIZE = 5;
    static final int MAX_GRID_SIZE = 500;
    static final Pattern GRID_SIZE_INTEGER_PATTERN = Pattern.compile("\\d{1,6}");
    static final Color DEFAULT_CELL_COLOR = Color.BLUE;
    static final Color DEFAULT_BACKGROUND_COLOR = Color.LIGHTGRAY;
    static final Color DEFAULT_GRID_LINE_COLOR = Color.BLACK;
}
