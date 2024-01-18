package com.example.cardgame;

public class Card
{
    private int rank;
    private int suit;
    public Card(int suit, int rank)
    {
        this.rank = rank;
        this.suit = suit;
    }
    public int getRank()
    {
        return rank;
    }
    public String getSuit()
    {
        switch (suit)
        {
            case 0:
                return "Spades";
            case 1:
                return "Hearts";
            case 2:
                return "Clubs";
            case 3:
                return "Diamonds";
            default:
                return "";
        }
    }
}
