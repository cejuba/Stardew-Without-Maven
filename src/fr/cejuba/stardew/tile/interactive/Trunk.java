package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.weapon.Axe;

public class Trunk extends InteractiveTile {
    GamePanel gamePanel;
    public Trunk(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        down1 = setup("tile/interactive/trunk", gamePanel.tileSize, gamePanel.tileSize);

        solidArea.setX(0);
        solidArea.setY(0);
        solidArea.setWidth(gamePanel.tileSize);
        solidArea.setHeight(gamePanel.tileSize);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon instanceof Axe;
    }
}
