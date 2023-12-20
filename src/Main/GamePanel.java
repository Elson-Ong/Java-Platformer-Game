package src.Main;

import src.Input.KeyboardInputs;
import src.Input.MouseInputs;
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
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean playerMoving = false;

    /**
     * Constructor
     * Initialize the various mouse and keyboard input listeners and at the same time import and load the animations
     */
    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImage();
        loadAnimation();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Import the image file
     */
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

    /**
     * Load the animation from the subimages from the image file
     */
    private void loadAnimation() {
        animations = new BufferedImage[9][6];

        for(int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
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
     * Set the direction in which the player is moving in
     * @param direction the direction the player is moving
     */
    public void setDirection(int direction){
        playerDirection = direction;
        playerMoving = true;
    }

    /**
     * Set whether the player is moving or not
     * @param moving whether the player is moving
     */
    public void setMoving(boolean moving){
        playerMoving = moving;
    }

    /**
     * Update which image to use in the image array for the various animations
     */
    private void updateAnimationTick() {
        animationTick ++;

        if(animationTick >= animationSpeed){
            animationTick = 0;
            animationIndex ++;

            if(animationIndex > getSpriteAmount(playerAction) - 1)
                animationIndex = 0;

        }
    }

    /**
     * Set the animation type to use for the player character
     */
    private void setAnimation() {

        if(playerMoving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }

    /**
     * Update the player position
     */
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

    /**
     * Logic update loop for the game
     */
    public void updateGame(){
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    /**
     * Frame update loop for the fame
     * @param g the <code>Graphics</code> object to paint
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(animations[playerAction][animationIndex],(int)xDelta,(int)yDelta, 256, 160,null);
    }
}
