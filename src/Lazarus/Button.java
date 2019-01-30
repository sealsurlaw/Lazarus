package Lazarus;

import GameEngine.*;

public class Button extends GameObject{
    
    public Button(int x, int y) {
        super(Resources.getImage(Resources.Images.BUTTON), x, y);
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
