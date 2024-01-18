package com.example.cardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GameWindow extends Application
{
    private String nickname;
    public GameWindow(String nickname)
    {
        this.nickname = nickname;
    }
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        Parent root = fxmlLoader.load();

        GameController controller = fxmlLoader.getController();
        controller.setNickname(nickname);

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("War - Card Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
