package fr.cejuba.stardew.object.activable;

import fr.cejuba.stardew.main.GamePanel;
import fr.cejuba.stardew.main.GameState;
import fr.cejuba.stardew.main.Type;

public class Door extends Activatable {

    private final GamePanel gamePanel;

    public Door(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setType(Type.OBSTACLE);
        name = "Door";
        down2 = setup("object/door", gamePanel.getTileSize(), gamePanel.getTileSize());
        collision = true;

        solidArea.setX(0);
        solidArea.setY(16);
        solidArea.setWidth(48);
        solidArea.setHeight(32);
        solidAreaDefaultX = (int) solidArea.getX();
        solidAreaDefaultY = (int) solidArea.getY();
    }
    public void interact(){
        gamePanel.setGameState(GameState.DIALOGUE);
        gamePanel.ui.setCurrentDialogue("You need a key to open this.");
    }
}
