package src.main;


import src.entities.Player;

import java.awt.*;

/**
 * @author  Tze Yik Ong
 *
 * Class for  the game
 */
public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private  Thread gameThread;
    private final int FPS = 120;
    private final int UPS = 200;
    private Player player;

    /**
     * Constructor
     * Initialize  the game panel and window and starts the game loop
     */
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }


    /**
     * Helper class to initialize all the classes
     */
    private void initClasses() {
        player = new Player(200,200);
    }

    /**
     * Method to start the game loop
     */
    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Method to update the game state
     */
    public void update(){
        player.update();
    }

    /**
     * Method to render the game
     */
    public void render(Graphics g){
        player.render(g);
    }

    /**
     * The game loop
     * Updates the logic and the frames of the game based on the set interval determined by the FPS and UPS
     */
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1){
                update();
                updates ++;
                deltaU --;
            }

            if(deltaF >= 1){
                gamePanel.repaint();
                frames ++;
                deltaF --;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS = " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    /**
     * Method to deal with game window losing focus
     */
    public void windowFocusLost(){
        player.resetDirBoolean();
    }

    public Player getPlayer(){
        return player;
    }

}
