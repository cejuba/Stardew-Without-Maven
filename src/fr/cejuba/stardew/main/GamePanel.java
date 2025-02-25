package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Player;
import fr.cejuba.stardew.tile.TileManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends Canvas implements Runnable {

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

    int FPS = 10;


    // System config

    Thread gameThread;
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

    public GamePanel() {
        // System.out.println("GamePanel constructed - KeyHandler: " + keyHandler);
        this.setWidth(screenWidth);
        this.setHeight(screenHeight);
        this.setFocusTraversable(true);
        this.requestFocus();
        player.setKeyHandler(keyHandler);
    }


    public void setupGame(){
        assetSetter.setObject();
        assetSetter.setNPC();
        playMusic(0);
        stopMusic(); // Commenter pour mettre la musique
        gameState = titleState;
    }

    // Useless for now but can be in future

    /*public void setKeyHandler(KeyHandler keyHandler) {
        System.out.println("Setting KeyHandler instance in GamePanel: " + keyHandler);
        this.keyHandler = keyHandler;
    }*/
/*
    public void startGameThread() {
      gameThread = new Thread(this);
      gameThread.start();
    }
*/

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        for(int i=0; i<1000; i++) {
            update();
            System.out.println("Boucle passée" + gameThread.toString()); // TOSHOW
            draw();
        }

        /*
        while (gameThread.isAlive() ) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                draw();
                delta--;
            }
        }
         */
    }

    public void update() {

        if (gameState == playState){
            // Player
            player.update();

            // NPC
            for(Entity entity : npc){
                if(entity != null){
                    entity.update();
                }
            }
        }
        if (gameState == pauseState){
            // Nothing for now
        }

        if (gameState == dialogueState){
            // Nothing for now
        }

    }

    public void draw() {
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, screenWidth, screenHeight); // Clear the canvas

        // Debug
        long drawStartTime = 0;
        if(keyHandler.checkDrawTime){
            drawStartTime = System.nanoTime();
        }

        // Title Screen
        if(gameState==titleState){
            ui.draw(graphicsContext);
        }
        else {
            // Tile
            tileManager.draw(graphicsContext);

            // Add entities to the list
            entityList.add(player);
            for (int i = 0; i < npc.length; i++) {
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < objects.length; i++){
                if(objects[i] != null){
                    entityList.add(objects[i]);
                }
            }

            // Sort
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity entity1, Entity entity2) {
                    int result = Integer.compare(entity1.worldY, entity2.worldY);
                    return result;
                }
            });

            // Draw entities
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(graphicsContext);
            }

            // Empty entity list
            for(int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }


            // UI
            ui.draw(graphicsContext);
        }

        // Debug
        if(keyHandler.checkDrawTime){
            long drawEndTime = System.nanoTime();
            long passed = drawEndTime - drawStartTime;
            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillText("Draw Time:" + passed, 10, 400);
            System.out.println("Draw Time:" + passed);
        }
    }

    public void playMusic(int i){
        music.setFile(i);
        music.playSound();
        music.loopSound();
    }

    public void stopMusic(){
        music.stopSound();
    }

    public void playSoundEffect(int i){ // SoundEnvironnement
        soundEffect.setFile(i);
        soundEffect.playSound();
    }
}