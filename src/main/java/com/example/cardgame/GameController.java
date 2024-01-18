package com.example.cardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.Socket;

public class GameController
{
    private String nickName;
    @FXML
    private Label playerLabel;
    @FXML
    private Label playerLabel2;
    @FXML
    private Label result1;
    @FXML
    private Label result2;
    @FXML
    private Label result;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private Button showCardButton;
    @FXML
    private Button startAgainButton;
    @FXML
    private Button tableButton;
    @FXML
    private Button endTheGameButton;
    @FXML
    private Button menuButton;
    @FXML
    private Button warButton;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    public void setNickname(String nickName) throws IOException
    {
        this.nickName = nickName;
        writer.write("Player-"+this.nickName);
        writer.newLine();
        writer.flush();

        String player1 = reader.readLine();
        playerLabel.setText(player1);
        String player2 = reader.readLine();
        playerLabel2.setText(player2);
    }

    @FXML
    public void initialize() throws IOException
    {
        try
        {
            socket = new Socket("localhost", 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        writer.write("Start");
        writer.newLine();
        writer.flush();

        String message = reader.readLine();
        if ("Start".equals(message))
        {
            result.setText("Click 'Show Card' button to start the game.");
            showCardButton.setVisible(true);
            endTheGameButton.setVisible(true);
            startAgainButton.setVisible(false);
            tableButton.setVisible(false);
            menuButton.setVisible(false);
            warButton.setVisible(false);
            image1.setImage(showCard("WarCardGame.jpg"));
            image2.setImage(showCard("WarCardGame.jpg"));

            String result_1 = reader.readLine();
            result1.setText(result_1);
            String result_2 = reader.readLine();
            result2.setText(result_2);
        }
    }
    private Image showCard(String card)
    {
        String imageName = card;
        String currentWorkingDirectory = System.getProperty("user.dir");
        String imagePath = currentWorkingDirectory+"/cards/" + imageName;
        imagePath = imagePath.replace("\\", File.separator);
        return new Image(new File(imagePath).toURI().toString());
    }
    @FXML
    public void nextRound(ActionEvent event)
    {
        try
        {
            writer.write("Next round");
            writer.newLine();
            writer.flush();

            String card1 = reader.readLine();
            image1.setImage(showCard(card1));
            String card2 = reader.readLine();
            image2.setImage(showCard(card2));

            String result_0 = reader.readLine();
            result.setText(result_0);
            String result_1 = reader.readLine();
            result1.setText(result_1);
            String result_2 = reader.readLine();
            result2.setText(result_2);

            String var = reader.readLine();
            if ("War".equals(var))
            {
                showCardButton.setVisible(false);
                warButton.setVisible(true);
            }
            else if ("End".equals(var))
            {
                showCardButton.setVisible(false);
                endTheGameButton.setVisible(false);
                startAgainButton.setVisible(true);
                tableButton.setVisible(true);
                menuButton.setVisible(true);
                warButton.setVisible(false);
            }

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    @FXML
    public void war(ActionEvent event)
    {
        try
        {
            writer.write("War");
            writer.newLine();
            writer.flush();

            String card1 = reader.readLine();
            image1.setImage(showCard(card1));
            String card2 = reader.readLine();
            image2.setImage(showCard(card2));

            String result_0 = reader.readLine();
            result.setText(result_0);
            String result_1 = reader.readLine();
            result1.setText(result_1);
            String result_2 = reader.readLine();
            result2.setText(result_2);

            String var = reader.readLine();
            if ("Next round".equals(var))
            {
                showCardButton.setVisible(true);
                warButton.setVisible(false);
            }
            else if ("End".equals(var))
            {
                showCardButton.setVisible(false);
                endTheGameButton.setVisible(false);
                startAgainButton.setVisible(true);
                tableButton.setVisible(true);
                menuButton.setVisible(true);
                warButton.setVisible(false);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @FXML
    void goToResultWindow(ActionEvent event) throws IOException
    {
        showCardButton.setVisible(false);
        endTheGameButton.setVisible(false);
        startAgainButton.setVisible(true);
        tableButton.setVisible(true);
        menuButton.setVisible(true);
        warButton.setVisible(false);

        image1.setImage(showCard("WarCardGame.jpg"));
        image2.setImage(showCard("WarCardGame.jpg"));
        result.setText("The match was interrupted");

        writer.write("End Game");
        writer.newLine();
        writer.flush();
    }
    @FXML
    void goToGameWindow(ActionEvent event)
    {
        try
        {
            GameWindow gamewindow = new GameWindow(nickName);
            gamewindow.start(new Stage());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
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
            TableWindow tableWindow = new TableWindow();
            tableWindow.start(new Stage());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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