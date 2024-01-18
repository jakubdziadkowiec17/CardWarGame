package com.example.cardgame;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GameHandler implements Runnable
{
    private final Socket socket1;
    private final Socket socket2;
    private String player1;
    private String player2;
    private int i;
    private boolean endGame;
    private List<Card> deck1;
    private List<Card> deck2;
    public List<Card> war1 = new ArrayList<>();
    public List<Card> war2 = new ArrayList<>();
    AtomicReference<String> message1Atomic = new AtomicReference<>();
    AtomicReference<String> message2Atomic = new AtomicReference<>();
    public GameHandler(Socket socket1, Socket socket2)
    {
        this.socket1 = socket1;
        this.socket2 = socket2;
    }

    @Override
    public void run()
    {
        try (
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
                BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream()))
        )
        {
            endGame=false;
            List<Card> deck = new ArrayList<>();
            for (int suit = 0; suit < 4; suit++)
            {
                for (int rank = 2; rank <= 14; rank++)
                {
                    deck.add(new Card(suit, rank));
                }
            }
            Collections.shuffle(deck);
            Collections.shuffle(deck);
            deck1 = new ArrayList<>(deck.subList(0, 26));
            deck2 = new ArrayList<>(deck.subList(26, 52));

            while (!(deck1.isEmpty()) && !(deck2.isEmpty()) && !endGame)
            {
                Thread thread1 = new Thread(()->playerThread(reader1, message1Atomic));
                thread1.start();
                Thread thread2 = new Thread(()->playerThread(reader2, message2Atomic));
                thread2.start();


                thread1.join();
                thread2.join();
                String message1 = message1Atomic.get();
                String[] message1Part = message1.split("-");
                String message2 = message2Atomic.get();
                String[] message2Part = message2.split("-");

                if ("End Game".equals(message1) || "End Game".equals(message2))
                {
                    endGame=true;
                }
                if ("Player".equals(message1Part[0]) && "Player".equals(message2Part[0]))
                {
                    player1 = message1Part[1];
                    player2 = message2Part[1];

                    writer(writer1,"Player 1: " + player1);
                    writer(writer2,"Player 1: " + player1);
                    writer(writer1,"Player 2: " + player2);
                    writer(writer2,"Player 2: " + player2);
                }
                if ("Start".equals(message1Part[0]) && "Start".equals(message2Part[0]))
                {
                    writer(writer1,"Start");
                    writer(writer2,"Start");

                    writer(writer1,"Number of all cards: " + deck1.size());
                    writer(writer2,"Number of all cards: " + deck1.size());
                    writer(writer1,"Number of all cards: " + deck2.size());
                    writer(writer2,"Number of all cards: " + deck2.size());
                }
                if ("Next round".equals(message1) && "Next round".equals(message2))
                {
                    if (!(deck1.isEmpty()||deck2.isEmpty()))
                    {
                        Card playerCard = deck1.remove(0);
                        writer(writer1,playerCard.getRank() + "_" + playerCard.getSuit() + ".jpg");
                        writer(writer2,playerCard.getRank() + "_" + playerCard.getSuit() + ".jpg");

                        Card playerCard1 = deck2.remove(0);
                        writer(writer1,playerCard1.getRank() + "_" + playerCard1.getSuit() + ".jpg");
                        writer(writer2,playerCard1.getRank() + "_" + playerCard1.getSuit() + ".jpg");

                        compare(playerCard, playerCard1, writer1,writer2);
                    }
                }
                if ("War".equals(message1) && "War".equals(message2))
                {
                    for (int x = 0; x < 2; x++)
                    {
                        if (deck1.isEmpty())
                        {
                            deck2.addAll(war1);
                            deck2.addAll(war2);
                            war1.clear();
                            war2.clear();
                            break;
                        }
                        else if (deck2.isEmpty())
                        {
                            deck1.addAll(war1);
                            deck1.addAll(war2);
                            war1.clear();
                            war2.clear();
                            break;
                        }
                        else
                        {
                            war1.add(deck1.remove(0));
                            war2.add(deck2.remove(0));
                        }
                    }

                    if (war1.size() == (3+(i*2)) && war2.size() == (3+(i*2)))
                    {
                        if (war1.get(2*(i+1)).getRank() > war2.get(2*(i+1)).getRank())
                        {
                            deck1.addAll(war1);
                            deck1.addAll(war2);

                            writer(writer1,war1.get(2*(i+1)).getRank() + "_" + war1.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer1,war2.get(2*(i+1)).getRank() + "_" + war2.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer2,war1.get(2*(i+1)).getRank() + "_" + war1.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer2,war2.get(2*(i+1)).getRank() + "_" + war2.get(2*(i+1)).getSuit() + ".jpg");

                            writer(writer1,player1 + " wins the war round");
                            writer(writer2,player1 + " wins the war round");

                            writer(writer1,"Number of all cards: " + deck1.size());
                            writer(writer2,"Number of all cards: " + deck1.size());
                            writer(writer1,"Number of all cards: " + deck2.size());
                            writer(writer2,"Number of all cards: " + deck2.size());

                            writer(writer1,"Next round");
                            writer(writer2,"Next round");

                            war1.clear();
                            war2.clear();
                            i=0;
                        }
                        else if (war1.get(2*(i+1)).getRank() < war2.get(2*(i+1)).getRank())
                        {
                            deck2.addAll(war1);
                            deck2.addAll(war2);

                            writer(writer1,war1.get(2*(i+1)).getRank() + "_" + war1.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer1,war2.get(2*(i+1)).getRank() + "_" + war2.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer2,war1.get(2*(i+1)).getRank() + "_" + war1.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer2,war2.get(2*(i+1)).getRank() + "_" + war2.get(2*(i+1)).getSuit() + ".jpg");

                            writer(writer1,player2 + " wins the war round");
                            writer(writer2,player2 + " wins the war round");

                            writer(writer1,"Number of all cards: " + deck1.size());
                            writer(writer2,"Number of all cards: " + deck1.size());

                            writer(writer1,"Number of all cards: " + deck2.size());
                            writer(writer2,"Number of all cards: " + deck2.size());

                            writer(writer1,"Next round");
                            writer(writer2,"Next round");

                            war1.clear();
                            war2.clear();
                            i=0;
                        }
                        else
                        {
                            writer(writer1,war1.get(2*(i+1)).getRank() + "_" + war1.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer1,war2.get(2*(i+1)).getRank() + "_" + war2.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer2,war1.get(2*(i+1)).getRank() + "_" + war1.get(2*(i+1)).getSuit() + ".jpg");
                            writer(writer2,war2.get(2*(i+1)).getRank() + "_" + war2.get(2*(i+1)).getSuit() + ".jpg");

                            writer(writer1,"Cards are equal, click the button to start the war");
                            writer(writer2,"Cards are equal, click the button to start the war");
                            writer(writer1,"Number of cards remaining: " + deck1.size());
                            writer(writer2,"Number of cards remaining: " + deck1.size());
                            writer(writer1,"Number of cards remaining: " + deck2.size());
                            writer(writer2,"Number of cards remaining: " + deck2.size());
                            writer(writer1,"War");
                            writer(writer2,"War");

                            i++;
                        }
                    }
                    else
                    {
                        i=0;

                        writer(writer1,"WarCardGame.jpg");
                        writer(writer1,"WarCardGame.jpg");
                        writer(writer2,"WarCardGame.jpg");
                        writer(writer2,"WarCardGame.jpg");

                        if (deck1.isEmpty())
                        {
                            writer(writer1,player2 + " wins the match by missing the opponent's cards!!!");
                            writer(writer2,player2 + " wins the match by missing the opponent's cards!!!");

                            updateDatabase(player2, 1, 0, 0);
                            updateDatabase(player1, 0, 0, 1);
                        }
                        if (deck2.isEmpty())
                        {
                            writer(writer1,player1 + " wins the match by missing the opponent's cards!!!");
                            writer(writer2,player1 + " wins the match by missing the opponent's cards!!!");

                            updateDatabase(player1, 1, 0, 0);
                            updateDatabase(player2, 0, 0, 1);
                        }

                        writer(writer1,"Number of all cards: " + deck1.size());
                        writer(writer2,"Number of all cards: " + deck1.size());
                        writer(writer1,"Number of all cards: " + deck2.size());
                        writer(writer2,"Number of all cards: " + deck2.size());

                        writer(writer1,"End");
                        writer(writer2,"End");
                    }
                }
            }

            if (!endGame)
            {
                String message1 = reader1.readLine();
                String message2 = reader2.readLine();
            }
            writer(writer1,"WarCardGame.jpg");
            writer(writer1,"WarCardGame.jpg");
            writer(writer2,"WarCardGame.jpg");
            writer(writer2,"WarCardGame.jpg");

            if (endGame)
            {
                writer(writer1,"The match was interrupted");
                writer(writer2,"The match was interrupted");

                updateDatabase(player1, 0, 1, 0);
                updateDatabase(player2, 0, 1, 0);
            }
            else if (deck1.isEmpty())
            {
                writer(writer1,player2 + " wins the match by missing the opponent's cards!!!");
                writer(writer2,player2 + " wins the match by missing the opponent's cards!!!");

                updateDatabase(player2, 1, 0, 0);
                updateDatabase(player1, 0, 0, 1);
            }
            else if (deck2.isEmpty())
            {
                writer(writer1,player1 + " wins the match by missing the opponent's cards!!!");
                writer(writer2,player1 + " wins the match by missing the opponent's cards!!!");

                updateDatabase(player1, 1, 0, 0);
                updateDatabase(player2, 0, 0, 1);
            }

            writer(writer1,"Number of all cards: " + deck1.size());
            writer(writer2,"Number of all cards: " + deck1.size());
            writer(writer1,"Number of all cards: " + deck2.size());
            writer(writer2,"Number of all cards: " + deck2.size());

            writer(writer1,"End");
            writer(writer2,"End");

            socket1.close();
            socket2.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void compare(Card card1, Card card2, BufferedWriter writer1,  BufferedWriter writer2) throws IOException
    {
        if (card1.getRank() > card2.getRank())
        {
            writer(writer1,player1 + " wins the round");
            writer(writer2,player1 + " wins the round");

            deck1.addAll(List.of(card1, card2));

            writer(writer1,"Number of all cards: " + deck1.size());
            writer(writer2,"Number of all cards: " + deck1.size());
            writer(writer1,"Number of all cards: " + deck2.size());
            writer(writer2,"Number of all cards: " + deck2.size());

            writer(writer1,"Next round");
            writer(writer2,"Next round");
        }
        else if (card1.getRank() < card2.getRank())
        {
            writer(writer1,player2 + " wins the round");
            writer(writer2,player2 + " wins the round");

            deck2.addAll(List.of(card1, card2));

            writer(writer1,"Number of all cards: " + deck1.size());
            writer(writer2,"Number of all cards: " + deck1.size());
            writer(writer1,"Number of all cards: " + deck2.size());
            writer(writer2,"Number of all cards: " + deck2.size());

            writer(writer1,"Next round");
            writer(writer2,"Next round");
        }
        else
        {
            if (i==0)
            {
                war1.add(card1);
                war2.add(card2);
            }
            writer(writer1,"Cards are equal, click the button to start the war");
            writer(writer2,"Cards are equal, click the button to start the war");

            writer(writer1,"Number of cards remaining: " + deck1.size());
            writer(writer2,"Number of cards remaining: " + deck1.size());
            writer(writer1,"Number of cards remaining: " + deck2.size());
            writer(writer2,"Number of cards remaining: " + deck2.size());

            writer(writer1,"War");
            writer(writer2,"War");
        }
    }
    private void writer(BufferedWriter writer, String text) throws IOException
    {
        writer.write(text);
        writer.newLine();
        writer.flush();
    }
    private synchronized void playerThread(BufferedReader reader, AtomicReference<String> message_0)
    {
        try
        {
            String message = reader.readLine();
            if (message != null)
            {
                message_0.set(message);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void updateDatabase(String nickName, int won, int unresolved, int lost)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cardgame", "root", "");
            String query = "SELECT won, unresolved, lost FROM users WHERE nickname = ?";

            try (PreparedStatement statement=connection.prepareStatement(query))
            {
                statement.setString(1, nickName);
                ResultSet resultSet=statement.executeQuery();

                if (resultSet.next())
                {
                    int currentWon = resultSet.getInt("won");
                    int currentUnresolved = resultSet.getInt("unresolved");
                    int currentLost = resultSet.getInt("lost");

                    won += currentWon;
                    unresolved += currentUnresolved;
                    lost += currentLost;
                }
            }
            String query1 = "UPDATE users SET won = ?, unresolved = ?, lost = ?, date_of_the_last_game = ? WHERE nickname = ?";
            try (PreparedStatement statement = connection.prepareStatement(query1))
            {
                statement.setInt(1, won);
                statement.setInt(2, unresolved);
                statement.setInt(3, lost);
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                statement.setString(5, nickName);

                statement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}