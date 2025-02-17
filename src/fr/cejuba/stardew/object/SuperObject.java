package fr.cejuba.stardew.object;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.UtilityTool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class SuperObject {

    public Image image;
    public String name;
    public boolean collision;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0, 48,48); // No scale with tilesize : be careful
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool utilityTool = new UtilityTool();

    public void draw(GraphicsContext graphicsContext, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (    worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX  &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ) {
            graphicsContext.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
        }
    }
}
