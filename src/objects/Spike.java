package src.objects;

import src.main.Game;

/**
 * @author  Tze Yik Ong
 * Class for the spikes
 */
public class Spike extends GameObject{

    public Spike(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int)(16 * Game.SCALE);
        hitbox.y += yDrawOffset;
    }
}
