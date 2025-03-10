package fr.cejuba.stardew.tile;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Map extends TileManager {

    private GamePanel gamePanel;
    private Image[] worldMap;
    private boolean miniMapActivated = false;

    public Map(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
        createWorldMap();
    }

    public void createWorldMap(){
        worldMap = new Image[gamePanel.maxMap];
        int worldMapWidth = gamePanel.tileSize * gamePanel.maxWorldCol;
        int worldMapHeight = gamePanel.tileSize * gamePanel.maxWorldRow;

        for(int i = 0; i < gamePanel.maxMap; i++){
            Canvas canvas = new Canvas(worldMapWidth, worldMapHeight);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

            int col = 0;
            int row = 0;

            while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
                int tileNumber = mapTileNumber[i][col][row];
                int x = gamePanel.tileSize * col;
                int y = gamePanel.tileSize * row;
                graphicsContext.drawImage(tiles[tileNumber].image, x, y);

                col++;
                if(col == gamePanel.maxWorldCol){
                    col = 0;
                    row++;
                }
            }

            WritableImage writableImage = new WritableImage(worldMapWidth, worldMapHeight);
            canvas.snapshot(null, writableImage);
            worldMap[i] = writableImage;
        }
    }

    public void drawFullMapScreen(GraphicsContext graphicsContext){

        // Background color
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Draw map
        int width = 500;
        int height = 500;
        int x = gamePanel.screenWidth / 2 - width / 2;
        int y = gamePanel.screenHeight / 2 - height / 2;
        graphicsContext.drawImage(worldMap[gamePanel.currentMap], x, y, width, height);

        // Calculate Scale
        double scale = (double)(gamePanel.tileSize * gamePanel.maxWorldCol) / width;

        // Draw player
        int playerX = (int)(x + gamePanel.player.worldX / scale);
        int playerY = (int)(y + gamePanel.player.worldY / scale);
        int playerSize = (int)(gamePanel.tileSize / scale);
        graphicsContext.drawImage(gamePanel.player.down1, playerX, playerY, playerSize, playerSize);

        // Objects
        for(Entity entity : gamePanel.object[gamePanel.currentMap]){
            if(entity != null){
                int entityX = (int)(x + entity.worldX / scale);
                int entityY = (int)(y + entity.worldY / scale);
                int entitySize = (int)(gamePanel.tileSize / scale);
                graphicsContext.drawImage(entity.image, entityX, entityY, entitySize, entitySize);
                graphicsContext.drawImage(entity.down1, entityX, entityY, entitySize, entitySize);
                graphicsContext.drawImage(entity.down2, entityX, entityY, entitySize, entitySize);
            }
        }
        // NPC
        for(Entity entity : gamePanel.npc[gamePanel.currentMap]){
            if(entity != null){
                int entityX = (int)(x + entity.worldX / scale);
                int entityY = (int)(y + entity.worldY / scale);
                int entitySize = (int)(gamePanel.tileSize / scale);
                graphicsContext.drawImage(entity.image, entityX, entityY, entitySize, entitySize);
                graphicsContext.drawImage(entity.down1, entityX, entityY, entitySize, entitySize);
                graphicsContext.drawImage(entity.down2, entityX, entityY, entitySize, entitySize);
            }
        }

        // Monster
        for(Entity entity : gamePanel.monster[gamePanel.currentMap]){
            if(entity != null){
                int entityX = (int)(x + entity.worldX / scale);
                int entityY = (int)(y + entity.worldY / scale);
                int entitySize = (int)(gamePanel.tileSize / scale);
                graphicsContext.drawImage(entity.image, entityX, entityY, entitySize, entitySize);
                graphicsContext.drawImage(entity.down1, entityX, entityY, entitySize, entitySize);
                graphicsContext.drawImage(entity.down2, entityX, entityY, entitySize, entitySize);
            }
        }



        // Hint
        graphicsContext.setFill(Color.WHITE);
        String text = "Press 'M' to close";
        int textX;
        int textY = gamePanel.screenHeight - gamePanel.tileSize;

        // Values
        int tailX = gamePanel.screenWidth - gamePanel.tileSize;
        textX = gamePanel.ui.getXAlignedToRightText(text, tailX);
        graphicsContext.fillText(text, textX, textY);
    }

    public void drawMiniMap(GraphicsContext graphicsContext){
        if(miniMapActivated) {
            // Draw map
            int width = 200;
            int height = 200;
            int x = gamePanel.screenWidth - width - gamePanel.tileSize;
            int y = gamePanel.tileSize;

            graphicsContext.setGlobalAlpha(0.8);
            graphicsContext.drawImage(worldMap[gamePanel.currentMap], x, y, width, height);

            // Calculate Scale
            double scale = (double) (gamePanel.tileSize * gamePanel.maxWorldCol) / width;

            // Draw player
            int playerX = (int) (x + gamePanel.player.worldX / scale);
            int playerY = (int) (y + gamePanel.player.worldY / scale);
            int playerSize = (int) (gamePanel.tileSize / scale);
            graphicsContext.drawImage(gamePanel.player.down1, playerX, playerY, playerSize, playerSize);

            // Objects
            for (Entity entity : gamePanel.object[gamePanel.currentMap]) {
                if (entity != null) {
                    int entityX = (int) (x + entity.worldX / scale);
                    int entityY = (int) (y + entity.worldY / scale);
                    int entitySize = (int) (gamePanel.tileSize / scale);
                    graphicsContext.drawImage(entity.image, entityX, entityY, entitySize, entitySize);
                    graphicsContext.drawImage(entity.down1, entityX, entityY, entitySize, entitySize);
                    graphicsContext.drawImage(entity.down2, entityX, entityY, entitySize, entitySize);
                }
            }
            // NPC
            for (Entity entity : gamePanel.npc[gamePanel.currentMap]) {
                if (entity != null) {
                    int entityX = (int) (x + entity.worldX / scale);
                    int entityY = (int) (y + entity.worldY / scale);
                    int entitySize = (int) (gamePanel.tileSize / scale);
                    graphicsContext.drawImage(entity.image, entityX, entityY, entitySize, entitySize);
                    graphicsContext.drawImage(entity.down1, entityX, entityY, entitySize, entitySize);
                    graphicsContext.drawImage(entity.down2, entityX, entityY, entitySize, entitySize);
                }
            }

            // Monster
            for (Entity entity : gamePanel.monster[gamePanel.currentMap]) {
                if (entity != null) {
                    int entityX = (int) (x + entity.worldX / scale);
                    int entityY = (int) (y + entity.worldY / scale);
                    int entitySize = (int) (gamePanel.tileSize / scale);
                    graphicsContext.drawImage(entity.image, entityX, entityY, entitySize, entitySize);
                    graphicsContext.drawImage(entity.down1, entityX, entityY, entitySize, entitySize);
                    graphicsContext.drawImage(entity.down2, entityX, entityY, entitySize, entitySize);
                }
            }

            graphicsContext.setGlobalAlpha(1);
        }
    }

    // Getters and setters
    public boolean isMiniMapActivated() {
        return miniMapActivated;
    }
    public void setMiniMapActivated(boolean miniMapActivated) {
        this.miniMapActivated = miniMapActivated;
    }
}