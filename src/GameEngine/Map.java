package GameEngine;

import Lazarus.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Map {

    protected ArrayList<GameObject> objects;
    protected GameObject[][] obj;
    protected ArrayList<GameObject> buttons;
    protected ArrayList<Toolbar> toolbar;
    protected BufferedImage background;
    protected BufferedImage statusImg = null;
    protected int tileHeight;
    protected int tileWidth;
    protected Player lazarus;
    protected FallingBlock falling = null;
    
    protected Game game;
    
    public enum Status {
        WIN, LOSE, PLAYING
    }

    protected Map(Game game) {
        this.game = game;
        Resources.init();
    }

    public void registerMovable(KeyboardObservable keyObs) {
        keyObs.addObserver(lazarus);
    }

    public void draw(Graphics2D gFrame) {
        // Entire map
        BufferedImage iMap = new BufferedImage(
                640, 480, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gMap = (Graphics2D) iMap.getGraphics();
        
        // Draw background
        gMap.drawImage(background, 0, 0, null);

        // Draw objects
        for (int i = 0; i < objects.size(); ++i) {
            objects.get(i).draw(gMap);
        }
        for (int i = 0; i < obj.length; ++i) {
            for (int j = 0; j < obj[0].length; ++j) {
                if (obj[i][j] != null)
                    obj[i][j].draw(gMap);
            }
        }
        
        // Draw buttons
        for (int i = 0; i < buttons.size(); ++i) {
            buttons.get(i).draw(gMap);
        }
        
        // Draw Lazarus on the map
        lazarus.draw(gMap);
        
        // Draw toolbar objects
        for (int i = 0; i < toolbar.size(); ++i) {
            toolbar.get(i).draw(gMap);
        } 
        
        // Draw status
        if (statusImg != null)
            gMap.drawImage(statusImg, 0, 0, null);

        // Draw to JFrame
        gFrame.drawImage(iMap, 0, 32, null);
    }

    public void update() {
        lazarus.updateState();
        for (int i = 0; i < objects.size(); ++i) {
            objects.get(i).updateState();
        }
    }

    public void collisions() {
        if (falling != null)
            lazarus.collision(falling);

        // Check Lazarus against all buttons
        for (int i = 0; i < buttons.size(); ++i) {
            lazarus.collision(buttons.get(i));
        }
    }

    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    public void removeObject(GameObject obj) {
        objects.remove(obj);
    }
    
    public void addButton(GameObject obj) {
        buttons.add(obj);
    }
    
    public void addToolbar(Toolbar tb) {
        toolbar.add(tb);
    }
    
    public void gameStatus(Status status) {
        if (status == Status.LOSE)
            statusImg = Resources.getImage(Resources.Images.LOSE)[0];
        if (status == Status.WIN) {
            if (this.getClass() != Level4.class)
                this.nextMap();
            statusImg = Resources.getImage(Resources.Images.WIN)[0];
        }
    }
    
    public abstract void decodeMap();
    
    public abstract void nextMap();
    
    public abstract void addBox(int type, GameObject go, int i, int j);
    
    public abstract void removeBox(int i, int j);
    
    public abstract boolean isClear(int i, int j);
    
    public abstract int getBlockType(int i, int j);
    
    public abstract void stopBlocks();
    
}
