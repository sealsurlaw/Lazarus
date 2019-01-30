package Lazarus;

import GameEngine.Toolbar;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class NextBlock extends Toolbar{
    private BufferedImage img;
    private int counter = 0;
    
    public NextBlock(int blockType) {
        super(20, 420);
        setImage(blockType);
    }
    
    public void setImage(int type) {
        switch(type) {
            case 0:
                img = Resources.getImage(Resources.Images.CARDBOX)[0];
                break;
            case 1:
                img = Resources.getImage(Resources.Images.WOODBOX)[0];
                break;
            case 2:
                img = Resources.getImage(Resources.Images.STONEBOX)[0];
                break;
            case 3:
                img = Resources.getImage(Resources.Images.METALBOX)[0];
                break;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        ++counter;
        if (counter < 10)
            g2.drawImage(img, x, y, null);
        if (counter == 20)
            counter = 0;
    }
    
}
