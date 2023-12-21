package src.main;

import src.inputs.KeyboardInputs;
import src.inputs.MouseInputs;
import static src.utils.Constants.PlayerConstants.*;
import static src.utils.Constants.Directions.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    /**
     * Constructor
     * Initialize the various mouse and keyboard input listeners and at the same time import and load the animations
     */
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Set the size of the game panel
     */
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    /**
     * Logic update loop for the game
     */
    public void updateGame(){

    }

    /**
     * Frame update loop for the fame
     * @param g the <code>Graphics</code> object to paint
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        game.render(g);
    }

    public Game getGame(){
        return game;
    }
}
