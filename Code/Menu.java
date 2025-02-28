package Code;

import Code.entities.MenuParticle;
import Code.entities.Player;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

import static Code.GameObject.r;


public class Menu extends MouseAdapter {


    private final Handler handler;
    private final int MenuBoxX = (int) (0.5 * Game.WIDTH) - 95;
    private final int MenuTextX = (int) (0.5 * Game.WIDTH) - 25;
    private boolean particles = false;

    public Menu(Handler handler) {
        this.handler = handler;
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {

        if (mx > x && mx < x + width) {
            return my > y && my < y + height;

        } else return false;

    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //Play

        if (mouseOver(mx, my, MenuBoxX, 250, 200, 64) && Game.gameState == STATE.MainMenu) {
            Game.gameState = STATE.RUNNING;
            handler.clearEnemies();
            particles = false;
            HUD.resetValues();
            handler.addObject(new Player(Game.WIDTH / 2 - 16, Game.HEIGHT / 2 - 16, ID.Player, handler));
        }

        // Main Menu --> Help
        if (mouseOver(mx, my, MenuBoxX, 350, 200, 64) && Game.gameState == STATE.MainMenu) {
            Game.gameState = STATE.Help;
        }
        // Help Menu --> Main Menu
        if (mouseOver(mx, my, MenuBoxX, 600, 200, 64) && Game.gameState == STATE.Help) {
            Game.gameState = STATE.MainMenu;
        }
        // End of Game --> Main Menu
        if (mouseOver(mx, my, MenuBoxX, 650, 250, 80) && Game.gameState == STATE.GameOver) {
            Game.gameState = STATE.MainMenu;

        }
        // Main Menu --> Exit Game
        if (mouseOver(mx, my, MenuBoxX, 450, 200, 64) && Game.gameState == STATE.MainMenu) {
            System.exit(1);
        }
    }

    public void render(Graphics g) {
        int x = (int) (0.5 * Game.WIDTH);
        Font fnt = new Font("arial", Font.BOLD, 50);
        Font fnt2 = new Font("arial", Font.BOLD, 30);

        g.setFont(fnt);
        g.setColor(Color.white);
        if (!particles) {
            for (int i = 0; i < 20; i++) {
                handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.MenuParticle, handler));
            }
            particles = true;
//            if (handler.object.getFirst() instanceof MenuParticle particle) {
//            }
        }
        if (Game.gameState == STATE.MainMenu) {


//            g.drawLine(Game.WIDTH / 2, 0, Game.WIDTH / 2, Game.HEIGHT);
            g.drawString("Menu", x - 70, 170);

            g.setFont(fnt2);
            g.drawRect(MenuBoxX, 250, 200, 64);
            g.drawString("Play", MenuTextX, 290);

            g.drawRect(MenuBoxX, 350, 200, 64);
            g.drawString("Help", MenuTextX, 390);

            g.drawRect(MenuBoxX, 450, 200, 64);
            g.drawString("Quit", MenuTextX, 490);
        } else if (Game.gameState == STATE.Help) {

            g.drawString("Help", x - 70, 170);

            g.setFont(fnt2);
            x = x - 300;
            g.drawString("Use 'W'  'A'  'S'  'D'  to dodge the enemies.", x, 300);
            g.drawString("Use 'CTRL' to accelerate, and 'SHIFT' to slow down", x - 70, 350);


            g.drawRect(MenuBoxX, 600, 200, 64);
            g.drawString("Back", MenuTextX, 640);
        } else if (Game.gameState == STATE.GameOver) {
            displayLeaderBoard(g);
            g.drawRect(x - 110, 650, 250, 80);
            g.drawString("Main Menu", x - 80, 702);


        }
    }

    public void tick() {

    }

    private void displayLeaderBoard(Graphics g) {

        int x = (int)(0.5 * Game.WIDTH) - 200;
        g.setColor(Color.white);
        Font endGameFont = g.getFont().deriveFont(Font.BOLD, 85);
        g.setFont(endGameFont);
        g.drawString("Game Over", x, 200);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 60));

        g.drawRect(x - 5, 220, 460, 400);
        g.setColor(Color.darkGray.darker());
        g.fillRect(x - 4, 220 + 1, 460 - 1, 400 - 1);

        g.setColor(Color.white);
        g.drawString("LEADERBOARD", x, 270);
        g.drawLine(x - 5, 275, x + 455, 275);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 40));

        // Sort the scores for display only
        ArrayList<Integer> sortedScores = new ArrayList<>(HUD.scoreList);
        sortedScores.sort(Comparator.reverseOrder());

        int y = 310;
        for (int i = 0; i != 5; i++) {
            int tempScore = 0;
            try {
                tempScore = sortedScores.get(i);
            } catch (IndexOutOfBoundsException ignored) {
            }
            if (tempScore != 0) g.drawString(String.format("%d. %s", i + 1, HUD.numStringConvert(tempScore)), x + 75, y);
            else g.drawString((i + 1) + ". 0", x + 75, y);
            y += 40;
        }

        if (HUD.score >= sortedScores.getFirst()) g.drawString("NEW HIGH SCORE!", x + 75, y);
        else g.drawString("Top Score:  " + HUD.numStringConvert(sortedScores.getFirst()), x + 75, y);

        if (sortedScores.size() > 4) {
            if (HUD.score < sortedScores.get(4)) {
                int position = 1;
                for (Integer s : sortedScores) {
                    if (HUD.score < s) position++;
                    else break;
                }
                g.drawString("You placed: " + position + "th", x + 75, y + 80);
            }
        }
        g.drawString("Your Score: " + HUD.numStringConvert((int) HUD.score), x + 75, y + 40);
    }

}
