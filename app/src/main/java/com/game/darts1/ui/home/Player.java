package com.game.darts1.ui.home;

import androidx.lifecycle.MutableLiveData;

public class Player {
    private MutableLiveData<String> name;
    private MutableLiveData<Integer> points;
    private MutableLiveData<Integer> score;
    private MutableLiveData<Integer> dartNumber;
    private MutableLiveData<Score> dart1;
    private MutableLiveData<Score> dart2;
    private MutableLiveData<Score> dart3;
    private int startPoints;

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<Integer> getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points.setValue(points);
    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score.setValue(score);
    }

    public MutableLiveData<Integer> getDartNumber() {
        return dartNumber;
    }

    public void setDartNumber(int dartNumber) {
        this.dartNumber.setValue(dartNumber);
    }

    public MutableLiveData<Score> getDart1() {
        return dart1;
    }

    public void setDart1(Score dart1) {
        this.dart1.setValue(dart1);
    }

    public MutableLiveData<Score> getDart2() {
        return dart2;
    }

    public void setDart2(Score dart2) {
        this.dart2.setValue(dart2);
    }

    public MutableLiveData<Score> getDart3() {
        return dart3;
    }

    public void setDart3(Score dart3) {
        this.dart3.setValue(dart3);
    }

    public int getStartPoints() {
        return startPoints;
    }

    public void setStartPoints(int startPoints) {
        this.startPoints = startPoints;
    }

    public int getTotalPoint(){
        int point = 0;
        if(dart1 != null){
            point += dart1.getValue().getTotalScore();
        }
        if(dart2 != null){
            point += dart2.getValue().getTotalScore();
        }
        if(dart3 != null){
            point += dart3.getValue().getTotalScore();
        }
        return point;
    }

    public Player() {
        name = new MutableLiveData<>();
        points = new MutableLiveData<>();
        score = new MutableLiveData<>();
        dart1 = new MutableLiveData<>();
        dart2 = new MutableLiveData<>();
        dart3 = new MutableLiveData<>();
        dartNumber = new MutableLiveData<>();
        setScore(0);
        setDart1(null);
        setDart2(null);
        setDart3(null);
    }

    public Score getLastDart(){
        if(getDart3().getValue() != null){
            return getDart3().getValue();
        }else if(getDart2().getValue() != null){
            return getDart2().getValue();
        }else if(getDart1().getValue() != null){
            return getDart1().getValue();
        }
        return null;
    }
}
