package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener {
    private final Set<Integer> down = new HashSet<>();
    private final Set<Integer> pressed = new HashSet<>();

    public boolean isDown(int keyCode) {
        return down.contains(keyCode);
    }

    public boolean wasPressed(int keyCode) {
        return pressed.contains(keyCode);
    }

    public void endFrame() {
        pressed.clear();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!down.contains(e.getKeyCode())) {
            pressed.add(e.getKeyCode());
        }
        down.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        down.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
