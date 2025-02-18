package fr.cejuba.stardew.main;

import javafx.scene.shape.Rectangle;

public class EventHandler {
    GamePanel gamePanel;
    Rectangle eventRectangle;
    int eventRectangleDefaultX, eventRectangleDefaultY;
    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRectangle = new Rectangle();
        eventRectangle.setX(23);
        eventRectangle.setY(23);
        eventRectangle.setWidth(2);
        eventRectangle.setHeight(2);
        eventRectangleDefaultX = (int) eventRectangle.getX();
        eventRectangleDefaultY = (int) eventRectangle.getY();
    }

    public void checkEvent() {

        /*if(hit(27,16,"right")){
            damagePit(gamePanel.dialogueState);
        }*/
        if(hit(23,12,"up")){
            healingPool(gamePanel.dialogueState);
        }
        if(hit(27,16,"right")){
            teleport(gamePanel.dialogueState);
        }

    }
    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;
        gamePanel.player.solidArea.setX(gamePanel.player.worldX + gamePanel.player.solidArea.getX());
        gamePanel.player.solidArea.setY(gamePanel.player.worldY + gamePanel.player.solidArea.getY());
        eventRectangle.setX(eventCol * gamePanel.tileSize + eventRectangle.getX());
        eventRectangle.setY(eventRow * gamePanel.tileSize + eventRectangle.getY());

        if (gamePanel.player.solidArea.getBoundsInParent().intersects(eventRectangle.getBoundsInParent())) {
            if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }

        gamePanel.player.solidArea.setX(gamePanel.player.solidAreaDefaultX);
        gamePanel.player.solidArea.setY(gamePanel.player.solidAreaDefaultY);
        eventRectangle.setX(eventRectangleDefaultX);
        eventRectangle.setY(eventRectangleDefaultY);

        return hit;
    }

    public void teleport(int gameState){
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "Teleportation";
        gamePanel.player.worldX = gamePanel.tileSize*37;
        gamePanel.player.worldY = gamePanel.tileSize*10;
    }
    public void damagePit(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into a pit";
        gamePanel.player.life -= 1;
    }

    public void healingPool(int gameState) {
        if(gamePanel.keyHandler.enterPressed){
            gamePanel.gameState = gameState;
            gamePanel.ui.currentDialogue = "You heal";
            gamePanel.player.life += 1;
        }
    }
}
