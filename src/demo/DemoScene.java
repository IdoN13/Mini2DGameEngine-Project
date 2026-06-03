package demo;

import engine.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class DemoScene extends Scene {
    private final List<Entity> walls = new ArrayList<>();
    private Entity player;
    private Entity goal;
    private boolean won;

    @Override
    public String name() {
        return "Demo Scene";
    }

    @Override
    public void reset() {
        walls.clear();
        player = new Entity(80, 80, 32, 32, new Color(80, 170, 255));
        goal = new Entity(760, 480, 28, 28, new Color(255, 205, 80));
        won = false;

        addWall(0, 560, 900, 40);
        addWall(0, 0, 20, 600);
        addWall(880, 0, 20, 600);
        addWall(180, 150, 220, 28);
        addWall(480, 250, 260, 28);
        addWall(260, 390, 230, 28);
    }

    @Override
    public void update(double dt, Input input) {
        double playerSpeed = 240;
        player.velocity.set(0, 0);

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) player.velocity.x = -playerSpeed;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) player.velocity.x = playerSpeed;
        if (input.isDown(KeyEvent.VK_UP) || input.isDown(KeyEvent.VK_W)) player.velocity.y = -playerSpeed;
        if (input.isDown(KeyEvent.VK_DOWN) || input.isDown(KeyEvent.VK_S)) player.velocity.y = playerSpeed;

        player.update(dt);
        for (Entity wall : walls) {
            Collision.resolveAabb(player, wall);
        }

        if (Collision.intersects(player, goal)) {
            goal.collected = true;
            won = true;
        }
    }

    @Override
    public void render(Graphics2D g, Renderer renderer) {
        goal.render(g);
        for (Entity wall : walls) wall.render(g);
        player.render(g);
        renderer.text(g, "Reach the yellow square.", 16, 48);
        if (won) {
            renderer.text(g, "Goal reached. Press R to reset.", 16, 72);
        }
    }

    private void addWall(int x, int y, int w, int h) {
        Entity wall = new Entity(x, y, w, h, new Color(90, 95, 110));
        wall.solid = true;
        walls.add(wall);
    }
}
