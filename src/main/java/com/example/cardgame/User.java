package com.example.cardgame;

public class User
{
    private int id;
    private String nickname;
    private int won;
    private int unresolved;
    private int lost;
    private String date_of_the_last_game;
    public User(int id, String nickname, int won, int unresolved, int lost, String date_of_the_last_game)
    {
        this.id = id;
        this.nickname = nickname;
        this.won = won;
        this.unresolved = unresolved;
        this.lost = lost;
        this.date_of_the_last_game = date_of_the_last_game;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getWon() {
        return won;
    }
    public void setWon(int won) {
        this.won = won;
    }
    public int getUnresolved() {
        return unresolved;
    }
    public void setUnresolved(int unresolved) {
        this.unresolved = unresolved;
    }
    public int getLost() {
        return lost;
    }
    public void setLost(int lost) {
        this.lost = lost;
    }
    public String getDate_of_the_last_game() {
        return date_of_the_last_game;
    }
    public void setDate_of_the_last_game(String date_of_the_last_game) {
        this.date_of_the_last_game = date_of_the_last_game;
    }
}