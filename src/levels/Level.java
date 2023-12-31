package src.levels;

import src.entities.Crabby;
import src.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static src.utils.HelpMethods.*;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Crabby> crabsList;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
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

    public int getMaxLvlOffsetX() {
        return maxLvlOffsetX;
    }

    public Point getSpawnPoint() {
        return playerSpawn;
    }
}
