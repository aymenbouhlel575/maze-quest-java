package com.mazequest.algorithm;

import com.mazequest.model.Maze;
import com.mazequest.model.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/** A* on a four-connected unit-cost grid, with admissible Manhattan heuristic. */
public final class AStarPathfinder {
    private AStarPathfinder() {}
    private record Node(Position pos, int cost, int score) {}

    public static List<Position> findPath(Maze maze, Position start, Position goal) {
        if (maze == null || !maze.isWalkable(start) || !maze.isWalkable(goal)) return List.of();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(Node::score));
        Map<Position, Integer> costs = new HashMap<>();
        Map<Position, Position> parent = new HashMap<>();
        costs.put(start, 0);
        frontier.add(new Node(start, 0, estimate(start, goal)));
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            if (current.cost != costs.getOrDefault(current.pos, Integer.MAX_VALUE)) continue;
            if (current.pos.equals(goal)) {
                List<Position> path = new ArrayList<>();
                for (Position p = goal; p != null; p = parent.get(p)) path.add(p);
                Collections.reverse(path);
                return path;
            }
            for (int[] d : dirs) {
                Position p = new Position(current.pos.x()+d[0], current.pos.y()+d[1]);
                if (!maze.isWalkable(p)) continue;
                int candidate = current.cost + 1;
                if (candidate < costs.getOrDefault(p, Integer.MAX_VALUE)) {
                    costs.put(p, candidate);
                    parent.put(p, current.pos);
                    frontier.add(new Node(p, candidate, candidate + estimate(p, goal)));
                }
            }
        }
        return List.of();
    }
    private static int estimate(Position a, Position b) {
        return Math.abs(a.x()-b.x())+Math.abs(a.y()-b.y());
    }
}
