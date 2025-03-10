package fr.cejuba.stardew.main;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

    // Debug
    boolean showDebugText = false;

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
        if (gamePanel.ui.titleScreenState == 0) {
            switch (event.getCode()) {
                case Z, UP -> {
                    gamePanel.ui.commandNumber--;
                    if (gamePanel.ui.commandNumber < 0) {
                        gamePanel.ui.commandNumber = 2;
                    }
                }
                case S, DOWN -> {
                    gamePanel.ui.commandNumber++;
                    if (gamePanel.ui.commandNumber > 2) {
                        gamePanel.ui.commandNumber = 0;
                    }
                }
                case ENTER -> {
                    switch (gamePanel.ui.commandNumber) {
                        case 0 -> gamePanel.ui.titleScreenState = 1;
                        case 1 -> gamePanel.setGameState(GameState.PLAY); //TBD
                        case 2 -> System.exit(0);
                    }
                }
            }
        } else if (gamePanel.ui.titleScreenState == 1) {
            switch (event.getCode()) {
                case Z, UP -> {
                    gamePanel.ui.commandNumber--;
                    if (gamePanel.ui.commandNumber < 0) {
                        gamePanel.ui.commandNumber = 3;
                    }
                }
                case S, DOWN -> {
                    gamePanel.ui.commandNumber++;
                    if (gamePanel.ui.commandNumber > 3) {
                        gamePanel.ui.commandNumber = 0;
                    }
                }
                case ENTER -> {
                    switch (gamePanel.ui.commandNumber) {
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
                        case 3 -> gamePanel.ui.titleScreenState = 0;
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
                switch(gamePanel.currentMap){
                    case 0 -> gamePanel.tileManager.loadMap("fr/cejuba/stardew/maps/worldV2.txt", 0);
                    case 1 -> gamePanel.tileManager.loadMap("fr/cejuba/stardew/maps/interior01.txt", 1);
                }
            }
            case M -> gamePanel.setGameState(GameState.MAP);
            case X -> gamePanel.map.miniMapActivated = !gamePanel.map.miniMapActivated;
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
        switch(gamePanel.ui.subState){
            case 0 -> maxCommandNumber = 5;
            case 3 -> maxCommandNumber = 1;
        }
        switch (event.getCode()) {


            case ESCAPE -> {
                gamePanel.setGameState(GameState.PLAY);
                gamePanel.ui.subState = 0;
            }
            case ENTER -> enterPressed = true;
            case Z, UP -> {
                gamePanel.ui.commandNumber--;
                gamePanel.playSoundEffect(9);
                if (gamePanel.ui.commandNumber < 0) {
                    gamePanel.ui.commandNumber = maxCommandNumber;
                }
            }
            case S, DOWN -> {
                gamePanel.ui.commandNumber++;
                gamePanel.playSoundEffect(9);
                if (gamePanel.ui.commandNumber > maxCommandNumber) {
                    gamePanel.ui.commandNumber = 0;
                }
            }
            case Q, LEFT -> {
                if (gamePanel.ui.subState == 0) {
                    if(gamePanel.ui.commandNumber == 1 && gamePanel.music.volumeScale > 0) {
                        gamePanel.music.volumeScale--;
                        gamePanel.music.checkVolume();
                        gamePanel.playSoundEffect(9);
                    }
                    if(gamePanel.ui.commandNumber == 2 && gamePanel.soundEffect.volumeScale > 0) {
                        gamePanel.soundEffect.volumeScale--;
                        gamePanel.soundEffect.checkVolume();
                        gamePanel.playSoundEffect(9);
                    }
                }

            }
            case D, RIGHT -> {
                if (gamePanel.ui.subState == 0) {
                    if(gamePanel.ui.commandNumber == 1 && gamePanel.music.volumeScale < 5) {
                        gamePanel.music.volumeScale++;
                        gamePanel.music.checkVolume();
                        gamePanel.playSoundEffect(9);
                    }
                    if(gamePanel.ui.commandNumber == 2 && gamePanel.soundEffect.volumeScale < 5) {
                        gamePanel.soundEffect.volumeScale++;
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
                gamePanel.ui.commandNumber--;
                if(gamePanel.ui.commandNumber < 0) {
                    gamePanel.ui.commandNumber = 1;
                }
                gamePanel.playSoundEffect(9);
            }
            case S, DOWN -> {
                gamePanel.ui.commandNumber++;
                if(gamePanel.ui.commandNumber > 1) {
                    gamePanel.ui.commandNumber = 0;
                }
                gamePanel.playSoundEffect(9);
            }
            case ENTER -> {
                switch (gamePanel.ui.commandNumber) {
                    case 0 -> {
                        gamePanel.setGameState(GameState.PLAY);
                        gamePanel.retry();
                        gamePanel.playMusic(0);
                    }
                    case 1 -> {
                        gamePanel.setGameState(GameState.TITLE);
                        gamePanel.stopMusic();
                        gamePanel.ui.titleScreenState = 0;
                        gamePanel.ui.commandNumber = 0;
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

        switch(gamePanel.ui.subState){
            case 0 -> {
                switch (event.getCode()) {
                    case Z, UP -> {
                        gamePanel.ui.commandNumber--;
                        gamePanel.playSoundEffect(9);
                        if (gamePanel.ui.commandNumber < 0) {
                            gamePanel.ui.commandNumber = 2;
                        }
                    }
                    case S, DOWN -> {
                        gamePanel.ui.commandNumber++;
                        gamePanel.playSoundEffect(9);
                        if (gamePanel.ui.commandNumber > 2) {
                            gamePanel.ui.commandNumber = 0;
                        }
                    }
                    case ESCAPE -> {
                        gamePanel.setGameState(GameState.PLAY);
                        gamePanel.ui.npc = null;
                     }
                }
            }
            case 1 -> {
                npcInventory(event.getCode());
                if(event.getCode() == KeyCode.ESCAPE){
                    gamePanel.ui.subState = 0;
                }
            }
            case 2 -> {
                playerInventory(event.getCode());
                if(event.getCode() == KeyCode.ESCAPE){
                    gamePanel.ui.subState = 0;
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
        switch(code){
            case Z, UP -> {
                if(gamePanel.ui.playerSlotRow !=0) {
                    gamePanel.ui.playerSlotRow--;
                    gamePanel.playSoundEffect(9);
                }
            }
            case S, DOWN -> {
                if(gamePanel.ui.playerSlotRow != 3) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.playerSlotRow++;
                    gamePanel.playSoundEffect(9);
                }
            }
            case Q, LEFT -> {
                if(gamePanel.ui.playerSlotColumn !=0) {
                    gamePanel.ui.playerSlotColumn--;
                    gamePanel.playSoundEffect(9);
                }
            }
            case D, RIGHT -> {
                if (gamePanel.ui.playerSlotColumn != 4) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.playerSlotColumn++;
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }

    private void npcInventory(KeyCode code){
        switch(code){
            case Z, UP -> {
                if(gamePanel.ui.npcSlotRow !=0) {
                    gamePanel.ui.npcSlotRow--;
                    gamePanel.playSoundEffect(9);
                }
            }
            case S, DOWN -> {
                if(gamePanel.ui.npcSlotRow != 3) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.npcSlotRow++;
                    gamePanel.playSoundEffect(9);
                }
            }
            case Q, LEFT -> {
                if(gamePanel.ui.npcSlotColumn !=0) {
                    gamePanel.ui.npcSlotColumn--;
                    gamePanel.playSoundEffect(9);
                }
            }
            case D, RIGHT -> {
                if (gamePanel.ui.npcSlotColumn != 4) { // TODO : Variable don't scale with windows size
                    gamePanel.ui.npcSlotColumn++;
                    gamePanel.playSoundEffect(9);
                }
            }
        }
    }
}
