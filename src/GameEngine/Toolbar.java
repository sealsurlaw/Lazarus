package GameEngine;

import java.awt.Graphics2D;

public abstract class Toolbar {
    protected int x;
    protected int y;
    
    public Toolbar(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void draw(Graphics2D g2);
}
