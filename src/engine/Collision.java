package engine;

public class Collision {
    public static boolean intersects(Entity a, Entity b) {
        return !a.collected && !b.collected && a.bounds().intersects(b.bounds());
    }

    public static void resolveAabb(Entity moving, Entity solid) {
        if (!intersects(moving, solid)) {
            return;
        }

        double overlapLeft = moving.position.x + moving.width - solid.position.x;
        double overlapRight = solid.position.x + solid.width - moving.position.x;
        double overlapTop = moving.position.y + moving.height - solid.position.y;
        double overlapBottom = solid.position.y + solid.height - moving.position.y;

        double smallestXOverlap = Math.min(overlapLeft, overlapRight);
        double smallestYOverlap = Math.min(overlapTop, overlapBottom);

        if (smallestXOverlap < smallestYOverlap) {
            if (overlapLeft < overlapRight) {
                moving.position.x -= overlapLeft;
            } else {
                moving.position.x += overlapRight;
            }
            moving.velocity.x = 0;
        } else {
            if (overlapTop < overlapBottom) {
                moving.position.y -= overlapTop;
            } else {
                moving.position.y += overlapBottom;
            }
            moving.velocity.y = 0;
        }
    }
}
