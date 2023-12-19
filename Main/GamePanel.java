package Main;

import Input.KeyboardInputs;
import Input.MouseInputs;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private float xDir = 0.3f, yDir = 0.3f;
    private Color color = new Color(150,20,90);
    private Random random;

    public GamePanel(){
        random = new Random();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        updateRectangle();
        g.setColor(color);
        g.fillRect((int)xDelta,(int)yDelta,200,50);
    }

    public void changeXDelta(int value){
        xDelta += value;
        repaint();
    }

    public void changeYDelta(int value){
        yDelta += value;
    }

    public void setRectPosition(int x, int y){
        xDelta = x;
        yDelta = y;
    }

    private void updateRectangle() {
        xDelta += xDir;
        if(xDelta < 0 || xDelta > 400) {
            xDir *= -1;
            color = getRandomColor();
        }
        yDelta += yDir;
        if(yDelta < 0 || yDelta > 400) {
            yDir *= -1;
            color = getRandomColor();
        }
    }

    private Color getRandomColor() {
        int r = random.nextInt(255);
        int g = random.nextInt(255);;
        int b = random.nextInt(255);;

        return new Color(r,g,b);
    }
}
