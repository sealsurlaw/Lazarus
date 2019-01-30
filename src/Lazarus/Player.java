/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lazarus;

import GameEngine.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class Player extends GameObject implements Observer {

    private Set<Integer> keyPressed = new HashSet<>();
    private PlayerControls controls;
    private Map map;
    private int animationCounter;
    private boolean moveEnabled;
    private int moveModifier = 0;
    private int animationModifier = 1;
    private int posI;
    private int posJ;
    private States state;

    enum States {
        STAND,
        LEFT,
        RIGHT,
        JUMP_UP_LEFT,
        JUMP_UP_RIGHT,
        JUMP_DOWN_LEFT,
        JUMP_DOWN_RIGHT,
        AFRAID,
        SQUISHED
    };

    public Player(int x, int y, PlayerControls pc, Map map) {
        super(Resources.getImage(Resources.Images.STAND), x, y);
        controls = pc;
        this.map = map;
        posI = x / 40;
        posJ = y / 40;
        animationCounter = 0;
        moveEnabled = true;
        state = States.STAND;
        collisionMask = new Rectangle(x, y, 40, 40);
    }

    @Override
    public void collision(GameObject other) {
        if (other.getClass() == FallingBlock.class) {
            FallingBlock falling = (FallingBlock) other;
            if (this.collisionMask.intersects(other.getCollisionMask())
                    && state != States.SQUISHED) {
                Resources.playSound(Resources.Sounds.SQUISHED);
                moveEnabled = false;
                x = posI * 40;
                y = posJ * 40;
                state = States.SQUISHED;
                setImageArray(Resources.getImage(Resources.Images.SQUISHED));
                animationCounter = 0;
                animationModifier = 1;
                moveModifier = 0;
                setIndex(0);
                map.gameStatus(Map.Status.LOSE);
            }
        }

        if (other.getClass() == Button.class
                && this.collisionMask.intersects(other.getCollisionMask())) {
            if (moveEnabled == true)
                Resources.playSound(Resources.Sounds.BUTTON);
            moveEnabled = false;
            map.stopBlocks();
            map.gameStatus(Map.Status.WIN);
        }
    }

    @Override
    public void updateState() {
        //System.out.println("posI: " + posI + " posJ: " + posJ);
        // Increments animation
        animationCounter += animationModifier;
        if (moveEnabled == false) {
            setIndex(animationCounter);
            //System.out.println("animationCounter: " + animationCounter);
        }

        if (animationCounter == 7
                && (state == States.JUMP_UP_LEFT
                || state == States.JUMP_UP_RIGHT
                || state == States.LEFT
                || state == States.RIGHT)) {
            stopMoving();
        }

        if (animationCounter == 0
                && (state == States.JUMP_DOWN_LEFT
                || state == States.JUMP_DOWN_RIGHT)) {
            stopMoving();
        }

        // If player presses left
        if (moveEnabled == true && keyPressed.contains(controls.LEFT)) {
            //TODO Check if can move before being squished
            if (map.isClear(posI - 1, posJ)
                    && map.isClear(posI - 1, posJ + 1)
                    && !map.isClear(posI - 1, posJ + 2)) {
                move(States.JUMP_DOWN_LEFT);
            } else if (map.isClear(posI - 1, posJ)
                    && !map.isClear(posI - 1, posJ + 1)) {
                move(States.LEFT);
            } else if (map.isClear(posI - 1, posJ - 1)
                    && !map.isClear(posI - 1, posJ)) {
                move(States.JUMP_UP_LEFT);
            }
        }
        if (moveEnabled == true && keyPressed.contains(controls.RIGHT)) {
            //TODO Check if can move before being squished
            if (map.isClear(posI + 1, posJ)
                    && map.isClear(posI + 1, posJ + 1)
                    && !map.isClear(posI + 1, posJ + 2)) {
                move(States.JUMP_DOWN_RIGHT);
            } else if (map.isClear(posI + 1, posJ)
                    && !map.isClear(posI + 1, posJ + 1)) {
                move(States.RIGHT);
            } else if (map.isClear(posI + 1, posJ - 1)
                    && !map.isClear(posI + 1, posJ)) {
                move(States.JUMP_UP_RIGHT);
            }
        }
        x += moveModifier * 1;
    }

    @Override
    public void update(Observable o, Object arg) {
        KeyboardObservable ko = (KeyboardObservable) o;
        keyPressed = ko.getKey();
    }

    public void move(States st) {
        Resources.playSound(Resources.Sounds.MOVE);
        state = st;
        // Set image array to appropriate images
        if (state == States.LEFT) {
            setImageArray(Resources.getImage(Resources.Images.LEFT));
            moveModifier = -1;
            animationModifier = 1;
            animationCounter = 0;
            --posI;
        }
        if (state == States.RIGHT) {
            setImageArray(Resources.getImage(Resources.Images.RIGHT));
            moveModifier = 1;
            animationModifier = 1;
            animationCounter = 0;
            ++posI;
        }

        if (state == States.JUMP_UP_LEFT) {
            setImageArray(Resources.getImage(Resources.Images.JUMP_LEFT));
            moveModifier = -1;
            animationCounter = 0;
            animationModifier = 1;
            --posI;
            --posJ;
        }
        if (state == States.JUMP_DOWN_LEFT) {
            setImageArray(Resources.getImage(Resources.Images.JUMP_RIGHT));
            moveModifier = -1;
            animationCounter = 6;
            animationModifier = -1;
            --posI;
            ++posJ;
        }
        if (state == States.JUMP_UP_RIGHT) {
            setImageArray(Resources.getImage(Resources.Images.JUMP_RIGHT));
            moveModifier = 1;
            animationCounter = 0;
            animationModifier = 1;
            ++posI;
            --posJ;
        }
        if (state == States.JUMP_DOWN_RIGHT) {
            setImageArray(Resources.getImage(Resources.Images.JUMP_LEFT));
            moveModifier = 1;
            animationCounter = 6;
            animationModifier = -1;
            ++posI;
            ++posJ;
        }
        // Set to first index
        setIndex(animationCounter);
        // Account for difference in sprite size
        if (state == States.LEFT || state == States.JUMP_UP_LEFT) {
            x -= 41;
            y -= 40;
        }
        if (state == States.RIGHT || state == States.JUMP_UP_RIGHT) {
            x -= 2;
            y -= 40;
        }
        if (state == States.JUMP_DOWN_LEFT) {
            x -= 41;
        }
        if (state == States.JUMP_DOWN_RIGHT) {

        }

        // Collision mask update
        collisionMask = new Rectangle(posI * 40, posJ * 40, 40, 40);
        // Disable controls while moving
        moveEnabled = false;
    }

    public void stopMoving() {
        setImageArray(Resources.getImage(Resources.Images.STAND));
        moveModifier = 0;
        setIndex(0);
        if (state == States.LEFT || state == States.JUMP_DOWN_LEFT) {
            x += 8;
            y += 40;
        }
        if (state == States.JUMP_DOWN_LEFT) {
            x -= 1;
        }
        if (state == States.JUMP_UP_LEFT) {
            x += 8;
        }
        if (state == States.RIGHT) {
            x += 35;
            y += 40;
        }
        if (state == States.JUMP_UP_RIGHT) {
            x += 35;
        }
        if (state == States.JUMP_DOWN_RIGHT) {
            x += 34;
            y += 40;
        }
        moveEnabled = true;

        state = States.STAND;

    }

    public void setImageArray(BufferedImage[] bf) {
        imgArray = bf;
    }

    public int getPosI() {
        return posI;
    }

    public int getPosJ() {
        return posJ;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(currImg, x, y, null);
        //g2.drawRect(collisionMask.x, collisionMask.y, 40, 40);
    }

}
