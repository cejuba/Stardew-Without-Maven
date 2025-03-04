package fr.cejuba.stardew.main;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    // Debug
    boolean showDebugText = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void addKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {

            if(gamePanel.gameState == gamePanel.titleState) {
                titleState(event);
            } else if (gamePanel.gameState == gamePanel.playState) {
                playState(event);
            } else if (gamePanel.gameState == gamePanel.pauseState) {
                pauseState(event);
            } else if (gamePanel.gameState == gamePanel.dialogueState) {
                dialogueState(event);
            } else if (gamePanel.gameState == gamePanel.characterState) {
                characterState(event);
            }
        });

        scene.setOnKeyReleased(event -> {
            System.out.println("Key Released" + event.getCode());
            switch (event.getCode()) {
                case Z, UP -> upPressed = false;
                case S, DOWN -> downPressed = false;
                case Q, LEFT -> leftPressed = false;
                case D, RIGHT -> rightPressed = false;
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
                        case 1 -> gamePanel.gameState = gamePanel.playState; //TBD
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
                            gamePanel.gameState = gamePanel.playState;
                        }
                        case 1 -> {
                            System.out.println("Need to do thief stuff");
                            gamePanel.gameState = gamePanel.playState;
                        }
                        case 2 -> {
                            System.out.println("Need to do sorcerer stuff");
                            gamePanel.gameState = gamePanel.playState;
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
            case T -> showDebugText = !showDebugText;
            case R -> gamePanel.tileManager.loadMap("fr/cejuba/stardew/maps/worldV2.txt");
            case P -> gamePanel.gameState = gamePanel.pauseState;
            case C -> gamePanel.gameState = gamePanel.characterState;
            case ENTER -> enterPressed = true;
        }
    }

    private void pauseState(KeyEvent event) {
        switch (event.getCode()) {
            case P -> gamePanel.gameState = gamePanel.playState;
        }
    }

    private void dialogueState(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER -> gamePanel.gameState = gamePanel.playState;
        }
    }

    private void characterState(KeyEvent event) {
        switch (event.getCode()) {
            case C -> gamePanel.gameState = gamePanel.playState;
        }
    }

}
