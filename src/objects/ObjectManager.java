package src.objects;

import src.entities.Player;
import src.gamestates.Playing;
import src.levels.Level;
import src.main.Game;
import src.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static src.utils.Constants.ObjectConstants.*;
import static src.utils.Constants.Projectiles.*;
import static src.utils.HelpMethods.*;

/**
 * @author  Tze Yik Ong
 * Class to manage the loading of the game objects
 */
public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage[] cannonImgs;
    private BufferedImage spikeImg, cannonBallImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public ObjectManager(Playing playing){
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player player){
        for(Spike s: spikes)
            if(s.getHitbox().intersects(player.getHitbox()))
                player.kill();
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox){
        for(Potion p: potions)
            if(p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
    }

    public void applyEffectToPlayer(Potion potion){
        if(potion.getObjType() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox){
        for(GameContainer gc: containers)
            if(gc.isActive() && !gc.doAnimation) {
                if(gc.getHitbox().intersects(attackBox)){
                    gc.setAnimation(true);
                    int type = 0;
                    if(gc.getObjType() == BARREL)
                        type = 1;

                    potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 4), type));
                    return;
                }
            }
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotionsList());
        containers = new ArrayList<>(newLevel.getContainersList());
        spikes = newLevel.getSpikesList();
        cannons = newLevel.getCannonsList();
        projectiles.clear();
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for(int j = 0; j < potionImgs.length; j ++)
            for(int i = 0; i < potionImgs[j].length; i ++)
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

        BufferedImage containerSprite = LoadSave.getSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for(int j = 0; j < containerImgs.length; j ++)
            for(int i = 0; i < containerImgs[j].length; i ++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);

        spikeImg = LoadSave.getSpriteAtlas(LoadSave.TRAP_ATLAS);

        cannonImgs = new BufferedImage[7];
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CANNON_ATLAS);

        for(int i = 0; i < cannonImgs.length; i ++)
            cannonImgs[i] = temp.getSubimage(40 * i,0,40,26);

        cannonBallImg = LoadSave.getSpriteAtlas(LoadSave.CANNON_BALL);
    }

    public void update(int[][] lvlData, Player player){
        for(Potion p: potions)
            if(p.isActive())
                p.update();

        for (GameContainer gc: containers)
            if(gc.isActive())
                gc.update();

        updateCannon(lvlData, player);
        updateProjectiles(lvlData, player);
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for(Projectile p : projectiles)
            if(p.isActive()) {
                p.updatePos();
                if(p.getHitbox().intersects(player.getHitbox())){
                    player.changeHealth(-25);
                    p.setActive(false);
                } else if (isProjectileHittingLevel(p, lvlData)) {
                    p.setActive(false);
                }
            }
    }

    private void updateCannon(int[][] lvlData, Player player) {
        for(Cannon c : cannons) {
            if(!c.doAnimation)
                if(c.getTileY() == player.getTileY())
                    if(isPlayerInRange(c, player))
                        if(isPlayerInfrontOfCannon(c, player))
                            if(canCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY()))
                                c.setAnimation(true);

            c.update();
            if(c.getAniIndex() == 4 && c.getAniTick() == 0)
                shootCannon(c);
        }
    }

    private void shootCannon(Cannon c) {
        projectiles.add(new Projectile((int)c.getHitbox().x, (int)c.getHitbox().y, c.getObjType()== CANNON_LEFT ? -1 : 1));
    }

    private boolean isPlayerInRange(Cannon c, Player player) {
        int distanceToPlayer = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
        return distanceToPlayer <= Game.TILES_SIZE * 5;
    }

    private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
        if(c.getObjType() == CANNON_LEFT) {
            if (c.getHitbox().x > player.getHitbox().x)
                return true;
        }
        else {
            if (c.getHitbox().x < player.getHitbox().x)
                return true;
        }
        return false;
    }

    public void draw(Graphics g, int xLvlOffset){
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for(Projectile p: projectiles)
            if(p.isActive())
                g.drawImage(cannonBallImg,
                        (int)(p.getHitbox().x - xLvlOffset),
                        (int)p.getHitbox().y ,
                        CANNON_BALL_WIDTH,
                        CANNON_BALL_HEIGHT,
                        null);
    }

    private void drawCannons(Graphics g, int xLvlOffset) {
        for(Cannon c : cannons){
            int x = (int) (c.getHitbox().x - xLvlOffset);
            int width = CANNON_WIDTH;

            if(c.getObjType() == CANNON_RIGHT){
                x += width;
                width *= -1;
            }
            g.drawImage(cannonImgs[c.getAniIndex()],
                    x,
                    (int) (c.getHitbox().y),
                    width,
                    CANNON_HEIGHT,
                    null);
        }
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for(Spike s: spikes)
            g.drawImage(spikeImg,
                    (int)(s.getHitbox().x - xLvlOffset),
                    (int)(s.getHitbox().y - s.getyDrawOffset()),
                    SPIKE_WIDTH,
                    SPIKE_HEIGHT,
                    null);
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc: containers)
            if(gc.isActive()) {
                int type = 0;
                if(gc.getObjType() == BARREL)
                    type = 1;

                g.drawImage(containerImgs[type][gc.getAniIndex()],
                        (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                        (int) (gc.getHitbox().y - gc.getyDrawOffset()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for(Potion p: potions)
            if(p.isActive()) {
                int type = 0;
                if(p.getObjType() == RED_POTION)
                    type = 1;

                g.drawImage(potionImgs[type][p.getAniIndex()],
                        (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                        (int) (p.getHitbox().y - p.getyDrawOffset()),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null);
            }
    }

    public void resetAllObject() {
        loadObjects(playing.getLevelManager().getCurrentLevel());

        for(Potion p: potions)
            p.reset();

        for (GameContainer gc: containers)
            gc.reset();

        for (Cannon c : cannons)
            c.reset();
    }
}
