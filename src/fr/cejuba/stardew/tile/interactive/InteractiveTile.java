package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.GraphicsContext;

public class InteractiveTile extends Entity {
    GamePanel gamePanel;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gamePanel, int column, int row) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    public boolean isCorrectItem(Entity entity) {
        return false;
    }

    public void playSoundEnvironment(){
    }

    public InteractiveTile getDestroyedForm(){
        return null;
    }

    public void update(){
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(GraphicsContext graphicsContext) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
           worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
           worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
           worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY ){

            graphicsContext.drawImage(down1, screenX, screenY);
        }
    }
}
