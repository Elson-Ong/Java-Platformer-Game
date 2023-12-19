package Main;

import Input.KeyboardInputs;
import Input.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img, subImage;

    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        importImage();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        subImage = img.getSubimage(1 * 64,8 * 40,64,40);

        g.drawImage(subImage,(int)xDelta,(int)yDelta, 128, 80,null);
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

}
