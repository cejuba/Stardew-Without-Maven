package fr.cejuba.stardew.object.projectile;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.Object;

public class Projectile extends Object {

    private GamePanel gamePanel;
    private Entity user;

    public Projectile(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void update(){

        if(user == gamePanel.player){
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            if(monsterIndex != 999){
                gamePanel.player.damageMonster(monsterIndex, attack, knockBackPower);
                generateParticle(user.projectile, gamePanel.monster[gamePanel.getCurrentMap()][monsterIndex]);
                alive = false;
            }
        }
        if(user != gamePanel.player){
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
            if(!gamePanel.player.invincible && contactPlayer){
                damagePlayer(attack);
                generateParticle(user.projectile, gamePanel.player);
                alive = false;
            }
        }

        switch (direction){
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        life--;
        if(life <= 0){
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNumber = (spriteNumber == 0) ? 0 : 1;
            spriteCounter = 0;
        }
    }

    public boolean haveRessource(Entity user){
        return false;
    }

    public void subtractResource(Entity user){
        user.mana -= useCost;
    }
}
