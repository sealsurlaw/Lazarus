package Lazarus;

import GameEngine.*;

public class Wall extends GameObject{
    
    public Wall(int x, int y) {
        super(Resources.getImage(Resources.Images.WALL), x, y);
    }

    @Override
    public void collision(GameObject other) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateState() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
