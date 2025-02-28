package Code;

import Code.entities.*;

import java.util.Random;


public class Spawn {

    private final Handler handler;
    private final HUD hud;
    private final Random r = new Random();
    private int value = 100;

    public Spawn(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick() {

        if (HUD.getScore() > value || HUD.getScore() == 0) {

            hud.setLevel(HUD.getLevel() + 1);

            if (HUD.getLevel() <= 100)
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 100) + 5, r.nextInt(700) + 5, ID.BasicEnemy, handler));

            handler.addObject(new Coin(r.nextInt(Game.WIDTH - 100) + 5, r.nextInt(743) + 5, ID.Coin, handler));

            if (HUD.getLevel() % 2 == 0) {
                handler.addObject(new Health(r.nextInt(Game.WIDTH - 100) + 5, r.nextInt(700) + 5, ID.Health, Math.floorDiv((int) HUD.score, 500) + 1, handler));
                if (HUD.getLevel() % 3 == 0)
                    handler.addObject(new TrailingEnemy(r.nextInt(Game.WIDTH - 100) + 5, r.nextInt(700) + 5, ID.TrailingEnemy, handler));

            }
            if (HUD.getLevel() % 4 == 0)
                handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 100) + 5, r.nextInt(700) + 5, ID.FastEnemy, handler));

            if (HUD.getLevel() % 20 == 0) {
                BasicEnemy.damageFactor += 1;
                TrailingEnemy.damageFactor += 1;
                HUD.scoreRate += 1;
            }

            value += 100;

        }


    }

}
