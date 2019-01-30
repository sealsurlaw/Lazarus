package Lazarus;

import GameEngine.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level4 extends Map {
    
    private enum Sym {
        WALL, EMPTY, BUTTON, LAZARUS, CARDBOARD, WOOD, STONE, METAL
    }
    
    NextBlock next;
    int blockCounter = 0;
    int blockCounterLimit = 60;
    int blockType = (int) (Math.random() * 4);
    boolean stopBlocks = false;

    Sym X = Sym.WALL;
    Sym O = Sym.EMPTY;
    Sym B = Sym.BUTTON;
    Sym L = Sym.LAZARUS;
    Sym C = Sym.CARDBOARD;
    Sym W = Sym.WOOD;
    Sym S = Sym.STONE;
    Sym M = Sym.METAL;

    Sym[][] map;
    
    Game ng;

    public Level4(Game game) {
        super(game);
                        
        objects = new ArrayList<>();
        obj = new GameObject[12][16];
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 16; ++j) {
                obj[i][j] = null;
            }
        }
        buttons = new ArrayList<>();
        toolbar = new ArrayList<>();
        
        background = Resources.getImage(Resources.Images.BACKGROUND)[0];
        
        map = new Sym[][]{
            {X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X},
            {X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X},
            {X, O, O, O, O, O, O, O, O, O, O, O, O, O, O, X},
            {X, B, O, O, O, O, O, B, O, O, O, O, O, O, B, X},
            {X, X, O, O, O, O, O, X, O, O, O, O, O, O, X, X},
            {X, X, O, O, O, O, O, X, O, O, O, O, O, O, X, X},
            {X, X, O, O, O, O, O, X, O, O, O, O, O, O, X, X},
            {X, X, O, O, O, O, O, X, O, O, O, O, O, O, X, X},
            {X, X, O, O, O, O, X, X, X, L, O, O, O, O, X, X},
            {X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X},
            {X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X},
            {X, X, X, X, X, X, X, X, X, X, X, X, X, X, X, X}
        };
        
        decodeMap();
        
        next = new NextBlock(blockType);
        toolbar.add(next);
        
    }

    @Override
    public void decodeMap() {    
        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[0].length; ++j) {
                switch(map[i][j]) {
                    case WALL:
                        GameObject wall = new Wall(j*40, i*40);
                        //objects.add(wall);
                        obj[i][j] = wall;
                        break;
                    case BUTTON:
                        GameObject button = new Button(j*40, i*40);
                        buttons.add(button);
                        break;
                    case LAZARUS:
                        PlayerControls playerCtl = new PlayerControls(
                            KeyEvent.VK_LEFT,
                            KeyEvent.VK_RIGHT
                        );
                        lazarus = (Player) new Player(j*40, i*40,
                                playerCtl, this);
                        game.getKeyObservable().addObserver(lazarus);
                        break;
                }
            }
        }
    }
    
    @Override
    public void update() {
        ++blockCounter;
        if (blockCounter == blockCounterLimit && stopBlocks == false) {
            if (blockCounterLimit > 5) blockCounterLimit -= 1;
            falling = new FallingBlock(blockType,
                    lazarus.getPosI()*40, this);
            objects.add(falling);
            blockType = (int) (Math.random() * 4);
            
            next.setImage(blockType);
            
            blockCounter = 0;
        }
        lazarus.updateState();
        for (int i = 0; i < objects.size(); ++i) {
            objects.get(i).updateState();
        }
    }
    
    @Override
    public void nextMap(){
        Map nextMap = new Level5(game);
        game.setMap(nextMap);
        nextMap.registerMovable(game.getKeyObservable());
    }
    
    @Override
    public void addBox(int type, GameObject go, int j, int i) {
        switch (type) {
            case 0:
                map[i][j] = C;
                break;
            case 1:
                map[i][j] = W;
                break;
            case 2:
                map[i][j] = S;
                break;
            case 3:
                map[i][j] = M;
                break;
        }
        obj[i][j] = go;
    }
    
    @Override
    public void removeBox(int j, int i) {
        map[i][j] = O;
        obj[i][j] = null;
    }
    
    
    
    @Override
    public boolean isClear(int i, int j) {
        if (map[j][i] == X ||
                map[j][i] == C ||
                map[j][i] == W ||
                map[j][i] == S ||
                map[j][i] == M){
            return false;
        }
        return true;
    }
    
    public int getBlockType(int j, int i) {
        if (obj[i][j].getClass() != Wall.class) {
            return ((StoppedBlock) obj[i][j]).getBlockType();
        }
        return 4;
    }
    
    @Override
    public void stopBlocks() {
        stopBlocks = true;
    }
}
