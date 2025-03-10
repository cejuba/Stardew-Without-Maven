package fr.cejuba.stardew.object;

import fr.cejuba.stardew.entity.Entity;
import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.GameState;
import fr.cejuba.stardew.main.Type;

public class Key extends Entity {

    private final GamePanel gamePanel;

    public Key(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.CONSUMABLE);
        name = "Key";
        down2 = setup("/object/key", gamePanel.getTileSize(), gamePanel.getTileSize());

        description = "[" + name + "]\n" + "It opens a door.";

        price = 100;
        stackable = true;
    }

    public boolean use(Entity entity) {

        gamePanel.setGameState(GameState.DIALOGUE);
        int objIndex = getDetected(entity, gamePanel.object, "Door");

        if(objIndex != 999) {
            gamePanel.ui.setCurrentDialogue("You used the " + name + " to open the door.");
            gamePanel.playSoundEffect(3);
            gamePanel.object[gamePanel.getCurrentMap()][objIndex] = null;
            return true;
        } else {
            gamePanel.ui.setCurrentDialogue("There is no door to open.");
            return false;
        }
    }
}
