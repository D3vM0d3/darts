package com.game.darts1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> player1;
    private MutableLiveData<String> player2;
    private MutableLiveData<Integer> player1Points;
    private MutableLiveData<Integer> player2Points;
    private MutableLiveData<Integer> player1Score;
    private MutableLiveData<Integer> player2Score;
    private MutableLiveData<Integer> round;
    private MutableLiveData<Integer> currentPlayer;
    private MutableLiveData<Integer> dartNumber;
    private MutableLiveData<Score> dart1Value;
    private MutableLiveData<Score> dart2Value;
    private MutableLiveData<Score> dart3Value;
    private int player1StartPoints;
    private int player2StartPoints;

    public MutableLiveData<String> getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1.setValue(player1);
    }

    public MutableLiveData<String> getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2.setValue(player2);
    }

    public MutableLiveData<Integer> getPlayer1Points() {
        return player1Points;
    }

    public void setPlayer1Points(int player1Points) {
        this.player1Points.setValue(player1Points);
    }

    public MutableLiveData<Integer> getPlayer2Points() {
        return player2Points;
    }

    public void setPlayer2Points(int player2Points) {
        this.player2Points.setValue(player2Points);
    }

    public MutableLiveData<Integer> getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score.setValue(player1Score);
    }

    public MutableLiveData<Integer> getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score.setValue(player2Score);
    }

    public MutableLiveData<Integer> getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round.setValue(round);
    }

    public MutableLiveData<Integer> getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer.setValue(currentPlayer);
    }

    public MutableLiveData<Integer> getDartNumber() {
        return dartNumber;
    }

    public void setDartNumber(int dartNumber) {
        this.dartNumber.setValue(dartNumber);
    }

    public MutableLiveData<Score> getDart1Value() {
        return dart1Value;
    }

    public void setDart1Value(Score dart1Value) {
        this.dart1Value.setValue(dart1Value);
    }

    public MutableLiveData<Score> getDart2Value() {
        return dart2Value;
    }

    public void setDart2Value(Score dart2Value) {
        this.dart2Value.setValue(dart2Value);
    }

    public MutableLiveData<Score> getDart3Value() {
        return dart3Value;
    }

    public void setDart3Value(Score dart3Value) {
        this.dart3Value.setValue(dart3Value);
    }

    public int getPlayer1StartPoints() {
        return player1StartPoints;
    }

    public void setPlayer1StartPoints(int player1StartPoints) {
        this.player1StartPoints = player1StartPoints;
    }

    public int getPlayer2StartPoints() {
        return player2StartPoints;
    }

    public void setPlayer2StartPoints(int player2StartPoints) {
        this.player2StartPoints = player2StartPoints;
    }

    public HomeViewModel() {
        player1 = new MutableLiveData<>();
        player1.setValue("Player1");
        player2 = new MutableLiveData<>();
        player2.setValue("Player2");
        player1Points = new MutableLiveData<>();
        player1Points.setValue(501);
        player2Points = new MutableLiveData<>();
        player2Points.setValue(501);
        player1Score = new MutableLiveData<>();
        player1Score.setValue(0);
        player2Score = new MutableLiveData<>();
        player2Score.setValue(0);
        round = new MutableLiveData<>();
        round.setValue(1);
        currentPlayer = new MutableLiveData<>();
        currentPlayer.setValue(1);
        dartNumber = new MutableLiveData<>();
        dartNumber.setValue(1);
        dart1Value = new MutableLiveData<>();
        dart1Value.setValue(null);
        dart2Value = new MutableLiveData<>();
        dart2Value.setValue(null);
        dart3Value = new MutableLiveData<>();
        dart3Value.setValue(null);
    }
}