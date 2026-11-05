# Publish to GitHub

## Before publishing

1. Read `README.md`, verify the screenshot and confirm you want the MIT license.
2. Run `bash scripts/test.sh` and `bash scripts/run.sh` on your machine.
3. Configure your Git identity if necessary (`git config user.name`, `git config user.email`).
4. Only publish code and project assets you intend to make public.

## Commands

Create an **empty repository** on GitHub named `maze-quest-java` (do not create a README in the GitHub UI), then:

```bash
cd maze-quest-github
git init
git add .
git commit -m "Initial commit: Maze Quest Java game"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/maze-quest-java.git
git push -u origin main
```

Replace `YOUR_USERNAME` with your actual GitHub username. If you already initialized Git or configured `origin`, review `git remote -v` and adapt commands instead of overwriting history.

## Suggested repository details

**Description:** Java 2D maze game featuring procedural generation, collectibles, Swing UI and A* pathfinding.

**Topics:** `java`, `java-swing`, `java2d`, `maze-game`, `game-development`, `astar`, `pathfinding`, `algorithms`, `object-oriented-programming`, `procedural-generation`

**Recommended:** add `assets/screenshots/gameplay.png` as a repository screenshot, keep the README current, and tag releases when tested.
