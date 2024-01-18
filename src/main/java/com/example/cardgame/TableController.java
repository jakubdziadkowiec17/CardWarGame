package com.example.cardgame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.*;

public class TableController
{
    @FXML
    private TableColumn<User, Integer> placeColumn;
    @FXML
    private TableColumn<User, String> date_of_the_last_gameColumn;
    @FXML
    private TableColumn<User, Integer> lostColumn;
    @FXML
    private TableColumn<User, String> nicknameColumn;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> unresolvedColumn;
    @FXML
    private TableColumn<User, Integer> wonColumn;
    @FXML
    private TableColumn<User, Integer> allColumn;
    @FXML
    public void initialize()
    {
        placeColumn.setCellValueFactory(data ->
        {
            User user = data.getValue();
            int place = tableView.getItems().indexOf(user) + 1;
            return javafx.beans.binding.Bindings.createObjectBinding(() -> place);
        });
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        wonColumn.setCellValueFactory(new PropertyValueFactory<>("won"));
        unresolvedColumn.setCellValueFactory(new PropertyValueFactory<>("unresolved"));
        lostColumn.setCellValueFactory(new PropertyValueFactory<>("lost"));
        allColumn.setCellValueFactory(data ->
        {
            User user = data.getValue();
            int all=user.getWon() + user.getUnresolved() + user.getLost();
            return javafx.beans.binding.Bindings.createObjectBinding(() -> all);
        });
        date_of_the_last_gameColumn.setCellValueFactory(new PropertyValueFactory<>("date_of_the_last_game"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(placeColumn, nicknameColumn, wonColumn, unresolvedColumn, lostColumn, allColumn, date_of_the_last_gameColumn);

        ObservableList<User> users = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardgame","root",""))
        {
            String query = "SELECT id, nickname, won, unresolved, lost, date_of_the_last_game FROM users order by won desc, unresolved desc, lost asc";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query))
            {
                try (ResultSet resultSet = preparedStatement.executeQuery())
                {
                    while (resultSet.next())
                    {
                        int id = resultSet.getInt("id");
                        String nickname = resultSet.getString("nickname");
                        int won = resultSet.getInt("won");
                        int unresolved = resultSet.getInt("unresolved");
                        int lost = resultSet.getInt("lost");
                        String date_of_the_last_game = resultSet.getString("date_of_the_last_game");

                        User user = new User(id, nickname, won, unresolved, lost, date_of_the_last_game);
                        users.add(user);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        tableView.setItems(users);
    }
    @FXML
    void goToStartApplication(ActionEvent event)
    {
        try
        {
            StartApplication startApplication = new StartApplication();
            startApplication.start(new Stage());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}