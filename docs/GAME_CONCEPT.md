# Game concept — Maze Quest

## Elevator pitch

**Maze Quest** is a single-player 2D puzzle adventure on desktop. The player navigates a new labyrinth every round, collects 8 coins, and reaches the exit. A built-in A* hint provides a way to discover algorithmic pathfinding in a playful context.

## Audience and goals

- **Audience:** players who enjoy small casual puzzles, and Java/algorithms learners.
- **Primary experience:** exploration, memory, spatial reasoning and route optimization.
- **Technical goal:** demonstrate Java OOP, procedural generation, event-driven UI and graph search in a compact, understandable codebase.

## Core gameplay loop

1. Generate a fully connected maze.
2. Place the player at (1,1), place the exit near the opposite corner, and scatter eight coins.
3. Explore with arrow keys or WASD. Walls block movement.
4. Collect all eight coins; the exit changes from gray to green.
5. Reach the exit for a victory overlay, then restart with R.

## Mechanics and rules (V1)

| Mechanic | Behavior |
|---|---|
| Maze | 25 columns × 19 rows, one-tile corridors, randomized DFS |
| Player | One orthogonal tile per input; no diagonal movement |
| Coins | Eight distinct walkable tiles, not start or exit |
| Exit | Requires all coins; passable before then, but no victory |
| Hint | A* route to coin with shortest path distance, then to exit |
| Timer | Monotonic elapsed game time; stops on pause and win |
| Moves | Counts valid player moves only |
| Pause | Freezes movement and timer |
| Restart | Creates a fresh level and resets counters |

## User stories

- As a player, I want intuitive keyboard controls to navigate easily.
- As a player, I want a random maze so each round is different.
- As a player, I want to see progress (coins, time, moves).
- As a learner, I want to toggle A* hints to understand navigation.

## Out of scope in V1

No enemies, online accounts, persistent leaderboards, save files, sound effects, mobile support, multiple difficulty levels or achievements. These are possible future enhancements, not present functionality.
