package GameEngine;

import Lazarus.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame {
    Map map;
    KeyboardObservable keyObs;
    
    public Game() {
        // Add title
        super("Lazarus");
        
        // Create a keyboard observer and passes it to the JFrame KeyListener
        keyObs = new KeyboardObservable();
        addKeyListener(keyObs.getKeyAdapter());
        
        // Contruct with initial map (probably title screen)
        map = new Level1(this);
        
        // Setup GUI (obviously)
        setupGUI();
        
        
        // Add movable objects to keyObs
        map.registerMovable(keyObs);
        
        // Timer to control redraw and gameloop events
        // 40ms ~ 25 FPS
        Timer gameLoopTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent act) {
                // Run game loop
                Game.this.gameLoop();
                // Redraw the screen
                Game.this.repaint();
            }
        });
        
        // Start the timer
        gameLoopTimer.start();
    }
    
    // Takes over the JFrame graphics context
    @Override
    public void paint(Graphics g) {
        Graphics2D gMap = (Graphics2D) g;
        
        // Draw map on JFrame
        map.draw(gMap);
    }

    // Sets up JFrame
    private void setupGUI() {
        // Make window visible
        setVisible(true);
        // Window height and width ((not in that order...))
        setSize(640, 512);
        // Places window in the center of the screen
        setLocationRelativeTo(null);
        // Set background, usually drawn over
        // Color set to the same color as title.png background
        setBackground(new Color(127, 122, 83));
        // User can't resize window
        setResizable(false);
        
        // When the user presses the X, close Java
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void gameLoop() {
        // Processes all actions of objects on map
        map.update();
        
        // Processes all collisions between objects
        map.collisions();
    }
    
    public void setMap(Map map){
        this.map = map;
    }
    
    public KeyboardObservable getKeyObservable() {
        return keyObs;
    }
}
