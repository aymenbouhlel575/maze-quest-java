package com.mazequest.model;

/** Maze grid: true indicates an impassable wall. */
public final class Maze {
    private final boolean[][] walls;

    public Maze(boolean[][] source) {
        if (source == null || source.length == 0 || source[0].length == 0) throw new IllegalArgumentException("Empty maze");
        int width = source[0].length;
        walls = new boolean[source.length][width];
        for (int y = 0; y < source.length; y++) {
            if (source[y] == null || source[y].length != width) throw new IllegalArgumentException("Non-rectangular maze");
            System.arraycopy(source[y], 0, walls[y], 0, width);
        }
    }

    public int width() { return walls[0].length; }
    public int height() { return walls.length; }
    public boolean isWalkable(Position p) {
        return p.x() >= 0 && p.y() >= 0 && p.x() < width() && p.y() < height() && !walls[p.y()][p.x()];
    }
}
