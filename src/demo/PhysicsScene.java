package demo;

import engine.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PhysicsScene extends Scene {
    private final List<Entity> solids = new ArrayList<>();
    private final Physics physics = new Physics();
    private Entity body;

    @Override
    public String name() {
        return "Physics Scene";
    }

    @Override
    public void reset() {
        solids.clear();
        body = new Entity(120, 120, 36, 36, new Color(120, 220, 140));
        body.dynamic = true;
        physics.gravityEnabled = true;

        addSolid(0, 560, 900, 40);
        addSolid(300, 430, 260, 28);
        addSolid(620, 320, 150, 28);
    }

    @Override
    public void update(double dt, Input input) {
        body.acceleration.set(0, 0);
        double force = 900;

        if (input.isDown(KeyEvent.VK_LEFT) || input.isDown(KeyEvent.VK_A)) body.acceleration.x = -force;
        if (input.isDown(KeyEvent.VK_RIGHT) || input.isDown(KeyEvent.VK_D)) body.acceleration.x = force;
        if (input.wasPressed(KeyEvent.VK_SPACE)) physics.gravityEnabled = !physics.gravityEnabled;
        if (input.wasPressed(KeyEvent.VK_UP) || input.wasPressed(KeyEvent.VK_W)) body.velocity.y = -420;

        physics.update(body, solids, dt);
    }

    @Override
    public void render(Graphics2D g, Renderer renderer) {
        for (Entity solid : solids) solid.render(g);
        body.render(g);
        renderer.text(g, "Space toggles gravity. Up/W jumps.", 16, 48);
        renderer.text(g, "Gravity: " + (physics.gravityEnabled ? "on" : "off"), 16, 72);
    }

    private void addSolid(int x, int y, int w, int h) {
        Entity solid = new Entity(x, y, w, h, new Color(95, 100, 115));
        solid.solid = true;
        solids.add(solid);
    }
}
