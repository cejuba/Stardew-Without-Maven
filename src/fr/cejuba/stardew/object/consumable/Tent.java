package fr.cejuba.stardew.object.consumable;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GameState;
import fr.cejuba.stardew.main.Type;

public class Tent extends Consumable{

    private final GamePanel gamePanel;

    public Tent(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.CONSUMABLE);
        name = "Tent";
        down2 = setup("object/tent", gamePanel.getTileSize(), gamePanel.getTileSize());
        description = "[" + name + "]\nYou can sleep until next morning";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity){
        gamePanel.setGameState(GameState.SLEEP);
        gamePanel.playSoundEffect(14);
        gamePanel.player.life = gamePanel.player.maxLife;
        gamePanel.player.mana = gamePanel.player.maxMana;
        gamePanel.player.getSleepingImage(down2);
        return true; // TODO : Need to discuss if you need to have it every time but it means that this item as no value
    }
}
