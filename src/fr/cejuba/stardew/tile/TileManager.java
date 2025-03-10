package fr.cejuba.stardew.tile;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.UtilityTool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][][] mapTileNumber; // First dimension is for the layer/map, second is for the column, third is for the row
    // boolean drawPath = true;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[50]; // number of tiles present in the game
        mapTileNumber = new int[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("fr/cejuba/stardew/maps/worldV2.txt", 0);
        loadMap("fr/cejuba/stardew/maps/interior01.txt", 1);
    }

    public void getTileImage() {
        try {
            setup(0, "grass00", false);
            setup(1, "grass00", false);
            setup(2, "grass00", false);
            setup(3, "grass00", false);
            setup(4, "grass00", false);
            setup(5, "grass00", false);
            setup(6, "grass00", false);
            setup(7, "grass00", false);
            setup(8, "grass00", false);
            setup(9, "grass00", false);

            setup(10, "grass00", false);
            setup(11, "grass01", false);
            setup(12, "water00", true);
            setup(13, "water01", true);
            setup(14, "water02", true);
            setup(15, "water03", true);
            setup(16, "water04", true);
            setup(17, "water05", true);
            setup(18, "water06", true);
            setup(19, "water07", true);
            setup(20, "water08", true);
            setup(21, "water09", true);
            setup(22, "water10", true);
            setup(23, "water11", true);
            setup(24, "water12", true);
            setup(25, "water13", true);
            setup(26, "road00", false);
            setup(27, "road01", false);
            setup(28, "road02", false);
            setup(29, "road03", false);
            setup(30, "road04", false);
            setup(31, "road05", false);
            setup(32, "road06", false);
            setup(33, "road07", false);
            setup(34, "road08", false);
            setup(35, "road09", false);
            setup(36, "road10", false);
            setup(37, "road11", false);
            setup(38, "road12", false);
            setup(39, "earth", false);
            setup(40, "wall", true);
            setup(41, "tree", true);
            setup(42, "hut", false);
            setup(43, "floor01", false);
            setup(44, "table01", true);

        } catch (Exception e) {
            System.out.println("Error loading tile image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();
        try {
            tiles[index] = new Tile();
            InputStream is = getClass().getResourceAsStream("/fr/cejuba/stardew/tile/" + imageName + ".png");
            if (is == null) {
                System.out.println("Resource not found: /fr/cejuba/stardew/tile/" + imageName + ".png");
                throw new RuntimeException("Resource not found: " + imageName);
            }
            tiles[index].image = new Image(is);
            tiles[index].image = utilityTool.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].setCollision(collision);
        } catch (Exception e) {
            System.out.println("Error setting up tile: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();
                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[col]);
                    mapTileNumber[map][col][row] = number;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error loading map: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNumber = mapTileNumber[gamePanel.currentMap][worldCol][worldRow];

            if (tileNumber >= tiles.length || tiles[tileNumber] == null) {
                System.out.println("Invalid tile number or uninitialized tile: " + tileNumber);
                worldCol++;
                if (worldCol == gamePanel.maxWorldCol) {
                    worldCol = 0;
                    worldRow++;
                }
                continue;
            }

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                graphicsContext.drawImage(tiles[tileNumber].image, screenX, screenY);
            }
            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        /*
        if(drawPath){
            graphicsContext.setFill(Color.rgb(255, 0, 0, 0.5));

            for(int i = 0; i < gamePanel.pathFinder.pathList.size(); i++){
                int worldX = gamePanel.pathFinder.pathList.get(i).col * gamePanel.tileSize;
                int worldY = gamePanel.pathFinder.pathList.get(i).row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                graphicsContext.fillRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
            }
        }*/
    }
}
