package Code;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private final Handler handler;
    private final boolean[] keyDown = new boolean[4];
    private double vel = 15;
    private boolean shiftPressed = false;
    private boolean ctrlPressed = false;

    public KeyInput(Handler handler) {
        this.handler = handler;
        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        handleSpecialKeys(key, true);

        GameObject tempObject = getPlayerObject();
        if (tempObject != null && Game.gameState == STATE.RUNNING) {
            switch (key) {
                case KeyEvent.VK_W -> {
                    tempObject.setVelY(-vel);
                    keyDown[0] = true;
                }
                case KeyEvent.VK_S -> {
                    tempObject.setVelY(vel);
                    keyDown[1] = true;
                }
                case KeyEvent.VK_D -> {
                    tempObject.setVelX(vel);
                    keyDown[2] = true;
                }
                case KeyEvent.VK_A -> {
                    tempObject.setVelX(-vel);
                    keyDown[3] = true;
                }
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            if (Game.gameState == STATE.GameOver){
                Game.gameState = STATE.MainMenu;
            }
            else if (Game.gameState != STATE.RUNNING)
                System.exit(1);
            else {
                Game.gameState = STATE.GameOver;
                handler.clearEnemies();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        handleSpecialKeys(key, false);

        GameObject tempObject = getPlayerObject();
        if (tempObject != null) {
            switch (key) {
                case KeyEvent.VK_W -> keyDown[0] = false;
                case KeyEvent.VK_S -> keyDown[1] = false;
                case KeyEvent.VK_D -> keyDown[2] = false;
                case KeyEvent.VK_A -> keyDown[3] = false;
            }

            // Vertical movement
            if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);

            // Horizontal movement
            if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
        }
    }

    private void handleSpecialKeys(int key, boolean pressed) {
        if (Game.gameState == STATE.RUNNING) {
            if (key == KeyEvent.VK_SHIFT) {
                shiftPressed = pressed;
                ctrlPressed = !pressed;
                vel = pressed ? 1:15;
                updateVelocity();
            } else if (key == KeyEvent.VK_CONTROL) {
                ctrlPressed = pressed;
                shiftPressed = !pressed;
                vel = pressed ? 30:15;
                updateVelocity();
            }
        }
    }

    private GameObject getPlayerObject() {
        try {
            return handler.object.getFirst();
        } catch (Exception ignored) {
            return null;
        }
    }

    private void updateVelocity() {
        GameObject tempObject = null;
        try {
            tempObject = handler.object.getFirst();

        } catch (Exception ignored) {
        }

        if (tempObject != null) {
            if (keyDown[0]) tempObject.setVelY(-vel);
            if (keyDown[1]) tempObject.setVelY(vel);
            if (keyDown[2]) tempObject.setVelX(vel);
            if (keyDown[3]) tempObject.setVelX(-vel);
        }

    }
}
