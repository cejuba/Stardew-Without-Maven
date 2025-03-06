package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.object.weapon.Axe;

public class DryTree extends InteractiveTile {
    GamePanel gamePanel;
    public DryTree(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        down1 = setup("tile/interactive/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon instanceof Axe;
    }
}
