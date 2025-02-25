package fr.cejuba.stardew.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GamePanel gamePanel = new GamePanel();

        StackPane root = new StackPane();
        root.getChildren().add(gamePanel);
        Scene scene = new Scene(root, gamePanel.getWidth(), gamePanel.getHeight());

        gamePanel.keyHandler.addKeyHandlers(scene);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        primaryStage.setTitle("Stardew Without Maven");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();


        gamePanel.setupGame();
        // gamePanel.startGameThread(); // TOSHOW

    }

    public static void main(String[] args) {
        launch(args);
    }
}
