package fr.cejuba.stardew.tile.interactive;

import fr.cejuba.stardew.main.GamePanel;

public class DryTree extends InteractiveTile {
    GamePanel gamePanel;
    public DryTree(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        down1 = setup("tile/interactive/drytree", gamePanel.tileSize, gamePanel.tileSize);
        destructible = true;
    }
}
