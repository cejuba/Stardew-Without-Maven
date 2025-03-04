package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Player;
import fr.cejuba.stardew.tile.TileManager;
import javafx.animation.AnimationTimer;
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
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public EventHandler eventHandler = new EventHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    public UI ui = new UI(this);

    public Player player = new Player(this, keyHandler);
    public Entity[] objects = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

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

        // Initialize objects and NPCs
    }

    public void setupGame() {
        startGameLoop();
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        // playMusic(0);
    }

    private void startGameLoop() {
        animationTimer = new AnimationTimer() {
            private double drawInterval = 1_000_000_000/FPS;
            private double delta = 0;
            private long lastTime = System.nanoTime();
            private long currentTime;

            @Override
            public void handle(long now) {

                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if(delta >=1){
                    if(gameState==playState){
                        update();
                    }
                    draw();
                    delta--;
                }
                /*
                if (counter <= 1) {

                    lastTime = now;
                    return;
                }

                long elapsedTime = now - lastTime;
                if (elapsedTime >= 1_000_000_000 / FPS) {
                    if (gameState == playState) {
                        update();
                    }
                    draw();
                    lastTime = now;
                }
                */
            }
        };
        animationTimer.start();
    }

    public void update() {
        player.update();
        for (Entity npcEntity : npc) {
            if (npcEntity != null) {
                npcEntity.update();
            }
        }
        for(int i = 0; i < monster.length; i++){
            if(monster[i] != null){
                monster[i].update();
            }
        }
    }

    public void draw() {
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());

        tileManager.draw(graphicsContext);

        entityList.add(player);

        for (Entity item : npc) {
            if (item != null) {
                entityList.add(item);
            }
        }

        for (Entity object : objects) {
            if (object != null) {
                entityList.add(object);
            }
        }

        for (Entity value : monster) {
            if (value != null) {
                entityList.add(value);
            }
        }

        entityList.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                return Integer.compare(e1.worldY, e2.worldY);
            }

        });

        for (Entity entity : entityList) {
            entity.draw(graphicsContext);
        }
        entityList.clear();

        // TODO: Gestion d'affichage l'un au dessus de l'autre
/*
        // Draw objects
        for (Entity object : objects) {
            if (object != null) {
                object.draw(graphicsContext);
            }
        }

        // Draw NPCs
        for (Entity npcEntity : npc) {
            if (npcEntity != null) {
                npcEntity.draw(graphicsContext);
            }
        }
*/
        player.draw(graphicsContext);
        ui.draw(graphicsContext);
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.playSound();
        music.loopSound();
    }

    public void stopMusic() {
        music.stopSound();
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.playSound();
    }
}
