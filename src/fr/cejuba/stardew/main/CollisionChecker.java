package fr.cejuba.stardew.main;

import fr.cejuba.stardew.entity.Entity;

import javax.xml.stream.events.EndElement;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {

         // Here we need to check the 4 corner of our collision box to be sure when it'll encounter a tile it can't pass

        int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.getX());
        int entityRightWorldX = (int) (entity.worldX + entity.solidArea.getX() + entity.solidArea.getWidth());
        int entityTopWorldY = (int) (entity.worldY + entity.solidArea.getY());
        int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.getY() + entity.solidArea.getHeight());

        int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
        int entityRightCol = entityRightWorldX/gamePanel.tileSize;
        int entityTopRow = entityTopWorldY/gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;

        // We only two tiles for each direction
        int tileNumber1, tileNumber2;

        switch (entity.direction){
            case "up" :
                entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision){ // If the player hit the two tile on top of him he can't move so we activate the collision
                    entity.collisionActivated = true;
                }
                break;
            case "down" :
                entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision){
                    entity.collisionActivated = true;
                }
                break;
            case "left" :
                entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision){
                    entity.collisionActivated = true;
                }
                break;
            case "right" :
                entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
                tileNumber1 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNumber1].collision || gamePanel.tileManager.tiles[tileNumber2].collision){
                    entity.collisionActivated = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for(int i = 0; i < gamePanel.superObject.length; i++){
            if(gamePanel.superObject[i] != null){
                // Get entity's solidArea position
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                // Get the object's solidArea position
                gamePanel.superObject[i].solidArea.setX(gamePanel.superObject[i].worldX + gamePanel.superObject[i].solidArea.getX());
                gamePanel.superObject[i].solidArea.setY(gamePanel.superObject[i].worldY + gamePanel.superObject[i].solidArea.getY());

                switch (entity.direction){
                    case "up" :
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(gamePanel.superObject[i].solidArea.getBoundsInParent())) {
                            if(gamePanel.superObject[i].collision){
                                entity.collisionActivated = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "down" :
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(gamePanel.superObject[i].solidArea.getBoundsInParent())) {
                            if(gamePanel.superObject[i].collision){
                                entity.collisionActivated = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "left" :
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(gamePanel.superObject[i].solidArea.getBoundsInParent())) {
                            if(gamePanel.superObject[i].collision){
                                entity.collisionActivated = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "right" :
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(gamePanel.superObject[i].solidArea.getBoundsInParent())) {
                            if(gamePanel.superObject[i].collision){
                                entity.collisionActivated = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                gamePanel.superObject[i].solidArea.setX(gamePanel.superObject[i].solidAreaDefaultX);
                gamePanel.superObject[i].solidArea.setY(gamePanel.superObject[i].solidAreaDefaultY);
            }
        }
        return index;

    }

    // If the player hit a Monster/NPC
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        for(int i = 0; i < target.length; i++){
            if(target[i] != null){
                // Get entity's solidArea position
                entity.solidArea.setX(entity.worldX + entity.solidArea.getX());
                entity.solidArea.setY(entity.worldY + entity.solidArea.getY());

                // Get the object's solidArea position
                target[i].solidArea.setX(target[i].worldX + target[i].solidArea.getX());
                target[i].solidArea.setY(target[i].worldY + target[i].solidArea.getY());

                switch (entity.direction){
                    case "up" :
                        entity.solidArea.setY(entity.solidArea.getY() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                    case "down" :
                        entity.solidArea.setY(entity.solidArea.getY() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                    case "left" :
                        entity.solidArea.setX(entity.solidArea.getX() - entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                    case "right" :
                        entity.solidArea.setX(entity.solidArea.getX() + entity.speed);
                        if (entity.solidArea.getBoundsInParent().intersects(target[i].solidArea.getBoundsInParent())) {
                            entity.collisionActivated = true;
                            index = i;
                        }
                        break;
                }
                entity.solidArea.setX(entity.solidAreaDefaultX);
                entity.solidArea.setY(entity.solidAreaDefaultY);
                target[i].solidArea.setX(target[i].solidAreaDefaultX);
                target[i].solidArea.setY(target[i].solidAreaDefaultY);
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
