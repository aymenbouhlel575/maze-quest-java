package com.mazequest.model;

import com.mazequest.algorithm.AStarPathfinder;
import com.mazequest.algorithm.MazeGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** Game rules and state, independent of Swing and rendering. */
public final class GameState {
    public static final int COLS = 25, ROWS = 19, COIN_COUNT = 8;
    public static final Position START = new Position(1,1);
    public static final Position EXIT = new Position(COLS-2, ROWS-2);

    private final Random random;
    private Maze maze;
    private final Set<Position> coins = new HashSet<>();
    private Position player;
    private boolean paused, won, showHint;
    private int moves, collected;
    private long startedNanos, pausedNanos, pausedTotalNanos, winElapsedNanos;

    public GameState() { this(new Random()); }
    public GameState(Random random) {
        if (random == null) throw new IllegalArgumentException("Random generator is required");
        this.random = random;
        reset();
    }
    public void reset() {
        maze = MazeGenerator.generate(COLS, ROWS, random);
        coins.clear();
        List<Position> floors = new ArrayList<>();
        for (int y=1; y<ROWS-1; y++) for (int x=1; x<COLS-1; x++) {
            Position p = new Position(x,y);
            if (maze.isWalkable(p) && !p.equals(START) && !p.equals(EXIT)) floors.add(p);
        }
        Collections.shuffle(floors, random);
        for (int i=0; i<Math.min(COIN_COUNT, floors.size()); i++) coins.add(floors.get(i));
        player=START; paused=false; won=false; showHint=false; moves=0; collected=0;
        pausedTotalNanos=0; startedNanos=System.nanoTime(); winElapsedNanos=0;
    }
    public boolean move(int dx, int dy) {
        if (paused || won || Math.abs(dx)+Math.abs(dy)!=1) return false;
        Position dest = new Position(player.x()+dx, player.y()+dy);
        if (!maze.isWalkable(dest)) return false;
        player=dest; moves++;
        if (coins.remove(dest)) collected++;
        if (player.equals(EXIT) && coins.isEmpty()) {
            winElapsedNanos=elapsedNanos(); won=true;
        }
        return true;
    }
    public void togglePause() {
        if (won) return;
        if (paused) { pausedTotalNanos += System.nanoTime() - pausedNanos; paused = false; }
        else { pausedNanos = System.nanoTime(); paused = true; }
    }
    public void toggleHint() { showHint = !showHint; }
    public List<Position> hintPath() {
        if (!showHint) return List.of();
        if (coins.isEmpty()) return AStarPathfinder.findPath(maze,player,EXIT);
        List<Position> best=List.of();
        for (Position p : coins) {
            List<Position> candidate=AStarPathfinder.findPath(maze,player,p);
            if (!candidate.isEmpty() && (best.isEmpty() || candidate.size()<best.size())) best=candidate;
        }
        return best;
    }
    private long elapsedNanos() {
        long now=paused ? pausedNanos : System.nanoTime();
        return Math.max(0, now-startedNanos-pausedTotalNanos);
    }
    public long elapsedSeconds() { return (won ? winElapsedNanos : elapsedNanos()) / 1_000_000_000L; }
    public Maze maze() { return maze; }
    public Position player() { return player; }
    public Set<Position> coins() { return Collections.unmodifiableSet(coins); }
    public int moves() { return moves; }
    public int collected() { return collected; }
    public boolean paused() { return paused; }
    public boolean won() { return won; }
    public boolean showHint() { return showHint; }
}
