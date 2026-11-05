package com.mazequest.app;

import com.mazequest.model.GameState;
import com.mazequest.ui.GamePanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/** Desktop entry point. */
public final class MazeQuestApp {
    private MazeQuestApp() {}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame=new JFrame("Maze Quest - Java Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GamePanel(new GameState()));
            frame.pack();frame.setResizable(false);frame.setLocationRelativeTo(null);frame.setVisible(true);
        });
    }
}
