package fr.cejuba.stardew.object.projectile;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.entity.Projectile;
import fr.cejuba.stardew.main.GamePanel;
import javafx.scene.paint.Color;

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

    public boolean haveRessource(Entity user){
        return user.ammo >= useCost; // TODO : change ammo to mana to have a spell
    }

    public void subtractRessource(Entity user){
        user.ammo -= useCost; // TODO : change ammo to mana to have a spell
    }

    public Color getParticleColor() {
        return Color.rgb(40,50,0);
    }

    public int getParticleSize() {
        return 10z;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }

}
