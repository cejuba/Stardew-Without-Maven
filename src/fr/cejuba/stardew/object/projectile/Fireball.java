package fr.cejuba.stardew.object.projectile;

import fr.cejuba.stardew.entity.Projectile;
import fr.cejuba.stardew.main.GamePanel;

public class Fireball extends Projectile {

    GamePanel gamePanel;

    public Fireball(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Fireball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up0 = setup("projectile/fireball_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("projectile/fireball_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down0 = setup("projectile/fireball_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("projectile/fireball_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left0 = setup("projectile/fireball_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("projectile/fireball_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right0 = setup("projectile/fireball_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("projectile/fireball_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }
}
