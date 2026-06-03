package main;

import demo.DemoScene;
import demo.PhysicsScene;
import engine.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game("Mini2DGameEngine", 900, 600);
        game.addScene(new DemoScene());
        game.addScene(new PhysicsScene());
        game.start();
    }
}
