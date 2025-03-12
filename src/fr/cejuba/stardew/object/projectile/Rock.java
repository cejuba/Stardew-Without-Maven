package fr.cejuba.stardew.object.projectile;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.paint.Color;

public class Rock extends Projectile {
    private final GamePanel gamePanel;


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
        up1 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        up2 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down1 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        down2 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        left1 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        left2 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        right1 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
        right2 = setup("projectile/rock_down_1", gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    public boolean haveRessource(Entity user){
        return user.ammo >= useCost; // TODO : change ammo to mana to have a spell
    }

    public void subtractResource(Entity user){
        user.ammo -= useCost; // TODO : change ammo to mana to have a spell
    }

    public Color getParticleColor() {
        return Color.rgb(40,50,0);
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
