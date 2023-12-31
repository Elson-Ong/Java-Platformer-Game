package src.main;

import src.inputs.KeyboardInputs;
import src.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

/**
 * @author  Tze Yik Ong
 * Class for the game panel(the game display itself)
 */
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        System.out.println(Game.GAME_WIDTH + " | " + Game.GAME_HEIGHT);
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }

}