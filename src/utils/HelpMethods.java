package src.utils;

import src.entities.Crabby;
import src.main.Game;
import src.objects.GameContainer;
import src.objects.Potion;
import src.objects.Spike;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static src.utils.Constants.EnemyConstants.CRABBY;
import static src.utils.Constants.ObjectConstants.*;

/**
 * @author  Tze Yik Ong
 * Class for the static helper methods for the whole program
 */
public class HelpMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!isSolid(x, y, lvlData))
            if (!isSolid(x + width, y + height, lvlData))
                if (!isSolid(x + width, y, lvlData))
                    if (!isSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid((int)xIndex, (int)yIndex, lvlData);
    }

    private static boolean isTileSolid(int xTile, int yTile, int[][] lvlData){
        int value = lvlData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;

    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottom left and bottom right
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;

    }

    public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData){
        if(xSpeed > 0)
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);

    }

    private static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData){
        for(int i = 0;  i < xEnd - xStart; i ++) {
            if (isTileSolid(xStart + i, y, lvlData))
                return false;
            if(!isTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }
    public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if(firstXTile > secondXTile)
            return isAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        else
            return isAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
    }

    public static int[][] getLevelData(BufferedImage img){
        int[][] levelData = new int[img.getHeight()][img.getWidth()];

        for(int j = 0; j < img.getHeight(); j ++){
            for(int i = 0 ; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();

                if(value >= 48)
                    value = 0;

                levelData[j][i] = value;
            }
        }
        return levelData;
    }

    public static ArrayList<Crabby> getCrabs(BufferedImage img){
        ArrayList<Crabby> list = new ArrayList<>();

        for(int j = 0; j < img.getHeight(); j ++){
            for(int i = 0 ; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();

                if(value == CRABBY)
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        }
        return list;
    }

    public static ArrayList<Potion> getPotions(BufferedImage img){
        ArrayList<Potion> list = new ArrayList<>();

        for(int j = 0; j < img.getHeight(); j ++){
            for(int i = 0 ; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();

                if(value == RED_POTION || value == BLUE_POTION)
                    list.add(new Potion(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }
        }
        return list;
    }

    public static ArrayList<GameContainer> getContainers(BufferedImage img){
        ArrayList<GameContainer> list = new ArrayList<>();

        for(int j = 0; j < img.getHeight(); j ++){
            for(int i = 0 ; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();

                if(value == BOX || value == BARREL)
                    list.add(new GameContainer(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }
        }
        return list;
    }

    public static ArrayList<Spike> getSpikes(BufferedImage img){
        ArrayList<Spike> list = new ArrayList<>();

        for(int j = 0; j < img.getHeight(); j ++){
            for(int i = 0 ; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();

                if(value == SPIKE)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE));
            }
        }
        return list;
    }

    public static Point getPlayerSpawn(BufferedImage img){
        for(int j = 0; j < img.getHeight(); j ++){
            for(int i = 0 ; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();

                if(value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        }

        return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }

}
