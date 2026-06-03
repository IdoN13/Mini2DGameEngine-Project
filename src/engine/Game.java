package engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements Runnable {
    private final JFrame frame;
    private final Input input = new Input();
    private final Renderer renderer = new Renderer();
    private final List<Scene> scenes = new ArrayList<>();
    private int sceneIndex = 0;
    private boolean running;
    private double fps;

    public Game(String title, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(input);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void addScene(Scene scene) {
        scene.attach(this);
        scene.reset();
        scenes.add(scene);
    }

    public void start() {
        frame.setVisible(true);
        running = true;
        new Thread(this, "game-loop").start();
    }

    @Override
    public void run() {
        long previous = System.nanoTime();
        double lag = 0.0;
        double tick = 1.0 / 60.0;

        while (running) {
            long now = System.nanoTime();
            double frameTime = Math.min(0.25, (now - previous) / 1_000_000_000.0);
            previous = now;
            lag += frameTime;
            fps = 1.0 / Math.max(frameTime, 0.0001);

            while (lag >= tick) {
                update(tick);
                lag -= tick;
            }

            repaint();
            input.endFrame();
            sleepBriefly();
        }
    }

    private void update(double dt) {
        if (input.wasPressed(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
        if (input.wasPressed(KeyEvent.VK_TAB)) {
            sceneIndex = (sceneIndex + 1) % scenes.size();
        }
        if (input.wasPressed(KeyEvent.VK_R)) {
            currentScene().reset();
        }
        currentScene().update(dt, input);
    }

    private Scene currentScene() {
        return scenes.get(sceneIndex);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        renderer.clear(g, getWidth(), getHeight());
        currentScene().render(g, renderer);
        renderer.text(g, "R reset", 16, 24);
    }

    private void sleepBriefly() {
        try {
            Thread.sleep(2);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
