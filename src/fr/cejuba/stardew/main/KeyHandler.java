package fr.cejuba.stardew.main;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

    private final GamePanel gamePanel;
    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

    // Debug
    private boolean showDebugText = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void addKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {

            switch(gamePanel.getGameState()){
                case GameState.TITLE -> titleState(event);
                case GameState.PLAY -> playState(event);
                case GameState.PAUSE -> pauseState(event);
                case GameState.DIALOGUE -> dialogueState(event);
                case GameState.INVENTORY -> characterState(event);
                case GameState.OPTION -> optionState(event);
                case GameState.GAMEOVER -> gameOverState(event);
                case GameState.TRADE -> tradeState(event);
                case GameState.MAP -> mapState(event);
            }
        });

        scene.setOnKeyReleased(event -> {
            System.out.println("Key Released" + event.getCode());
            switch (event.getCode()) {
                case Z, UP -> upPressed = false;
                case S, DOWN -> downPressed = false;
                case Q, LEFT -> leftPressed = false;
                case D, RIGHT -> rightPressed = false;
                case F -> shotKeyPressed = false;
            }
        });
    }

    private void titleState(KeyEvent event) {
        if (gamePanel.ui.getTitleScreenState() == 0) {
            switch (event.getCode()) {
                case Z, UP -> {
                    gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() - 1);
                    if (gamePanel.ui.getCommandNumber() < 0) {
                        gamePanel.ui.setCommandNumber(2);
                    }
                }
                case S, DOWN -> {
                    gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() + 1);
                    if (gamePanel.ui.getCommandNumber() > 2) {
                        gamePanel.ui.setCommandNumber(0);
                    }
                }
                case ENTER -> {
                    switch (gamePanel.ui.getCommandNumber()) {
                        case 0 -> gamePanel.ui.setTitleScreenState(1);
                        case 1 -> gamePanel.setGameState(GameState.PLAY); //TBD
                        case 2 -> System.exit(0);
                    }
                }
            }
        } else if (gamePanel.ui.getTitleScreenState() == 1) {
            switch (event.getCode()) {
                case Z, UP -> {
                    gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() - 1);
                    if (gamePanel.ui.getCommandNumber() < 0) {
                        gamePanel.ui.setCommandNumber(3);
                    }
                }
                case S, DOWN -> {
                    gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() + 1);
                    if (gamePanel.ui.getCommandNumber() > 3) {
                        gamePanel.ui.setCommandNumber(0);
                    }
                }
                case ENTER -> {
                    switch (gamePanel.ui.getCommandNumber()) {
                        case 0 -> {
                            System.out.println("Need to do fighter stuff");
                            gamePanel.setGameState(GameState.PLAY);
                            gamePanel.playMusic(0);
                        }
                        case 1 -> {
                            System.out.println("Need to do thief stuff");
                            gamePanel.setGameState(GameState.PLAY);
                            gamePanel.playMusic(0);
                        }
                        case 2 -> {
                            System.out.println("Need to do sorcerer stuff");
                            gamePanel.setGameState(GameState.PLAY);
                            gamePanel.playMusic(0);
                        }
                        case 3 -> gamePanel.ui.setTitleScreenState(0);
                    }
                }
            }
        }
    }

    private void playState(KeyEvent event) {
        switch (event.getCode()) {
            case Z, UP -> upPressed = true;
            case S, DOWN -> downPressed = true;
            case Q, LEFT -> leftPressed = true;
            case D, RIGHT -> rightPressed = true;
            case F -> shotKeyPressed = true;
            case T -> showDebugText = !showDebugText;
            case R -> {
                switch(gamePanel.getCurrentMap()){
                    case 0 -> gamePanel.tileManager.loadMap("fr/cejuba/stardew/maps/worldV2.txt", 0);
                    case 1 -> gamePanel.tileManager.loadMap("fr/cejuba/stardew/maps/interior01.txt", 1);
                }
            }
            case M -> gamePanel.setGameState(GameState.MAP);
            case X -> gamePanel.map.setMiniMapActivated(gamePanel.map.isMiniMapActivated());
            case P -> gamePanel.setGameState(GameState.PAUSE);
            case C -> gamePanel.setGameState(GameState.INVENTORY);
            case ESCAPE -> gamePanel.setGameState(GameState.OPTION);
            case ENTER -> enterPressed = true;
        }
    }

    private void pauseState(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE, P -> gamePanel.setGameState(GameState.PLAY);
        }
    }

    private void dialogueState(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE, ENTER -> gamePanel.setGameState(GameState.PLAY);
        }
    }

    private void characterState(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE, C -> gamePanel.setGameState(GameState.PLAY);
            case ENTER -> gamePanel.player.selectItem();
        }
        playerInventory(event.getCode());
    }

    private void optionState(KeyEvent event) {
        int maxCommandNumber = 0;
        switch(gamePanel.ui.getSubState()){
            case 0 -> maxCommandNumber = 5;
            case 3 -> maxCommandNumber = 1;
        }
        switch (event.getCode()) {

            case ESCAPE -> {
                gamePanel.setGameState(GameState.PLAY);
                gamePanel.ui.setSubState(0);
            }
            case ENTER -> enterPressed = true;
            case Z, UP -> {
                gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() - 1);
                gamePanel.playSoundEffect(9);
                if (gamePanel.ui.getCommandNumber() < 0) {
                    gamePanel.ui.setCommandNumber(maxCommandNumber);
                }
            }
            case S, DOWN -> {
                gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() + 1);
                gamePanel.playSoundEffect(9);
                if (gamePanel.ui.getCommandNumber() > maxCommandNumber) {
                    gamePanel.ui.setCommandNumber(0);
                }
            }
            case Q, LEFT -> {
                if (gamePanel.ui.getSubState() == 0) {
                    if(gamePanel.ui.getCommandNumber() == 1 && gamePanel.music.getVolumeScale() > 0) {
                        gamePanel.music.setVolumeScale(gamePanel.music.getVolumeScale() - 1);
                        gamePanel.music.checkVolume();
                        gamePanel.playSoundEffect(9);
                    }
                    if(gamePanel.ui.getCommandNumber() == 2 && gamePanel.soundEffect.getVolumeScale() > 0) {
                        gamePanel.soundEffect.setVolumeScale(gamePanel.soundEffect.getVolumeScale() - 1);
                        gamePanel.soundEffect.checkVolume();
                        gamePanel.playSoundEffect(9);
                    }
                }

            }
            case D, RIGHT -> {
                if (gamePanel.ui.getSubState() == 0) {
                    if(gamePanel.ui.getCommandNumber() == 1 && gamePanel.music.getVolumeScale() < 5) {
                        gamePanel.music.setVolumeScale(gamePanel.music.getVolumeScale() + 1);
                        gamePanel.music.checkVolume();
                        gamePanel.playSoundEffect(9);
                    }
                    if(gamePanel.ui.getCommandNumber() == 2 && gamePanel.soundEffect.getVolumeScale() < 5) {
                        gamePanel.soundEffect.setVolumeScale(gamePanel.soundEffect.getVolumeScale() + 1);
                        gamePanel.soundEffect.checkVolume();
                        gamePanel.playSoundEffect(9);
                    }
                }
            }

        }
    }

    private void gameOverState(KeyEvent event) {
        switch (event.getCode()) {
            case Z, UP -> {
                gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() - 1);
                if(gamePanel.ui.getCommandNumber() < 0) {
                    gamePanel.ui.setCommandNumber(1);
                }
                gamePanel.playSoundEffect(9);
            }
            case S, DOWN -> {
                gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() + 1);
                if(gamePanel.ui.getCommandNumber() > 1) {
                    gamePanel.ui.setCommandNumber(0);
                }
                gamePanel.playSoundEffect(9);
            }
            case ENTER -> {
                switch (gamePanel.ui.getCommandNumber()) {
                    case 0 -> {
                        gamePanel.setGameState(GameState.PLAY);
                        gamePanel.retry();
                        gamePanel.playMusic(0);
                    }
                    case 1 -> {
                        gamePanel.setGameState(GameState.TITLE);
                        gamePanel.stopMusic();
                        gamePanel.ui.setTitleScreenState(0);
                        gamePanel.ui.setCommandNumber(0);
                        gamePanel.restart();
                    }
                }
            }
        }
    }

    private void tradeState(KeyEvent event) {

        if(event.getCode() == KeyCode.ENTER) {
            enterPressed = true;
        }

        switch(gamePanel.ui.getSubState()){
            case 0 -> {
                switch (event.getCode()) {
                    case Z, UP -> {
                        gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() - 1);
                        gamePanel.playSoundEffect(9);
                        if (gamePanel.ui.getCommandNumber() < 0) {
                            gamePanel.ui.setCommandNumber(2);
                        }
                    }
                    case S, DOWN -> {
                        gamePanel.ui.setCommandNumber(gamePanel.ui.getCommandNumber() + 1);
                        gamePanel.playSoundEffect(9);
                        if (gamePanel.ui.getCommandNumber() > 2) {
                            gamePanel.ui.setCommandNumber(0);
                        }
                    }
                    case ESCAPE -> {
                        gamePanel.setGameState(GameState.PLAY);
                        gamePanel.ui.setNpc(null);
                     }
                }
            }
            case 1 -> {
                npcInventory(event.getCode());
                if(event.getCode() == KeyCode.ESCAPE){
                    gamePanel.ui.setSubState(0);
                }
            }
            case 2 -> {
                playerInventory(event.getCode());
                if(event.getCode() == KeyCode.ESCAPE){
                    gamePanel.ui.setSubState(0);
                }
            }
        }
    }

    private void mapState(KeyEvent event) {
        switch (event.getCode()) {
            case ESCAPE, M -> gamePanel.setGameState(GameState.PLAY);
        }
    }

    private void playerInventory(KeyCode code){
        int row = gamePanel.ui.getPlayerSlotRow();
        int col = gamePanel.ui.getPlayerSlotColumn();
        switch(code){
            case Z, UP -> {
                if(row !=0) {
                    gamePanel.ui.setPlayerSlotRow(row - 1);
                    gamePanel.playSoundEffect(9);
                }
            }
            case S, DOWN -> {
                if(row != 3) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.setPlayerSlotRow(row + 1);
                    gamePanel.playSoundEffect(9);
                }
            }
            case Q, LEFT -> {
                if(col !=0) {
                    gamePanel.ui.setPlayerSlotColumn(col - 1);
                    gamePanel.playSoundEffect(9);
                }
            }
            case D, RIGHT -> {
                if (col != 4) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.setPlayerSlotColumn(col + 1);
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }

    private void npcInventory(KeyCode code){
        int row = gamePanel.ui.getNpcSlotRow();
        int col = gamePanel.ui.getNpcSlotColumn();
        switch(code){
            case Z, UP -> {
                if(row !=0) {
                    gamePanel.ui.setNpcSlotRow(row - 1);
                    gamePanel.playSoundEffect(9);
                }
            }
            case S, DOWN -> {
                if(row != 3) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.setNpcSlotRow(row + 1);
                    gamePanel.playSoundEffect(9);
                }
            }
            case Q, LEFT -> {
                if(col !=0) {
                    gamePanel.ui.setNpcSlotColumn(col - 1);
                    gamePanel.playSoundEffect(9);
                }
            }
            case D, RIGHT -> {
                if (col != 4) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.setNpcSlotColumn(col +  1);
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }

    // Getters and setters
    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isDownPressed() {
        return downPressed;
    }
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public boolean isEnterPressed() {
        return enterPressed;
    }
    public boolean isShotKeyPressed() {
        return shotKeyPressed;
    }
    public boolean isShowDebugText() {
        return showDebugText;
    }
    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }
    public GamePanel getGamePanel() {
        return gamePanel;
    }

}
