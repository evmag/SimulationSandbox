package com.github.evmag.simulationsandbox.simulations.gameoflife;

import java.util.regex.Pattern;

class GameOfLifeConstants {
    static final int DEFAULT_GRID_SIZE = 20;
    static final int MIN_GRID_SIZE = 5;
    static final int MAX_GRID_SIZE = 500;
    static final Pattern GRID_SIZE_INTEGER_PATTERN = Pattern.compile("\\d{1,6}");
}
