package com.mazequest;

import com.mazequest.model.GameState;
import com.mazequest.ui.GamePanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

/** Renders the actual Swing panel without opening a desktop window. */
public final class GenerateScreenshot {
    public static void main(String[] args) throws Exception {
        GamePanel panel=new GamePanel(new GameState(new Random(42)));
        panel.setSize(panel.getPreferredSize());
        BufferedImage image=new BufferedImage(panel.getWidth(),panel.getHeight(),BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D graphics=image.createGraphics();
        panel.paint(graphics);graphics.dispose();
        ImageIO.write(image,"png",new File(args[0]));
        System.exit(0); // Swing timer keeps the EDT alive in headless mode
    }
}
