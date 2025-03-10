package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.weapon.Axe;

public class Trunk extends InteractiveTile {
    public Trunk(GamePanel gamePanel, int column, int row) {
        super(gamePanel);

        down2 = setup("tile/interactive/trunk", gamePanel.getTileSize(), gamePanel.getTileSize());

        this.worldX = column * gamePanel.getTileSize();
        this.worldY = row * gamePanel.getTileSize();

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
