package Main;

import Input.KeyboardInputs;
import Input.MouseInputs;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean playerMoving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImage();
        loadAnimation();

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
        }finally {
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private void loadAnimation() {
        animations = new BufferedImage[9][6];

        for(int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void setDirection(int direction){
        playerDirection = direction;
        playerMoving = true;
    }

    public void setMoving(boolean moving){
        playerMoving = moving;
    }

    private void updateAnimationTick() {
        animationTick ++;

        if(animationTick >= animationSpeed){
            animationTick = 0;
            animationIndex ++;

            if(animationIndex >= getSpriteAmount(playerAction))
                animationIndex = 0;

        }
    }

    private void setAnimation() {

        if(playerMoving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    private void updatePosition() {
        if(playerMoving){
            switch (playerDirection){
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void updateGame(){
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(animations[playerAction][animationIndex],(int)xDelta,(int)yDelta, 256, 160,null);
    }
}
