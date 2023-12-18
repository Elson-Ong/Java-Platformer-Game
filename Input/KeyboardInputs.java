package Input;

import Main.GamePanel;

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
                gamePanel.changeYDelta(-5);
                break;

            // DOWN
            case KeyEvent.VK_A:
                gamePanel.changeXDelta(-5);
                break;

            // LEFT
            case KeyEvent.VK_S:
                gamePanel.changeYDelta(5);
                break;

            // RIGHT
            case KeyEvent.VK_D:
                gamePanel.changeXDelta(5);
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
