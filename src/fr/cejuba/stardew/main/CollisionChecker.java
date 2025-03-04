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
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision) {
                    entity.collisionActivated = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision) {
                    entity.collisionActivated = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision) {
                    entity.collisionActivated = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision) {
                    entity.collisionActivated = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.objects.length; i++) {
            Entity object = gamePanel.objects[i];
            if (object != null) {
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                object.solidArea.setX(object.worldX + object.solidArea.getX());
                object.solidArea.setY(object.worldY + object.solidArea.getY());

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(object.solidArea.getBoundsInParent())) {
                            if (object.collision) {
                                entity.collisionActivated = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(object.solidArea.getBoundsInParent())) {
                            if (object.collision) {
                                entity.collisionActivated = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(object.solidArea.getBoundsInParent())) {
                            if (object.collision) {
                                entity.collisionActivated = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(object.solidArea.getBoundsInParent())) {
                            if (object.collision) {
                                entity.collisionActivated = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                object.solidArea.setX(object.solidAreaDefaultX);
                object.solidArea.setY(object.solidAreaDefaultY);
            }
        }
        return index;
    }



    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            Entity targetEntity = target[i];
            if (targetEntity != null) {
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                targetEntity.solidArea.setX(targetEntity.worldX + targetEntity.solidArea.getX());
                targetEntity.solidArea.setY(targetEntity.worldY + targetEntity.solidArea.getY());

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(targetEntity.solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(targetEntity.solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(targetEntity.solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(targetEntity.solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                }
                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                targetEntity.solidArea.setX(targetEntity.solidAreaDefaultX);
                targetEntity.solidArea.setY(targetEntity.solidAreaDefaultY);
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity){
        entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
        entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

        // Get the object's solidArea position
        gamePanel.player.solidArea.setX(gamePanel.player.worldX + gamePanel.player.solidArea.getX());
        gamePanel.player.solidArea.setY(gamePanel.player.worldY + gamePanel.player.solidArea.getY());

        switch (entity.direction){
            case "up" :
                entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                if (entity.solidArea.getBoundsInParent().intersects(gamePanel.player.solidArea.getBoundsInParent())) {
                    entity.collisionActivated = true;
                }
                break;
            case "down" :
                entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                if (entity.solidArea.getBoundsInParent().intersects(gamePanel.player.solidArea.getBoundsInParent())) {
                    entity.collisionActivated = true;
                }
                break;
            case "left" :
                entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                if (entity.solidArea.getBoundsInParent().intersects(gamePanel.player.solidArea.getBoundsInParent())) {
                    entity.collisionActivated = true;
                }
                break;
            case "right" :
                entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                if (entity.solidArea.getBoundsInParent().intersects(gamePanel.player.solidArea.getBoundsInParent())) {
                    entity.collisionActivated = true;
                }
                break;
        }
        entity.solidArea.setX(entity.solidAreaDefaultX);
        entity.solidArea.setY(entity.solidAreaDefaultY);
        gamePanel.player.solidArea.setX(gamePanel.player.solidAreaDefaultX);
        gamePanel.player.solidArea.setY(gamePanel.player.solidAreaDefaultY);
    }
}
