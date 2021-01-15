package com.game.darts1.ui.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.game.darts1.ui.home.Player;
import com.game.darts1.ui.home.Score;
import com.game.darts1.ui.stats.Game;
import com.game.darts1.ui.stats.GameStats;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    final static String NAME = "darts.db";
    final static String TABLE_GAMES = "games";
    final static String TABLE_POINTS = "points";
    static SQLiteDatabase db;

    public void connect(String path){
        if(db != null){
            db.close();
        }
        File f = new File(path+"/"+NAME);

        db = SQLiteDatabase.openOrCreateDatabase(f, null);
        createTables();
    }

    public void createTables(){
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TABLE_GAMES);
        sb.append("(");
        sb.append("id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append("player1 TEXT NOT NULL,");
        sb.append("player2 TEXT NOT NULL,");
        sb.append("time TEXT NOT NULL,");
        sb.append("score1 INTEGER NOT NULL,");
        sb.append("score2 INTEGER NOT NULL");
        sb.append(");");

        db.execSQL(sb.toString());

        sb = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TABLE_POINTS);
        sb.append("(");
        sb.append("game_id INTEGER NOT NULL,");
        sb.append("round INTEGER NOT NULL,");
        sb.append("p1_dart1 INTEGER,");
        sb.append("p1_dart1_m INTEGER,");
        sb.append("p1_dart2 INTEGER,");
        sb.append("p1_dart2_m INTEGER,");
        sb.append("p1_dart3 INTEGER,");
        sb.append("p1_dart3_m INTEGER,");
        sb.append("p2_dart1 INTEGER,");
        sb.append("p2_dart1_m INTEGER,");
        sb.append("p2_dart2 INTEGER,");
        sb.append("p2_dart2_m INTEGER,");
        sb.append("p2_dart3 INTEGER,");
        sb.append("p2_dart3_m INTEGER,");
        sb.append("p1_points INTEGER NOT NULL,");
        sb.append("p2_points INTEGER NOT NULL");
        sb.append(");");
        db.execSQL(sb.toString());
    }

    public int addGame(String p1, String p2){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        ContentValues contentValues = new ContentValues();
        contentValues.put("player1", p1);
        contentValues.put("player2", p2);
        contentValues.put("time", formatter.format(date));
        contentValues.put("score1", 0);
        contentValues.put("score2", 0);

        long c = db.insert(TABLE_GAMES, null, contentValues);

        return (int)c;
    }

    public void updateGame(int id, int score1, int score2){
        ContentValues contentValues = new ContentValues();
        contentValues.put("score1", score1);
        contentValues.put("score2", score2);
        db.update(TABLE_GAMES, contentValues, "id=?",new String[]{String.valueOf(id)});
    }

    public void addDarts(int id, int round, Score[] scores, int points1, int points2){
        ContentValues contentValues = new ContentValues();
        contentValues.put("game_id", id);
        contentValues.put("round", round);

        for(int i = 0; i < 3; i++){

            contentValues.put("p1_dart"+(i+1), (scores[i] != null) ? scores[i].getValue() : null);
            contentValues.put("p1_dart"+(i+1)+"_m", (scores[i] != null) ? scores[i].getMultiplier() : null);
        }
        for(int i = 0; i < 3; i++){
            contentValues.put("p2_dart"+(i+1), (scores[i+3] != null) ? scores[i+3].getValue() : null);
            contentValues.put("p2_dart"+(i+1)+"_m", (scores[i+3] != null) ? scores[i+3].getMultiplier() : null);
        }
        contentValues.put("p1_points", points1);
        contentValues.put("p2_points", points2);

        long c = db.insert(TABLE_POINTS, null, contentValues);
    }

    public List<Game> getGames(){
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_GAMES+" ORDER BY time DESC", null);
        List<Game> list = new ArrayList<Game>();
        while(c.moveToNext()){
            list.add(new Game(
                    getIntValue(c, "id"),
                    getStringValue(c, "player1"),
                    getStringValue(c, "player2"),
                    getStringValue(c, "time"),
                    getIntValue(c, "score1"),
                    getIntValue(c, "score2")
                    ));
        }
        return list;
    }

    public List<GameStats> getDarts(int id){
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_POINTS+" WHERE game_id="+id, null);
        List<GameStats> list = new ArrayList<GameStats>();
        while(c.moveToNext()){
            Player p1 = new Player();
            p1.setDart1(new Score(getIntValue(c,"p1_dart1_m"), getIntValue(c,"p1_dart1"), 0));
            p1.setDart2(new Score(getIntValue(c,"p1_dart2_m"), getIntValue(c,"p1_dart2"), 0));
            p1.setDart3(new Score(getIntValue(c,"p1_dart3_m"), getIntValue(c,"p1_dart3"), 0));
            p1.setPoints(getIntValue(c, "p1_points"));
            Player p2 = new Player();
            p2.setDart1(new Score(getIntValue(c,"p2_dart1_m"), getIntValue(c,"p2_dart1"), 0));
            p2.setDart2(new Score(getIntValue(c,"p2_dart2_m"), getIntValue(c,"p2_dart2"), 0));
            p2.setDart3(new Score(getIntValue(c,"p2_dart3_m"), getIntValue(c,"p2_dart3"), 0));
            p2.setPoints(getIntValue(c, "p2_points"));
            list.add(new GameStats(p1, p2));
        }
        return list;
    }

    public int getIntValue(Cursor c, String column){
        return c.getInt(c.getColumnIndex(column));
    }

    public String getStringValue(Cursor c, String column){
        return c.getString(c.getColumnIndex(column));
    }
}
