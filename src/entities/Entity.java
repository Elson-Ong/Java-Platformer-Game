package src.entities;

import src.main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author  Tze Yik Ong
 * Abstract class for all the entity in the game i.e. player character, enemy
 */
public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex, state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;


    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }


    protected void drawHitbox(Graphics g, int xLvlOffset){
        //For debugging hitbox
        g.setColor(Color.PINK);
        g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int)(width * Game.SCALE), (int)(height * Game.SCALE));
    }

//    protected void updateHitbox(){
//        hitbox.x = (int) x;
//        hitbox.y = (int) y;
//    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public int getState() {
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }

}
