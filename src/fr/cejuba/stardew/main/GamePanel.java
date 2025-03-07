package fr.cejuba.stardew.main;


// TODO : Check if player retry(); : is the items deleted or not (possible bug bcs can appear multiple times)
// TODO : Bug when a junimo is not killed and the player restart the game, the junimo is still with it's previous life

import fr.cejuba.stardew.AI.PathFinder;
import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Player;
import fr.cejuba.stardew.tile.TileManager;
import fr.cejuba.stardew.tile.interactive.InteractiveTile;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends Canvas {

    final int originalTileSize = 16;
    final int scale = 3;

    // Screen settings
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 27;
    public final int maxScreenRow = 15;
    public final int maxMap = 10;
    public int currentMap = 0;
    public final int screenWidth = maxScreenCol * tileSize; // 1296 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 720 pixels

    // Fullscreen settings
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;

    WritableImage tempScreen;
    GraphicsContext graphicsContext;
    public boolean fullScreenOn = false;
    private Stage stage;

    // World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    int FPS = 60;

    // System
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public EventHandler eventHandler = new EventHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    Config config = new Config(this);
    public UI ui = new UI(this);
    public PathFinder pathFinder = new PathFinder(this);

    // Entity and object
    public Player player = new Player(this, keyHandler);
    public Entity[][] object = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] interactiveTile = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // States
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    public GamePanel(Stage stage) {
        this.stage = stage;
        this.setFocusTraversable(true);
        this.requestFocus();
        player.setKeyHandler(keyHandler);

        // Initialize objects and NPCs
    }

    public void setupGame() {
        startGameLoop();
        basicAssetSetup();

        tempScreen = new WritableImage(screenWidth, screenHeight);
        graphicsContext = this.getGraphicsContext2D();
        if(fullScreenOn){
            setFullScreen();
        }
    }

    public void basicAssetSetup(){
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
    }

    public void retry(){
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        basicAssetSetup();
    }

    public void restart(){
        player.setDefaultValues(); // It also resets the player's position + life/mana
        player.setItems();

    }

    public void setFullScreen(){
        stage.setFullScreen(true);

        screenWidth2 = (int) stage.getWidth();
        screenHeight2 = (int) stage.getHeight();

    }

    private void startGameLoop() {
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
        AnimationTimer animationTimer = new AnimationTimer() {
            private final double drawInterval = (double) 1_000_000_000 / FPS;
            private double delta = 0;
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {

                long currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if (delta >= 1) {
                    if (gameState == playState) {
                        update();
                    }
                    drawTempScreen();
                    drawFinalScreen();
                    delta--;
                }
            }
        };
        animationTimer.start();
    }

    public void update() {
        player.update();
        for (int i = 0; i < npc[1].length; i++) { // [1] is because [0] is for the map
            if (npc[currentMap][i] != null) {
                npc[currentMap][i].update();
            }
        }
        for(int i = 0; i < monster[1].length; i++){ // [1] is because [0] is for the map
            if(monster[currentMap][i] != null){
                if(monster[currentMap][i].alive && !monster[currentMap][i].dying){
                    monster[currentMap][i].update();
                }
                if (!monster[currentMap][i].alive) {
                    monster[currentMap][i].checkDrop();
                    monster[currentMap][i] = null;
                }
            }
        }
        checkAliveUpdate(projectileList);

        checkAliveUpdate(particleList);

        for (int i = 0; i < interactiveTile[1].length; i++) {
            if (interactiveTile[currentMap][i] != null) {
                interactiveTile[currentMap][i].update();
            }
        }
    }

    private void checkAliveUpdate(ArrayList<Entity> arrayList) {
        for(int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i) != null){
                if(arrayList.get(i).alive){
                    arrayList.get(i).update();
                }
                if (!arrayList.get(i).alive) {
                    arrayList.remove(i);
                }
            }
        }
    }

    public void drawTempScreen(){
        long drawStartTime = 0;

        if(keyHandler.showDebugText){
            drawStartTime = System.nanoTime();
        }
        if(gameState==titleState){
            ui.draw(graphicsContext);
        }
        else{
            tileManager.draw(graphicsContext);

            for(int i = 0; i < interactiveTile[1].length; i++){
                if(interactiveTile[currentMap][i] != null){
                    interactiveTile[currentMap][i].draw(graphicsContext);
                }
            }

            entityList.add(player);

            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].draw(graphicsContext);
                }
            }
            for(int i = 0; i < object[1].length; i++){
                if(object[currentMap][i] != null){
                    object[currentMap][i].draw(graphicsContext);
                }
            }
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    monster[currentMap][i].draw(graphicsContext);
                }
            }
            for (Entity projectileList : projectileList) {
                if (projectileList != null) {
                    entityList.add(projectileList);
                }
            }

            for (Entity particleList : particleList) {
                if (particleList != null) {
                    entityList.add(particleList);
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

        if(keyHandler.showDebugText) {

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
            graphicsContext.fillText("Column: " + Math.round((player.worldX + player.solidArea.getX()) / tileSize), x, y);
            y += lineHeight;
            graphicsContext.fillText("Row: " + Math.round((player.worldY + player.solidArea.getY()) / tileSize), x, y);
            y += lineHeight;
            graphicsContext.fillText("Draw Time: " + drawTime / 1_000_000 + "ms", x, y);
            //System.out.println("Draw Time: " + drawTime / 1_000_000 + "ms");
        }
    }

    public void drawFinalScreen(){
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        graphicsContext.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2);
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
