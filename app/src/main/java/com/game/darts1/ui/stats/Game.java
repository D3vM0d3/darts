package com.game.darts1.ui.stats;

public class Game {
    private int id;
    private String player1;
    private String player2;
    private String time;
    private int score1;
    private int score2;

    public int getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getTime() {
        return time;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public Game(int id, String player1, String player2, String time, int score1, int score2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.time = time;
        this.score1 = score1;
        this.score2 = score2;
    }
}
