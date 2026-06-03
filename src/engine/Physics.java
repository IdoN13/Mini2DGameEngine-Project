package engine;

import java.util.List;

public class Physics {
    public boolean gravityEnabled = false;
    public double gravity = 900.0;
    public double damping = 0.88;

    public void update(Entity body, List<Entity> walls, double dt) {
        if (gravityEnabled) {
            body.acceleration.y = gravity;
        }

        body.update(dt);
        body.velocity.x *= Math.pow(damping, dt);

        for (Entity wall : walls) {
            if (wall.solid) {
                Collision.resolveAabb(body, wall);
            }
        }
    }
}
