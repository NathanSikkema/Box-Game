package Code;

import Code.entities.MenuParticle;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static Code.GameObject.r;

public class HUD {
    static final ArrayList<Integer> scoreList = new ArrayList<>();
    private static final String SCORE_FILE = "src/Code/GameScores.txt";
    public static double HEALTH = 100;
    public static int BANK = 0;
    public static double score = 0;
    public static double scoreRate = 1;
    public static int collisionCount = 0;
    public static int level = 0;
    public static boolean scoreSaved = false;
    private final Handler handler;
    Image image = new ImageIcon("src/Assets/Heart.png").getImage();
    private int greenValue = 255;
    private int redValue = 0;

    public HUD(Handler handler) {
        this.handler = handler;
        updateList();
    }

    public static void resetValues() {
        HEALTH = 100;
        BANK = 0;
        score = 0;
        scoreRate = 1;
        collisionCount = 0;
        level = 0;
        scoreSaved = false;


    }

    public static String numStringConvert(double num) {
        return NumberFormat.getNumberInstance(Locale.US).format(num);
    }

    public static void displayData(Graphics g, Handler handler) {
        int basicEnemyCount = 0;
        int trailingEnemyCount = 0;
        int coins = 0;
        int hearts = 0;

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.BasicEnemy) basicEnemyCount++;
            if (handler.object.get(i).getId() == ID.TrailingEnemy) trailingEnemyCount++;
            if (handler.object.get(i).getId() == ID.Coin) coins++;
            if (handler.object.get(i).getId() == ID.Health) hearts++;
        }
        Font healthFont = g.getFont().deriveFont(Font.BOLD, 20);
        g.setFont(healthFont);
        g.drawString(String.format("%.0f", HEALTH), 50, 39);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 16));

        g.drawString(String.format("Score: " + (int) score), 20, 80);
        g.drawString("Level: " + level, 20, 100);
        g.drawString("Basic Enemies: " + basicEnemyCount, 20, 120);
        g.drawString("Trailing Enemies: " + trailingEnemyCount, 20, 140);
        g.drawString("Collision Count: " + collisionCount, 20, 160);
        g.drawString("Coins Generated: " + (coins + BANK), 20, 180);
        g.drawString("Coins Collected: " + BANK, 20, 200);
        g.drawString("Hearts: " + hearts, 20, 220);
    }

    public static double getScore() {
        return score;
    }

    public static int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        HUD.level = level;
    }

    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 150);
        greenValue = (int) HEALTH * 2;
        greenValue = (int) Game.clamp(greenValue, 0, 255);
        redValue = 255 - greenValue;
        redValue = (int) Game.clamp(redValue, 0, 255);
        if (HEALTH != 0) score += scoreRate;


        if (HUD.HEALTH == 0 && !scoreSaved) {
            Game.gameState = STATE.GameOver;
            saveScore((int) score);
            scoreSaved = true;
            handler.clearEnemies();
            for (int i = 0; i < 20; i++) {
//                handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.MenuParticle, handler));
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(15, 15, 300, 32);
        g.setColor(new Color(redValue, greenValue, 0));
        g.fillRect(15, 15, (int) (HEALTH * 2), 32);
        g.drawImage(image, 20, 19, 25, 25, null);
        g.setColor(Color.white);

        g.drawRect(15, 15, 300, 32);
        g.drawRect(15, 55, 200, 175);

        displayData(g, handler);


    }

    private void updateList() {
        scoreList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scoreList.add(Integer.parseInt(line.trim()));
            }
        } catch (Exception ignored) {
        }
    }

    private void saveScore(int newScore) {
        if (newScore >= 1000) {
            scoreList.add(newScore);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
                for (int score : scoreList) {
                    writer.write(String.valueOf(score));
                    writer.newLine();
                }
            } catch (Exception ignored) {
            }
        }

    }
}
