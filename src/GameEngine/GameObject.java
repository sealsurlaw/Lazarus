package GameEngine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public abstract class GameObject {
    protected Image[] imgArray;     // All sprites of object
    protected Image currImg;        // Current image drawn
    protected int x, y;             // Position and rotation
    
    protected Rectangle collisionMask;
    protected AffineTransform at;
        
    public GameObject() {
        this(null,0,0);
    }
    
    public GameObject(Image[] imgArray, int x, int y) {
        this.imgArray = imgArray;
        this.x = x;
        this.y = y;
        
        currImg = imgArray[0];
        collisionMask = new Rectangle(
                x,y,imgArray[0].getWidth(null),imgArray[0].getHeight(null));
    }
    
    // X location getter/setter
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    
    // Y location getter/setter
    public int getY() {
        return y;
    }
    public void seyY(int y) {
        this.y = y;
    }
    
    // Sets specific imgArray index
    public void setIndex(int index) {
        if (index < imgArray.length)
            currImg = imgArray[index];
    }
    
    public Rectangle getCollisionMask() {
        return collisionMask;
    }
    
    // Handles collisons with other game objects
    public abstract void collision(GameObject other);
    
    public abstract void updateState();
    
    // Draws image
    public void draw(Graphics2D g2) {
        g2.drawImage(currImg, x, y, null);
    }

}