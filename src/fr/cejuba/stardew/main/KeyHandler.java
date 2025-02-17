package fr.cejuba.stardew.main;

import javafx.scene.Scene;

public class KeyHandler {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    // Debug
    boolean checkDrawTime = true;

    public void addKeyHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {
            System.out.println("Key Pressed" + event.getCode());
            switch (event.getCode()) {
                case Z, UP -> upPressed = true;
                case S, DOWN -> downPressed = true;
                case Q, LEFT -> leftPressed = true;
                case D, RIGHT -> rightPressed = true;
                case T -> checkDrawTime = true;
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
