package GameEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class KeyboardObservable extends Observable{
    private KeyAdapter ka;
    private KeyEvent key;
    private Set<Integer> pressed = new HashSet<>();
    
    public KeyboardObservable() {
        ka = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                KeyboardObservable.this.pressed.add(key.getKeyCode());
                KeyboardObservable.this.setChanged();
                KeyboardObservable.this.notifyObservers(KeyboardObservable.this);
            }
            
            @Override
            public void keyReleased(KeyEvent key) {
                KeyboardObservable.this.pressed.remove(key.getKeyCode());
                KeyboardObservable.this.setChanged();
                KeyboardObservable.this.notifyObservers(KeyboardObservable.this);
            }
        };
    }
    
    public Set<Integer> getKey() {
        return pressed;
    }
    
    // Sent to the JFrame to listen for key strokes
    public KeyAdapter getKeyAdapter() {
        return ka;
    }
    
}