package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.canvas.GraphicsContext;

public class InteractiveTile extends Entity {
    private final GamePanel gamePanel;
    private boolean destructible = false;

    public InteractiveTile(GamePanel gamePanel) {
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

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.getScreenX();
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.getScreenY();

        if(worldX + gamePanel.getTileSize() > gamePanel.player.worldX - gamePanel.player.getScreenX() &&
           worldX - gamePanel.getTileSize() < gamePanel.player.worldX + gamePanel.player.getScreenX() &&
           worldY + gamePanel.getTileSize() > gamePanel.player.worldY - gamePanel.player.getScreenY() &&
           worldY - gamePanel.getTileSize() < gamePanel.player.worldY + gamePanel.player.getScreenY()){

            graphicsContext.drawImage(down2, screenX, screenY);
        }
    }


    // Getters and Setters
    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }

    public boolean isDestructible() {
        return destructible;
    }
}
