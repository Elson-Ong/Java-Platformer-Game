package src.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author  Tze Yik Ong
 * Class to load all the images from the res folder
 */
public class LoadSave {
    // Player
    public static final String PLAYER_ATLAS = "player_sprites.png";
    // Game background
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    // Enemy
    public static final String CRABBY_SPRITE = "crabby_sprite.png";
    // Game object
    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";
    public static final String CANNON_ATLAS = "cannon_atlas.png";
    // Status bar
    public static final String STATUS_BAR = "health_power_bar.png";
    // Menu
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    // Pause
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    // Level completed
    public static final String COMPLETED_IMG = "completed_sprite.png";

    public static BufferedImage getSpriteAtlas(String fileName){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

        try {
             img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] getAllLevels(){
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;
        System.out.println("File: ");

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for(int i = 0; i < filesSorted.length; i ++)
            for(int j = 0; j < files.length; j ++)
                if (files[j].getName().equals((i + 1) + ".png"))
                    filesSorted[i] = files[j];

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for(int i = 0; i < imgs.length; i ++){
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imgs;
    }


}
