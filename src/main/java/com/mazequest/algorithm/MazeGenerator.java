package com.mazequest.algorithm;

import com.mazequest.model.Maze;
import com.mazequest.model.Position;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

/** Randomized depth-first search creates a connected, perfect maze. */
public final class MazeGenerator {
    private MazeGenerator() {}

    public static Maze generate(int width, int height, Random random) {
        if (width < 5 || height < 5 || width % 2 == 0 || height % 2 == 0 || random == null)
            throw new IllegalArgumentException("Width and height must be odd and >= 5, with a random generator");
        boolean[][] walls = new boolean[height][width];
        for (boolean[] row : walls) java.util.Arrays.fill(row, true);
        walls[1][1] = false;
        Deque<Position> stack = new ArrayDeque<>();
        stack.push(new Position(1, 1));
        int[][] steps = {{0,-2},{0,2},{-2,0},{2,0}};
        while (!stack.isEmpty()) {
            Position p = stack.peek();
            List<Position> choices = new ArrayList<>();
            for (int[] d : steps) {
                int x = p.x() + d[0], y = p.y() + d[1];
                if (x > 0 && x < width - 1 && y > 0 && y < height - 1 && walls[y][x]) choices.add(new Position(x, y));
            }
            if (choices.isEmpty()) stack.pop();
            else {
                Position n = choices.get(random.nextInt(choices.size()));
                walls[(p.y()+n.y())/2][(p.x()+n.x())/2] = false;
                walls[n.y()][n.x()] = false;
                stack.push(n);
            }
        }
        return new Maze(walls);
    }
}
