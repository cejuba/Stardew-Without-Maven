package fr.cejuba.stardew.main;

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


    Thread gameThread;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, keyHandler);
    public SuperObject[] superObject = new SuperObject[10]; // If we want to increase the number of object we can do it here, but careful about performances;

    public UI ui = new UI(this);



    public GamePanel(KeyHandler keyHandler) {
        // System.out.println("GamePanel constructed - KeyHandler: " + keyHandler);
        this.setWidth(screenWidth);
        this.setHeight(screenHeight);
        this.setFocusTraversable(true);
        this.requestFocus();
        player.setKeyHandler(keyHandler);
    }

    public void setupGame(){
        assetSetter.setObject();
        playMusic(0);
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

        player.update();

    }

    public void draw() {
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, screenWidth, screenHeight); // Clear the canvas

        // Debug
        long drawStartTime = 0;
        if(keyHandler.checkDrawTime){
            drawStartTime = System.nanoTime();
        }

        tileManager.draw(graphicsContext);

        for (SuperObject object : superObject) {
            if (object != null) {
                object.draw(graphicsContext, this);
            }
        }
        // Player
        player.draw(graphicsContext);

        // UI
        ui.draw(graphicsContext);

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