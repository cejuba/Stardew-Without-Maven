package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Player;
import fr.cejuba.stardew.object.SuperObject;
import fr.cejuba.stardew.tile.TileManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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




    int FPS = 60;


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
    public SuperObject[] superObject = new SuperObject[10]; // If we want to increase the number of object we can do it here, but careful about performances;
    public Entity[] npc =  new Entity[10];


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

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread.isAlive()) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                draw();
                delta--;
            }
        }
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

            // Object
            for (SuperObject object : superObject) {
                if (object != null) {
                    object.draw(graphicsContext, this);
                }
            }

            // NPC
            for(Entity entity : npc){
                if(entity != null){
                    entity.draw(graphicsContext);
                }
            }

            // Player
            player.draw(graphicsContext);

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