package engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Entity {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();
    public final Vector2 acceleration = new Vector2();
    public int width;
    public int height;
    public Color color;
    public boolean solid;
    public boolean dynamic;
    public boolean collected;

    public Entity(double x, double y, int width, int height, Color color) {
        this.position.set(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Rectangle bounds() {
        return new Rectangle((int) position.x, (int) position.y, width, height);
    }

    public void update(double dt) {
        velocity.x += acceleration.x * dt;
        velocity.y += acceleration.y * dt;
        position.x += velocity.x * dt;
        position.y += velocity.y * dt;
    }

    public void render(Graphics2D g) {
        if (collected) {
            return;
        }
        g.setColor(color);
        g.fillRect((int) position.x, (int) position.y, width, height);
    }
}
