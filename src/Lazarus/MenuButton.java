package Lazarus;

import GameEngine.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;


public class MenuButton extends Toolbar{
    int x, y;
    Set<Integer> keyPressed = new HashSet<>();
    private int selected;
    private Map map;
    private boolean isTitle;
    private String winner;
    private Font font;
    
    public MenuButton(int xCor, int yCor, boolean isTitle, Map map, String winner){
        super(xCor, yCor);
        this.x = xCor;
        this.y = yCor;
        this.map = map;
        this.isTitle = isTitle;
        this.winner = winner;
        font = new Font(null,Font.BOLD,30);
        selected = 0;
    }
    
    public void draw(Graphics2D g2){
        if (!isTitle) {
            g2.setColor(new Color(160, 102, 49));
            g2.fillRect(x-120, y-100, 440, 270);
            g2.setColor(Color.black);
            g2.drawRect(x-120, y-100, 440, 270);
            g2.setColor(Color.yellow);
            g2.setFont(font);
            g2.drawString(winner + " Wins!", x, y-35);
        }
        if (selected == 0)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.red);
        g2.fillRect(x, y, 200, 50);
        if (selected == 1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.red);
        g2.fillRect(x, y+70, 200, 50);
        g2.setColor(Color.black);
        g2.drawRect(x, y, 200, 50);
        g2.drawRect(x, y+70, 200, 50);
        g2.setFont(font);
        if (isTitle)
            g2.drawString("Start", x+70, y+35);
        else
            g2.drawString("Retry", x+70, y+35);
        g2.drawString("Exit", x+75, y+105);
    }

}