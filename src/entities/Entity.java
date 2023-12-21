package src.entities;

/**
 * @author  Tze Yik Ong
 *
 * Abstract class for all the entity in the game i.e. player character, enemy
 */
public abstract class Entity {

    protected float x, y;

    public Entity(float x, float y){
        this.x = x;
        this.y = y;
    }

}
