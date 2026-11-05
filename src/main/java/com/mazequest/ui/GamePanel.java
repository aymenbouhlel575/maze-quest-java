package com.mazequest.ui;

import com.mazequest.model.GameState;
import com.mazequest.model.Position;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

/** Swing view and keyboard controller; game rules live in GameState. */
public final class GamePanel extends JPanel {
    public static final int TILE=30, HEADER=92;
    private final GameState game;
    public GamePanel(GameState game) {
        this.game=game;
        setPreferredSize(new Dimension(GameState.COLS*TILE, GameState.ROWS*TILE+HEADER));
        setBackground(new Color(14,22,36));
        setFocusable(true);
        bind("UP",0,-1); bind("DOWN",0,1); bind("LEFT",-1,0); bind("RIGHT",1,0);
        bind("W",0,-1); bind("S",0,1); bind("A",-1,0); bind("D",1,0);
        action("R",game::reset);
        action("H",game::toggleHint);
        action("P",game::togglePause);
        new Timer(250,e->repaint()).start();
    }
    private void action(String key, Runnable action) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key),key);
        getActionMap().put(key,new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { action.run(); repaint(); }
        });
    }
    private void bind(String key,int dx,int dy) { action(key,()->game.move(dx,dy)); }

    @Override protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g=(Graphics2D)graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(new Font("SansSerif",Font.BOLD,24)); g.setColor(new Color(241,245,249));
            g.drawString("MAZE QUEST",18,33);
            g.setFont(new Font("SansSerif",Font.PLAIN,14)); g.setColor(new Color(170,187,211));
            g.drawString("Coins: "+game.collected()+"/"+GameState.COIN_COUNT+"     Moves: "+game.moves()+"     Time: "+game.elapsedSeconds()+"s",19,57);
            g.drawString("Arrows / WASD: move     H: A* hint     P: pause     R: new maze",19,79);
            for (int y=0;y<GameState.ROWS;y++) for (int x=0;x<GameState.COLS;x++) {
                int px=x*TILE,py=HEADER+y*TILE;
                boolean wall=!game.maze().isWalkable(new Position(x,y));
                g.setColor(wall ? new Color(43,65,88) : new Color(20,34,52));
                g.fillRect(px,py,TILE,TILE);
                if (wall) {g.setColor(new Color(61,88,113));g.drawRect(px,py,TILE-1,TILE-1);}
            }
            if (game.showHint()) {
                g.setColor(new Color(77,192,212,120));
                for (Position p:game.hintPath()) g.fillOval(p.x()*TILE+10,HEADER+p.y()*TILE+10,10,10);
            }
            int ex=GameState.EXIT.x()*TILE,ey=HEADER+GameState.EXIT.y()*TILE;
            g.setColor(game.coins().isEmpty()?new Color(74,222,128):new Color(110,132,146));
            g.fillRoundRect(ex+4,ey+4,TILE-8,TILE-8,7,7);
            g.setFont(new Font("SansSerif",Font.BOLD,16));g.setColor(new Color(8,26,30));g.drawString("E",ex+9,ey+21);
            for (Position p:game.coins()) {
                g.setColor(new Color(252,196,66));g.fillOval(p.x()*TILE+7,HEADER+p.y()*TILE+7,16,16);
                g.setColor(new Color(255,229,141));g.drawOval(p.x()*TILE+9,HEADER+p.y()*TILE+9,12,12);
            }
            Position player=game.player();g.setColor(new Color(113,174,255));
            g.fillOval(player.x()*TILE+4,HEADER+player.y()*TILE+4,TILE-8,TILE-8);
            g.setColor(Color.WHITE);g.fillOval(player.x()*TILE+17,HEADER+player.y()*TILE+10,5,5);
            if (game.paused() || game.won()) {
                g.setColor(new Color(7,14,24,220));
                g.fillRoundRect(120,HEADER+180,getWidth()-240,160,22,22);
                g.setColor(new Color(245,248,250));g.setFont(new Font("SansSerif",Font.BOLD,28));
                center(g,game.won()?"YOU WIN!":"PAUSED",HEADER+240);
                g.setFont(new Font("SansSerif",Font.PLAIN,16));
                center(g,game.won()?"Press R for another maze":"Press P to continue",HEADER+275);
            }
        } finally {g.dispose();}
    }
    private void center(Graphics2D g,String text,int y) {
        FontMetrics fm=g.getFontMetrics();g.drawString(text,(getWidth()-fm.stringWidth(text))/2,y);
    }
}
