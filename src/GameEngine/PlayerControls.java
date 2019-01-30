package GameEngine;

import java.awt.event.KeyEvent;

public class PlayerControls {
    final public int LEFT;
    final public int RIGHT;
    final public int ESC;
    
    // Specifies controls for different instances of the same class (i.e. Tank)
    public PlayerControls(int left, int right) {
        LEFT = left;
        RIGHT = right;
        ESC = KeyEvent.VK_ESCAPE;
    }
}