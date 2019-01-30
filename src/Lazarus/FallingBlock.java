package Lazarus;

import GameEngine.*;
import java.awt.Rectangle;

public class FallingBlock extends GameObject{
    private Map map;
    private int blockType;
    private int posI;
    private int posJ = -1;
    
    public FallingBlock(int block, int x, Map map) {
        super(Resources.getImage(Resources.Images.CARDBOX), x, -40);
        this.map = map;
        posI = x/40;
        blockType = block;
        switch(blockType) {
            case 0:
                imgArray = Resources.getImage(Resources.Images.CARDBOX);
                break;
            case 1:
                imgArray = Resources.getImage(Resources.Images.WOODBOX);
                break;
            case 2:
                imgArray = Resources.getImage(Resources.Images.STONEBOX);
                break;
            case 3:
                imgArray = Resources.getImage(Resources.Images.METALBOX);
                break;
        }
        setIndex(0);
        
        collisionMask = new Rectangle(x, y, 40, 40);
    }

    @Override

    public void collision(GameObject other) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateState() {
        y += 8;
        if (y%40 == 0)
            ++posJ;
        if (!map.isClear(posI, posJ+1)) {
            if (blockType > map.getBlockType(posI, posJ+1)) {
                map.removeBox(posI, posJ+1);
                Resources.playSound(Resources.Sounds.CRUSH);
            }
            else {
                Resources.playSound(Resources.Sounds.WALL);
                GameObject sb = new StoppedBlock(blockType, posI, posJ);
                if (posJ >= 0)
                    map.addBox(blockType, sb, posI, posJ);
                map.removeObject(this);
            }
        }
        
        collisionMask = new Rectangle(x, y, 40, 40);
    }
    
}
