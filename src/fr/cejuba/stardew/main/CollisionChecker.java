package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.getX());
        int entityRightWorldX = (int) (entity.worldX + entity.solidArea.getX() + entity.solidArea.getWidth());
        int entityTopWorldY = (int) (entity.worldY + entity.solidArea.getY());
        int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.getY() + entity.solidArea.getHeight());

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNumber1, tileNumber2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tiles[tileNumber1].isCollision() || gamePanel.tileManager.tiles[tileNumber2].isCollision()) {
                    entity.collisionActivated = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].isCollision() || gamePanel.tileManager.tiles[tileNumber2].isCollision()) {
                    entity.collisionActivated = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].isCollision() || gamePanel.tileManager.tiles[tileNumber2].isCollision()) {
                    entity.collisionActivated = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].isCollision() || gamePanel.tileManager.tiles[tileNumber2].isCollision()) {
                    entity.collisionActivated = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.object[1].length; i++) {
            Entity object = gamePanel.object[gamePanel.currentMap][i];
            if (object != null) {

                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                object.solidArea.setX(object.worldX + object.solidArea.getX());
                object.solidArea.setY(object.worldY + object.solidArea.getY());

                switch (entity.direction){
                    case "up" :
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        break;
                    case "down" :
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        break;
                    case "left" :
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        break;
                    case "right" :
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        break;
                }
                if (entity.solidArea.getBoundsInParent().intersects(object.solidArea.getBoundsInParent())) {
                    if (object.collision) {
                        entity.collisionActivated = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                object.solidArea.setX(object.solidAreaDefaultX);
                object.solidArea.setY(object.solidAreaDefaultY);
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for (int i = 0; i < target[1].length; i++) {
            Entity targetEntity = target[gamePanel.currentMap][i];
            if (targetEntity != null) {
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                target[gamePanel.currentMap][i].solidArea.setX(targetEntity.worldX + targetEntity.solidArea.getX());
                target[gamePanel.currentMap][i].solidArea.setY(targetEntity.worldY + targetEntity.solidArea.getY());

                switch (entity.direction){
                    case "up" :
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        break;
                    case "down" :
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        break;
                    case "left" :
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        break;
                    case "right" :
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        break;
                }

                if (entity.solidArea.getBoundsInParent().intersects(targetEntity.solidArea.getBoundsInParent())) {
                    if (target[gamePanel.currentMap][i] != entity) {
                        entity.collisionActivated = true;
                        index = i;
                    }
                }
                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                targetEntity.solidArea.setX(targetEntity.solidAreaDefaultX);
                targetEntity.solidArea.setY(targetEntity.solidAreaDefaultY);
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
        entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

        // Get the object's solidArea position
        gamePanel.player.solidArea.setX(gamePanel.player.worldX + gamePanel.player.solidArea.getX());
        gamePanel.player.solidArea.setY(gamePanel.player.worldY + gamePanel.player.solidArea.getY());

        switch (entity.direction){
            case "up" :
                entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                break;
            case "down" :
                entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                break;
            case "left" :
                entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                break;
            case "right" :
                entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                break;
        }
        if (entity.solidArea.getBoundsInParent().intersects(gamePanel.player.solidArea.getBoundsInParent())) {
            entity.collisionActivated = true;
            contactPlayer = true;
        }

        entity.solidArea.setX(entity.solidAreaDefaultX);
        entity.solidArea.setY(entity.solidAreaDefaultY);
        gamePanel.player.solidArea.setX(gamePanel.player.solidAreaDefaultX);
        gamePanel.player.solidArea.setY(gamePanel.player.solidAreaDefaultY);

        return contactPlayer;
    }

}
