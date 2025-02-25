package fr.cejuba.stardew.main;

public class EventHandler {
    GamePanel gamePanel;
    EventRectangle[][] eventRectangle;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.eventRectangle = new EventRectangle[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        for (int col = 0; col < gamePanel.maxWorldCol; col++) {
            for (int row = 0; row < gamePanel.maxWorldRow; row++) {
                eventRectangle[col][row] = new EventRectangle();
                eventRectangle[col][row].initialize(23, 23, 2, 2);
            }
        }
    }

    public void checkEvent() {

        // Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gamePanel.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent) {
            if (hit(23, 12, "up")) {
                healingPool(23,12,gamePanel.dialogueState);
            }
            if (hit(27, 16, "right")) {
                damagePit(27,16,gamePanel.dialogueState);
            }
            if (hit(23, 19, "any")) {
                damagePit(23,19,gamePanel.dialogueState);
            }
        }
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;
        gamePanel.player.solidArea.setX(gamePanel.player.worldX + gamePanel.player.solidArea.getX());
        gamePanel.player.solidArea.setY(gamePanel.player.worldY + gamePanel.player.solidArea.getY());
        EventRectangle eventRect = eventRectangle[col][row];
        eventRect.setX(col * gamePanel.tileSize + eventRect.eventRectangleDefaultX);
        eventRect.setY(row * gamePanel.tileSize + eventRect.eventRectangleDefaultY);

        if (gamePanel.player.solidArea.getBoundsInParent().intersects(eventRect.getBoundsInParent()) && !eventRect.eventDone) {
            if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;
            }
        }

        gamePanel.player.solidArea.setX(gamePanel.player.solidAreaDefaultX);
        gamePanel.player.solidArea.setY(gamePanel.player.solidAreaDefaultY);
        eventRect.setX(eventRect.eventRectangleDefaultX);
        eventRect.setY(eventRect.eventRectangleDefaultY);

        return hit;
    }

    public void teleport(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "Teleportation";
        gamePanel.player.worldX = gamePanel.tileSize * 37;
        gamePanel.player.worldY = gamePanel.tileSize * 10;
    }

    public void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into a pit";
        gamePanel.player.life -= 1;
        // eventRectangle[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row, int gameState) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameState;
            gamePanel.ui.currentDialogue = "You heal";
            gamePanel.player.life += 1;
        }
    }
}
