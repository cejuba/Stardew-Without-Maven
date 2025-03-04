package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Player;
import fr.cejuba.stardew.tile.TileManager;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends Canvas {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 32;
    public final int maxScreenRow = 16;
    public final int screenWidth = maxScreenCol * tileSize; // 768 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 768 pixels

    // World Settings

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    // System config

    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public EventHandler eventHandler = new EventHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    public UI ui = new UI(this);

    // Entity and Object

    public Player player = new Player(this, keyHandler);
    public Entity[] objects = new Entity[10]; // If we want to increase the number of object we can do it here, but careful about performances;
    public Entity[] npc =  new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GameStates
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    private AnimationTimer animationTimer;

    public GamePanel() {
        this.setFocusTraversable(true);
        this.requestFocus();
        player.setKeyHandler(keyHandler);
    }

    public void setupGame(){
        startGameLoop();
    }

    private void startGameLoop() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        };
        animationTimer.start();

        Task<Void> gameLogicTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                double drawInterval = 1_000_000_000 / FPS;
                double delta = 0;
                long lastTime = System.nanoTime();
                long currentTime;

                while (!isCancelled()) {
                    currentTime = System.nanoTime();
                    delta += (currentTime - lastTime) / drawInterval;
                    lastTime = currentTime;

                    if (delta >= 1) {
                        javafx.application.Platform.runLater(() -> update());
                        delta--;
                    }
                }
                return null;
            }
        };

        Thread gameLogicThread = new Thread(gameLogicTask);
        gameLogicThread.setDaemon(true);
        gameLogicThread.start();
    }


    public void update() {
        if (gameState == playState) {
            player.update();
        }
    }

    public void draw(){
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
        graphicsContext.setFill(Color.BLACK);

        tileManager.draw(graphicsContext);
        player.draw(graphicsContext);
        ui.draw(graphicsContext);
    }

    public void playMusic(int i){
        music.setFile(i);
        music.playSound();
        music.loopSound();
    }

    public void stopMusic(){
        music.stopSound();
    }

    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.playSound();
    }
}
