package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;

public class EventHandler {
    GamePanel gamePanel;
    EventRectangle[][][] eventRectangle;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.eventRectangle = new EventRectangle[gamePanel.maxMap][gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while(map < gamePanel.maxMap && col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow){
                    eventRectangle[map][col][row] = new EventRectangle();
                    eventRectangle[map][col][row].initialize(23, 23, 2, 2);
            col++;
            if(col == gamePanel.maxWorldCol){
                col = 0;
                row++;
                if(row == gamePanel.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gamePanel.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0,23, 12, "up")) {
                healingPool();
            } else if (hit(0,30,12, "any")){
                teleport(1,12,13);
            } else if (hit(1,12,13, "any")){
                teleport(0,30,12);
            } else if (hit(1,12,9,"up")){
                speak(gamePanel.npc[1][0]); // speak to merchant
            }
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;
        if(map == gamePanel.currentMap){
            gamePanel.player.solidArea.setX(gamePanel.player.worldX + gamePanel.player.solidArea.getX());
            gamePanel.player.solidArea.setY(gamePanel.player.worldY + gamePanel.player.solidArea.getY());
            EventRectangle eventRect = eventRectangle[map][col][row];
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
        }
        return hit;
    }

    public void teleport(int map, int col, int row) {

        gamePanel.setGameState(GameState.TRANSITION);
        tempMap = map;
        tempCol = col;
        tempRow = row;

        /*
        gamePanel.currentMap = map;
        gamePanel.player.worldX = col * gamePanel.tileSize;
        gamePanel.player.worldY = row * gamePanel.tileSize;
        previousEventX = gamePanel.player.worldX;
        previousEventY = gamePanel.player.worldY;
        */
        canTouchEvent = false;
        gamePanel.playSoundEffect(13);
    }

    public void damagePit() {
        gamePanel.playSoundEffect(6);
        gamePanel.ui.currentDialogue = "You fall into a pit";
        gamePanel.player.life -= 1;
        canTouchEvent = false;
    }

    public void healingPool() {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.player.attackCanceled = true;
            gamePanel.playSoundEffect(2);
            gamePanel.ui.currentDialogue = "You heal and regenerate mana";
            gamePanel.player.life = gamePanel.player.maxLife;
            gamePanel.player.mana = gamePanel.player.maxMana;
            gamePanel.assetSetter.setMonster();
        }
    }

    public void speak(Entity entity){
        if(gamePanel.keyHandler.enterPressed){
            gamePanel.setGameState(GameState.DIALOGUE);
            gamePanel.player.attackCanceled = true;
            entity.speak();
        }
    }
}
