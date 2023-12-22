package src.utils;

import src.main.Game;

public class HelpMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData){

        if(!isSolid(x, y, levelData)) //top left
            if(!isSolid(x+width, y+ height, levelData)) //bottom right
                if(!isSolid(x+width, y, levelData)) //top right
                    if(!isSolid(x, y+height, levelData)) // bottom left
                        return true;

        return false;
    }

    private static boolean isSolid(float x, float y, int[][] levelData){

        if(x < 0 || x >= Game.GAME_WIDTH)
            return true;
        if(y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = levelData[(int)yIndex][(int)xIndex];

        if(value != 11 || value >= 48 || value < 0)
            return true;
        else
            return false;
    }
}
