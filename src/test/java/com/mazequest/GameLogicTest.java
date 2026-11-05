package com.mazequest;

import com.mazequest.algorithm.AStarPathfinder;
import com.mazequest.algorithm.MazeGenerator;
import com.mazequest.model.GameState;
import com.mazequest.model.Maze;
import com.mazequest.model.Position;
import java.util.List;
import java.util.Random;

/** Dependency-free smoke and invariants tests, runnable with java -ea. */
public final class GameLogicTest {
    public static void main(String[] args) {
        int checks=0;
        for (int seed=0;seed<100;seed++) {
            Maze maze=MazeGenerator.generate(GameState.COLS,GameState.ROWS,new Random(seed));
            List<Position> path=AStarPathfinder.findPath(maze,GameState.START,GameState.EXIT);
            require(!path.isEmpty(),"Exit unreachable");checks++;
            require(path.get(0).equals(GameState.START) && path.get(path.size()-1).equals(GameState.EXIT),"Bad path ends");checks++;
            for (int i=1;i<path.size();i++) {
                Position a=path.get(i-1),b=path.get(i);
                require(maze.isWalkable(b) && Math.abs(a.x()-b.x())+Math.abs(a.y()-b.y())==1,"Invalid A* step");checks++;
            }
            GameState game=new GameState(new Random(seed));
            require(game.coins().size()==GameState.COIN_COUNT,"Wrong coin count");checks++;
            require(!game.move(-1,0),"Walked through border");checks++;
            require(game.moves()==0,"Illegal move counted");checks++;
            game.toggleHint();require(!game.hintPath().isEmpty(),"No coin hint");checks++;
            game.togglePause();require(!game.move(1,0),"Moved while paused");checks++;
            game.togglePause();game.reset();require(game.collected()==0 && game.moves()==0,"Reset failed");checks++;
        }
        boolean rejected=false;
        try {MazeGenerator.generate(10,11,new Random());} catch (IllegalArgumentException expected) {rejected=true;}
        require(rejected,"Even dimensions accepted");checks++;
        System.out.println("PASS: "+checks+" assertions across 100 seeded mazes");
    }
    private static void require(boolean ok,String message) {if (!ok) throw new AssertionError(message);}
}
