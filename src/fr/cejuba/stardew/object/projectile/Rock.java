package fr.cejuba.stardew.object.projectile;

import fr.cejuba.stardew.entity.Projectile;
import fr.cejuba.stardew.main.GamePanel;

public class Rock extends Projectile {
    GamePanel gamePanel;


    public Rock(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up0 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down0 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left0 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right0 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("projectile/rock_down_1", gamePanel.tileSize, gamePanel.tileSize);
    }
}
