package fr.cejuba.stardew.main;


// TODO : Check if player retry(); : is the items deleted or not (possible bug bcs can appear multiple times)
// TODO : Bug when a junimo is not killed and the player restart the game, the junimo is still with it's previous life
// TODO : Map are darker at night / impossible ?
// TODO : Heart can be taken in the inventory

import fr.cejuba.stardew.AI.PathFinder;
import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Player;
import fr.cejuba.stardew.environment.EnvironmentManager;
import fr.cejuba.stardew.tile.Map;
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

    private final int originalTileSize = 16;
    private final int scale = 3;

    // Screen settings
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 27;
    private final int maxScreenRow = 15;
    private final int maxMap = 10;
    private int currentMap = 0;
    private final int screenWidth = maxScreenCol * tileSize; // 1296 pixels
    private final int screenHeight = maxScreenRow * tileSize; // 720 pixels

    // Fullscreen settings
    private int screenWidth2 = screenWidth;
    private int screenHeight2 = screenHeight;

    private WritableImage tempScreen;
    private GraphicsContext graphicsContext;
    public boolean fullScreenOn = false;
    private final Stage stage;

    // World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    private final int FPS = 60;

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
    EnvironmentManager environmentManager = new EnvironmentManager(this);
    Map map = new Map(this);

    // Entity and object
    public Player player = new Player(this, keyHandler);
    public Entity[][] object = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] interactiveTile = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();

    // States
    private GameState gameState = GameState.TITLE;

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
        environmentManager.setup();

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
                    if (gameState == GameState.PLAY) {
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
        environmentManager.update();
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
        for(int i = 0; i < projectile[1].length; i++){
            if(projectile[currentMap][i] != null){
                if(projectile[currentMap][i].alive){
                    projectile[currentMap][i].update();
                }
                if (!projectile[currentMap][i].alive) {
                    projectile[currentMap][i] = null;
                }
            }
        }

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

        // Debug
        long drawStartTime = 0;
        if(keyHandler.isShowDebugText()){
            drawStartTime = System.nanoTime();
        }

        // Title Screen
        if(gameState==GameState.TITLE){
            ui.draw(graphicsContext);
        }
        // Map Screen
        else if(gameState==GameState.MAP){
            map.drawFullMapScreen(graphicsContext);
        }
        // Others
        else{
            tileManager.draw(graphicsContext);

            for(int i = 0; i < interactiveTile[1].length; i++){
                if(interactiveTile[currentMap][i] != null){
                    // interactiveTile[currentMap][i].draw(graphicsContext);
                    entityList.add(interactiveTile[currentMap][i]);
                }
            }

            entityList.add(player);

            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    // npc[currentMap][i].draw(graphicsContext);
                    entityList.add(npc[currentMap][i]);
                }
            }
            for(int i = 0; i < object[1].length; i++){
                if(object[currentMap][i] != null){
                    // object[currentMap][i].draw(graphicsContext);
                    entityList.add(object[currentMap][i]);
                }
            }
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    // monster[currentMap][i].draw(graphicsContext);
                    entityList.add(monster[currentMap][i]);
                }
            }
            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    // projectile[currentMap][i].draw(graphicsContext);
                    entityList.add(projectile[currentMap][i]);
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

            environmentManager.draw(graphicsContext);

            map.drawMiniMap(graphicsContext);

            ui.draw(graphicsContext);
        }
        // TODO: Gestion d'affichage l'un au dessus de l'autre

        if(keyHandler.isShowDebugText()) {

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

    // Getter Setter

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxMap() {
        return maxMap;
    }

    public int getCurrentMap() {
        return currentMap;
    }
    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
}
