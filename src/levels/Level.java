package src.levels;

import src.entities.Crabby;
import src.main.Game;
import src.objects.GameContainer;
import src.objects.Potion;
import src.objects.Spike;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static src.utils.HelpMethods.*;

/**
 * @author  Tze Yik Ong
 * Class to keep track of the individual level data
 */
public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Crabby> crabsList;
    private ArrayList<Potion> potionsList;
    private ArrayList<GameContainer> containersList;
    private ArrayList<Spike> spikesList;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void calcPlayerSpawn() {
        playerSpawn = getPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createSpikes() {
        spikesList =  getSpikes(img);
    }

    private void createContainers() {
        containersList = getContainers(img);
    }

    private void createPotions() {
        potionsList = getPotions(img);
    }

    private void createEnemies() {
        crabsList = getCrabs(img);
    }

    private void createLevelData() {
        lvlData = getLevelData(img);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }

    public int[][] getLvlData(){
        return lvlData;
    }

    public ArrayList<Crabby> getCrabsList() {
        return crabsList;
    }

    public ArrayList<Potion> getPotionsList() {
        return potionsList;
    }

    public ArrayList<GameContainer> getContainersList() {
        return containersList;
    }

    public ArrayList<Spike> getSpikesList() {
        return spikesList;
    }

    public int getMaxLvlOffsetX() {
        return maxLvlOffsetX;
    }

    public Point getSpawnPoint() {
        return playerSpawn;
    }

}
