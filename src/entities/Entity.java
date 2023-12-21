package src.entities;

/**
 * @author  Tze Yik Ong
 *
 * Abstract class for all the entity in the game i.e. player character, enemy
 */
public abstract class Entity {

    protected float x, y;
    protected int width, height;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
