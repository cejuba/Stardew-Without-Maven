package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.weapon.Axe;
import javafx.scene.paint.Color;

public class DryTree extends InteractiveTile {
    GamePanel gamePanel;
    public DryTree(GamePanel gamePanel, int column, int row) {
        super(gamePanel, column, row);
        this.gamePanel = gamePanel;

        down1 = setup("tile/interactive/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
        life = 3;

        this.worldX = column;
        this.worldY = row;
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon instanceof Axe;
    }

    public void playSoundEnvironment() {
        gamePanel.playSoundEffect(11);
    }

    public InteractiveTile getDestroyedForm(){
        return new Trunk(gamePanel, worldX / gamePanel.tileSize, worldY / gamePanel.tileSize);
    }

    public Color getParticleColor() {
        return Color.rgb(65,50,30);
    }

    public int getParticleSize() {
        return 6;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }
}
