package Lazarus;

import GameEngine.*;

public class StoppedBlock extends GameObject{
    private int posI;
    private int posJ;
    private int blockType;
    
    public StoppedBlock(int block, int posI, int posJ) {
        super(Resources.getImage(Resources.Images.CARDBOX), posI*40, posJ*40);
        this.posI = posI;
        this.posJ = posJ;
        blockType = block;
        switch(block) {
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
    }
    
    public int getBlockType() {
        return blockType;
    }

    @Override
    public void collision(GameObject other) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateState() {
    }
    
}