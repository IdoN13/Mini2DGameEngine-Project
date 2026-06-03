package engine;

import java.awt.Graphics2D;

public abstract class Scene {
    protected Game game;

    public void attach(Game game) {
        this.game = game;
    }

    public abstract String name();
    public abstract void reset();
    public abstract void update(double dt, Input input);
    public abstract void render(Graphics2D g, Renderer renderer);
}
