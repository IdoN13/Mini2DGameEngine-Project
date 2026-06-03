package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Renderer {
    public void clear(Graphics2D g, int width, int height) {
        g.setColor(new Color(25, 28, 34));
        g.fillRect(0, 0, width, height);
    }

    public void text(Graphics2D g, String text, int x, int y) {
        g.setColor(new Color(230, 230, 230));
        g.setFont(new Font("Consolas", Font.PLAIN, 14));
        g.drawString(text, x, y);
    }
}
