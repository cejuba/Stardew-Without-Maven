package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Player;
import fr.cejuba.stardew.entity.Projectile;
import fr.cejuba.stardew.tile.TileManager;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
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
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

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
                if(monster[i].alive && !monster[i].dying){
                    monster[i].update();
                }
                if (!monster[i].alive) {
                    monster[i] = null;
                }
            }
        }
        for(int i = 0; i < projectileList.size(); i++){
            if(projectileList.get(i) != null){
                if(projectileList.get(i).alive){
                    projectileList.get(i).update();
                }
                if (!projectileList.get(i).alive) {
                    projectileList.remove(i);
                }
            }
        }
    }

    public void draw() {
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());

        long drawStartTime = 0;

        if(keyHandler.showDebugText){
            drawStartTime = System.nanoTime();
        }
        if(gameState==titleState){
            ui.draw(graphicsContext);
        }
        else{
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

            for (Entity value : projectileList) {
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

            ui.draw(graphicsContext);
        }
        // TODO: Gestion d'affichage l'un au dessus de l'autre

        if(keyHandler.showDebugText){

            // Became Obsolete because of the new draw method in a separate thread TODO: Change the debug text to the new draw method
            long drawEndTime = System.nanoTime();
            long drawTime = drawEndTime - drawStartTime;

            graphicsContext.setFill(Color.WHITE);
            graphicsContext.setFont(new Font("Arial", 20));
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            graphicsContext.fillText("WorldX: " + player.worldX, x, y);
            y += lineHeight;
            graphicsContext.fillText("WorldY: " + player.worldY, x, y);
            y += lineHeight;
            graphicsContext.fillText("Column: " + Math.round((player.worldX + player.solidArea.getX())/tileSize), x, y);
            y += lineHeight;
            graphicsContext.fillText("Row: " + Math.round((player.worldY + player.solidArea.getY())/tileSize), x, y);
            y += lineHeight;
            graphicsContext.fillText("Draw Time: " + drawTime / 1_000_000 + "ms", x, y);
            //System.out.println("Draw Time: " + drawTime / 1_000_000 + "ms");

        }
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
