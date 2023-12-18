package Main;


/**
 * @author  Tze Yik Ong
 *
 * Class for  the game
 */
public class Game {

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
    }
}
