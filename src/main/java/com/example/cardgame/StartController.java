package com.example.cardgame;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StartController
{
    @FXML
    private TextField nicknameTextField;
    @FXML
    private Label errorLabel;
    @FXML
    void goToGameWindow(ActionEvent event)
    {
        try
        {
            String nickname = nicknameTextField.getText();
            if (!nickname.isEmpty())
            {
                GameWindow gamewindow = new GameWindow(nickname);
                addToDatabase(nickname);
                gamewindow.start(new Stage());
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            }
            else
            {
                errorLabel.setText("Nickname cannot be empty, enter a new field value.");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void goToTableWindow(ActionEvent event)
    {
        try
        {
            TableWindow tablewindow = new TableWindow();
            tablewindow.start(new Stage());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void addToDatabase(String nickName)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardgame", "root", "");
            if (!userExists(connection, nickName))
            {
                String insertQuery = "INSERT INTO users (nickname, won, unresolved, lost, date_of_the_last_game) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery))
                {
                    insertStatement.setString(1, nickName);
                    insertStatement.setInt(2, 0);
                    insertStatement.setInt(3, 0);
                    insertStatement.setInt(4, 0);
                    insertStatement.setDate(5, null);

                    insertStatement.executeUpdate();
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private boolean userExists(Connection connection, String nickName) throws SQLException
    {
        String query = "SELECT COUNT(*) FROM users WHERE nickname = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, nickName);
            try (ResultSet set = statement.executeQuery())
            {
                if (set.next())
                {
                    return set.getInt(1)>0;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}