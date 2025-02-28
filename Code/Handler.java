package Code;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    public LinkedList<GameObject> object = new LinkedList<>();

    public void tick() {

        for (int i = object.size() - 1; i >= 0; i--) {
            GameObject tempObject;
            try {
                tempObject = object.get(i);
                tempObject.tick();
            } catch (Exception ignored) {
            }
            ;


        }


    }

    public void render(Graphics g) {
        for (int i = object.size() - 1; i >= 0; i--) {
            try {
                GameObject tempObject = object.get(i);
                tempObject.render(g);
            } catch (Exception ignored) {
            }
        }


    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void clearEnemies() {

        this.object.clear();
    }

}
