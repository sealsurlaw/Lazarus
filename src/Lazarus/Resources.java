package Lazarus;

import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class Resources {

    private static HashMap<Images, BufferedImage[]> imgHash;

    public static enum Images {
        BACKGROUND, BUTTON, CARDBOX, AFRAID, JUMP_LEFT, JUMP_RIGHT,
        LEFT, LOSE, RIGHT, SQUISHED, STAND, MESH, METALBOX, ROCK,
        STONEBOX, TITLE, WALL, WIN, WOODBOX
    }

    public static enum Sounds {
        BUTTON, CRUSH, MOVE, MUSIC, SQUISHED, WALL
    }

    //Initializes hashmap with Image and Sound objects
    public static void init() {
        imgHash = new HashMap<>();
        ////IMAGES////
        //BACKGROUND
        imgHash.put(Images.BACKGROUND, loadImage("Background"));
        //BUTTON
        imgHash.put(Images.BUTTON, loadImage("Button"));
        //CARDBOX
        imgHash.put(Images.CARDBOX, loadImage("CardBox"));
        //AFRAID
        imgHash.put(Images.AFRAID, loadImage("Lazarus_afraid", 10));
        //JUMP_LEFT
        imgHash.put(Images.JUMP_LEFT, loadImage("Lazarus_jump_left", 7));
        //JUMP_RIGHT
        imgHash.put(Images.JUMP_RIGHT, loadImage("Lazarus_jump_right", 7));
        //LEFT
        imgHash.put(Images.LEFT, loadImage("Lazarus_left", 7));
        //LOSE
        imgHash.put(Images.LOSE, loadImage("Lose"));
        //RIGHT
        imgHash.put(Images.RIGHT, loadImage("Lazarus_right", 7));
        //SQUISHED
        imgHash.put(Images.SQUISHED, loadImage("Lazarus_squished", 10));
        //STAND
        imgHash.put(Images.STAND, loadImage("Lazarus_stand"));
        //MESH
        imgHash.put(Images.MESH, loadImage("Mesh"));
        //METALBOX
        imgHash.put(Images.METALBOX, loadImage("MetalBox"));
        //ROCK
        imgHash.put(Images.ROCK, loadImage("Rock"));
        //STONEBOX
        imgHash.put(Images.STONEBOX, loadImage("StoneBox"));
        //TITLE
        imgHash.put(Images.TITLE, loadImage("Title"));
        //WALL
        imgHash.put(Images.WALL, loadImage("Wall"));
        //WIN
        imgHash.put(Images.WIN, loadImage("Win"));
        //WOODBOX
        imgHash.put(Images.WOODBOX, loadImage("WoodBox"));
    }

    // Gets Image objects
    public static BufferedImage[] getImage(Images im) {
        return imgHash.get(im);
    }
    
    public static void playSound(Sounds snd) {
        String path = "";
        if (snd == Sounds.BUTTON) path = "Button.wav";
        if (snd == Sounds.CRUSH) path = "Crush.wav";
        if (snd == Sounds.MOVE) path = "Move.wav";
        if (snd == Sounds.MUSIC) path = "Music.mid";
        if (snd == Sounds.SQUISHED) path = "Squished.wav";
        if (snd == Sounds.WALL) path = "Wall.wav";

        try {
            AudioInputStream audioIn =
                    AudioSystem.getAudioInputStream(new File("res/sound/" + path));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (snd == Sounds.MUSIC) {
                clip.setLoopPoints(0, -1);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.WARNING, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.WARNING, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    private static BufferedImage[] loadImage(String path, int numImg) {
        BufferedImage[] img = new BufferedImage[numImg];
        try {
            if (numImg == 1) {
                path = "res/img/" + path + "/" + path + ".png";
                img[0] = ImageIO.read(new File(path));
            } else {
                for (int i = 0; i < numImg; ++i) {
                    String pathNew = "res/img/" + path + "/" + path + "-" + i + ".png";
                    img[i] = ImageIO.read(new File(pathNew));
                }
            }
        } catch (IOException e) {
            System.err.println("\"" + path + "\" does not exist.");
        }
        return img;
    }

    private static BufferedImage[] loadImage(String path) {
        return loadImage(path, 1);
    }

}
