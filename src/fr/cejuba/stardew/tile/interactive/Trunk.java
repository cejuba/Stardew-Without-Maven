package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.weapon.Axe;

public class Trunk extends InteractiveTile {
    GamePanel gamePanel;
    public Trunk(GamePanel gamePanel, int column, int row) {
        super(gamePanel, column, row);
        this.gamePanel = gamePanel;

        down2 = setup("tile/interactive/trunk", gamePanel.tileSize, gamePanel.tileSize);

        this.worldX = column * gamePanel.tileSize;
        this.worldY = row * gamePanel.tileSize;

        solidArea.setX(0);
        solidArea.setY(0);
        solidArea.setWidth(0);
        solidArea.setHeight(0);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon instanceof Axe;
    }
}
