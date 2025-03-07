package fr.cejuba.stardew.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GamePanel gamePanel = new GamePanel(primaryStage);
        gamePanel.setWidth(gamePanel.screenWidth);
        gamePanel.setHeight(gamePanel.screenHeight);

        StackPane root = new StackPane();
        root.getChildren().add(gamePanel);
        Scene scene = new Scene(root, gamePanel.getWidth(), gamePanel.getHeight());

        gamePanel.keyHandler.addKeyHandlers(scene);
        primaryStage.setTitle("Stardew Without Maven");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();

        gamePanel.setupGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
