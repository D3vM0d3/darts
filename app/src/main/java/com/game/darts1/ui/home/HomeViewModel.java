package com.game.darts1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private Player player1;
    private Player player2;
    private MutableLiveData<Integer> round;
    private MutableLiveData<Integer> currentPlayerNumber;
    private boolean started;

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public MutableLiveData<Integer> getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round.setValue(round);
    }

    public MutableLiveData<Integer> getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(int currentPlayer) {
        this.currentPlayerNumber.setValue(currentPlayer);
    }

    public Player getCurrentPlayer(){
        if(currentPlayerNumber.getValue() == 1){
            return player1;
        }
        return player2;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public HomeViewModel() {
        player1 = new Player();
        player1.setName("Player1");
        player2 = new Player();
        player2.setName("Player2");
        round = new MutableLiveData<>();
        round.setValue(0);
        currentPlayerNumber = new MutableLiveData<>();
        currentPlayerNumber.setValue(1);
    }

    public void switchPlayer(){
        if(getCurrentPlayerNumber().getValue() == 1){
            getCurrentPlayerNumber().setValue(2);
        }else{
            getCurrentPlayerNumber().setValue(1);
        }
    }

    public void startNewGame(String p1, String p2){
        setStarted(true);
        player1.setName(p1);
        player2.setName(p2);
    }
}