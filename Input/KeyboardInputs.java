package Input;

import Main.GamePanel;
import static utils.Constants.Directions.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            // UP
            case KeyEvent.VK_W:
                gamePanel.setDirection(UP);
                break;
            // DOWN
            case KeyEvent.VK_A:
                gamePanel.setDirection(LEFT);
                break;
            // LEFT
            case KeyEvent.VK_S:
                gamePanel.setDirection(DOWN);
                break;
            // RIGHT
            case KeyEvent.VK_D:
                gamePanel.setDirection(RIGHT);
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.setMoving(false);
                break;

        }
    }
}
