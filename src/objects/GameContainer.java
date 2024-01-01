package src.objects;

import src.main.Game;

import static src.utils.Constants.ObjectConstants.*;

/**
 * @author  Tze Yik Ong
 * Class for containers in the game - boxes and barrels
 */
public class GameContainer extends GameObject{

    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    private void createHitbox() {
        if(objType == BOX){
            initHitbox(25, 18);
            xDrawOffset= (int) (7 * Game.SCALE);
            yDrawOffset= (int) (12 * Game.SCALE);
        }
        else{
            initHitbox(23, 25);
            xDrawOffset= (int) (8 * Game.SCALE);
            yDrawOffset= (int) (5 * Game.SCALE);
        }
        hitbox.y += yDrawOffset + (int) (2 * Game.SCALE);
        hitbox.x += (float) (xDrawOffset / 2);
    }

    public void update(){
        if(doAnimation)
            updateAnimationTick();
    }
}
