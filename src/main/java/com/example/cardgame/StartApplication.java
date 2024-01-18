package com.example.cardgame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class StartApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardgame","root","");

            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("start-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 700);
            stage.setTitle("War - Card Game");
            stage.setScene(scene);
            stage.show();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        launch();
    }
}