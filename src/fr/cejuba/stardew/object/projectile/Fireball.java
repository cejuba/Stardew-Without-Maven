package fr.cejuba.stardew.object.projectile;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Projectile;
import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.paint.Color;

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
        up1 = setup("projectile/fireball_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("projectile/fireball_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("projectile/fireball_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("projectile/fireball_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("projectile/fireball_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("projectile/fireball_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("projectile/fireball_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("projectile/fireball_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public boolean haveRessource(Entity user){
        return user.mana >= useCost;
    }

    public Color getParticleColor() {
        return Color.rgb(240, 50, 0);
    }

    public int getParticleSize() {
        return 10;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }
}
