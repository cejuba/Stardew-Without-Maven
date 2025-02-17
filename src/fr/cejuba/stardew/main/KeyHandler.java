package fr.cejuba.stardew.main;

import javafx.scene.Scene;

public class KeyHandler {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    // Debug
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void addKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {

            // Play State
            if(gamePanel.gameState == gamePanel.playState){
                System.out.println("Key Pressed" + event.getCode());
                switch (event.getCode()) {
                    case Z, UP -> upPressed = true;
                    case S, DOWN -> downPressed = true;
                    case Q, LEFT -> leftPressed = true;
                    case D, RIGHT -> rightPressed = true;
                    case T -> checkDrawTime = !checkDrawTime;
                    case P -> gamePanel.gameState = gamePanel.pauseState;
                    case ENTER -> enterPressed = true;
                }
            }

            // Pause State
            else if(gamePanel.gameState == gamePanel.pauseState) {
                switch (event.getCode()) {
                    case P -> gamePanel.gameState = gamePanel.playState;

                }
            }
            // Dialogue State

            else if(gamePanel.gameState == gamePanel.dialogueState){
                switch (event.getCode()) {
                    case ENTER -> gamePanel.gameState = gamePanel.playState;
                }
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
}
