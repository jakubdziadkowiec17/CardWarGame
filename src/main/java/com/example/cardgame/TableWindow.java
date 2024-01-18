package com.example.cardgame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TableWindow extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table-view.fxml"));
        Parent root = fxmlLoader.load();

        TableController controller = fxmlLoader.getController();
        controller.initialize();

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setTitle("War - Card Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
