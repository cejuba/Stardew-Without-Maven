package fr.cejuba.stardew.main;

import javafx.scene.Scene;

public class KeyHandler {

    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    // Debug
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void addKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {
            System.out.println("Key Pressed" + event.getCode());
            switch (event.getCode()) {
                case Z, UP -> upPressed = true;
                case S, DOWN -> downPressed = true;
                case Q, LEFT -> leftPressed = true;
                case D, RIGHT -> rightPressed = true;
                case T -> checkDrawTime = !checkDrawTime;
                case P -> {
                    if(gamePanel.gameState == gamePanel.playState) gamePanel.gameState = gamePanel.pauseState;
                    else gamePanel.gameState = gamePanel.playState;
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
