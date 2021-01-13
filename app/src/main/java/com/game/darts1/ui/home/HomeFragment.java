package com.game.darts1.ui.home;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.game.darts1.MainActivity;
import com.game.darts1.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View dartTableBox;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        View view = root.findViewById(R.id.dart_table_box);
        dartTableBox = view;
        onTouchListener(view);

        setListeners(root);

        final TextView d1 = root.findViewById(R.id.dart1_value);
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setDartNumber(1);
                homeViewModel.setDart2Value(null);
                homeViewModel.setDart3Value(null);
                calculatePoints();
            }
        });
        final TextView d2 = root.findViewById(R.id.dart2_value);
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getDart1Value().getValue() != null) {
                    homeViewModel.setDartNumber(2);
                    homeViewModel.setDart3Value(null);
                    calculatePoints();
                }
            }
        });
        final TextView d3 = root.findViewById(R.id.dart3_value);
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getDart2Value().getValue() != null) {
                    homeViewModel.setDartNumber(3);
                }
            }
        });

        homeViewModel.getDartNumber().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                d1.setBackgroundColor(Color.parseColor("#ffffff"));
                d2.setBackgroundColor(Color.parseColor("#ffffff"));
                d3.setBackgroundColor(Color.parseColor("#ffffff"));
                switch (s){
                    case 1:
                        d1.setBackgroundColor(Color.parseColor("#A1B8C8"));
                        break;
                    case 2:
                        d2.setBackgroundColor(Color.parseColor("#A1B8C8"));
                        break;
                    case 3:
                        d3.setBackgroundColor(Color.parseColor("#A1B8C8"));
                        break;
                }
            }
        });

        Button nextBtn = root.findViewById(R.id.next_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchPlayer();
            }
        });

        startNewGame();

        return root;
    }

    private void setListeners(View root){
        final TextView player1Name = root.findViewById(R.id.player1);
        homeViewModel.getPlayer1().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                player1Name.setText(s);
            }
        });

        final TextView player2Name = root.findViewById(R.id.player2);
        homeViewModel.getPlayer2().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                player2Name.setText(s);
            }
        });

        setObserver(root, R.id.player1_score, homeViewModel.getPlayer1Score());
        setObserver(root, R.id.player2_score, homeViewModel.getPlayer2Score());
        setObserver(root, R.id.player1_points, homeViewModel.getPlayer1Points());
        setObserver(root, R.id.player2_points, homeViewModel.getPlayer2Points());
        setObserver(root, R.id.round, homeViewModel.getRound());
        setScoreObserver(root, R.id.dart1_value, homeViewModel.getDart1Value());
        setScoreObserver(root, R.id.dart2_value, homeViewModel.getDart2Value());
        setScoreObserver(root, R.id.dart3_value, homeViewModel.getDart3Value());

        final TextView currentPlayer = root.findViewById(R.id.current_player_name);
        homeViewModel.getCurrentPlayer().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                if(s == 1){
                    currentPlayer.setText(homeViewModel.getPlayer1().getValue());
                }else{
                    currentPlayer.setText(homeViewModel.getPlayer2().getValue());
                }
            }
        });
    }

    private void setObserver(View root, int id, MutableLiveData<Integer> mutable){
        final TextView textView = root.findViewById(id);
        mutable.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                textView.setText(String.valueOf(s));
            }
        });
    }

    private void setScoreObserver(View root, int id, MutableLiveData<Score> mutable){
        final TextView textView = root.findViewById(id);
        mutable.observe(getViewLifecycleOwner(), new Observer<Score>() {
            @Override
            public void onChanged(@Nullable Score s) {
                if(s == null){
                    textView.setText("");
                }else {
                    textView.setText(String.valueOf(s.getTotalScore()));
                }
            }
        });
    }

    //Table touch listener
    private void onTouchListener(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    Point target = new Point(x, y);
                    Point center = new Point(v.getWidth() / 2, v.getHeight() / 2);

                    double distance = getDistance(target, center);
                    Score score = getScore(center, target);
                    switch (homeViewModel.getDartNumber().getValue()){
                        case 1:
                            homeViewModel.setDart1Value(score);
                            break;
                        case 2:
                            homeViewModel.setDart2Value(score);
                            break;
                        case 3:
                            homeViewModel.setDart3Value(score);
                            break;
                    }

                    calculatePoints();

                    homeViewModel.setDartNumber(homeViewModel.getDartNumber().getValue() + 1);
                    //Log.d("SCORE", String.format("%.0f %dx%d", distance, score.getMultiplier(), score.getTotalScore()));

                    //View dart1 = v.findViewById(R.id.dart1);
                    //dart1.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    //params.setMargins(target.x - dart1.getWidth() / 2, target.y - dart1.getHeight() / 2, 0, 0);
                    //dart1.setLayoutParams(params);


                }
                return true;
            }
        });
    }

    private Score getScore(Point c, Point p){
        double d = getDistance(c, p);

        int ring = 0;
        for(int r: getRingRadius()){
            if(d < r){
                break;
            }else{
                ring++;
            }
        }

        int multiplier = 1;
        switch (ring){
            case 0:
                return new Score(2, 25);
            case 1:
                return new Score(1, 25);
            case 6:
                return new Score(0, 0);
            case 3:
                multiplier = 3;
                break;
            case 5:
                multiplier = 2;
                break;
        }

        return new Score(multiplier, getSlice(c, p));
    }

    private int getSlice(Point c, Point p){
        Point a = new Point(0, -100);
        Point b = new Point(p.x - c.x, p.y - c.y);

        double alpha = (a.x * b.x + a.y * b.y) / (Math.sqrt(a.x * a.x + a.y * a.y) * Math.sqrt(b.x * b.x + b.y * b.y));
        alpha = Math.toDegrees(Math.acos(alpha));
        double x = 360 / 20;
        int y = (int)Math.floor((alpha + x / 2) / x);
        int index = 0;
        if(p.x < c.x){
            index += 11;
        }
        return getPointValue(index + y);
    }

    private int getPointValue(int i){
        int[] values = new int[]{20, 1, 18, 4, 13, 6, 10, 15, 2, 17, 3, 20, 5, 12, 9, 14, 11, 8, 16, 7, 19, 3};
        return values[i];
    }

    private int[] getRingRadius(){
        double[] r = new double[]{0.0391, 0.1016, 0.3828, 0.4922, 0.6641, 0.7656};
        double width = dartTableBox.getWidth() / 2;

        return new int[]{
                (int) (width * r[0]),
                (int) (width * r[1]),
                (int) (width * r[2]),
                (int) (width * r[3]),
                (int) (width * r[4]),
                (int) (width * r[5]),
        };
    }

    private double getDistance(Point a, Point b){
        return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
       /* HomeViewModel model = ((MainActivity) getActivity()).getHomeViewModel();
        if(model != null){
            homeViewModel.setText(model.getText().getValue());
        }*/
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        //save fragment data on main activity
        ((MainActivity) getActivity()).setHomeViewModel(homeViewModel);
    }

    private void startNewGame(){
        homeViewModel.setPlayer1("Player 1");
        homeViewModel.setPlayer2("Player 2");
        homeViewModel.setPlayer1Score(0);
        homeViewModel.setPlayer1Score(0);
        homeViewModel.setCurrentPlayer(1);
        startNewRound();
    }

    private void startNewRound(){
        homeViewModel.setPlayer1StartPoints(501);
        homeViewModel.setPlayer2StartPoints(501);
        homeViewModel.setPlayer1Points(501);
        homeViewModel.setPlayer2Points(501);
        homeViewModel.setRound(1);
        resetDarts();
    }

    private void resetDarts(){
        homeViewModel.setDart1Value(null);
        homeViewModel.setDart2Value(null);
        homeViewModel.setDart3Value(null);
        homeViewModel.setDartNumber(1);
    }

    private void switchPlayer(){
        if(homeViewModel.getCurrentPlayer().getValue() == 1){
            homeViewModel.setPlayer1StartPoints(homeViewModel.getPlayer1Points().getValue());
            homeViewModel.setCurrentPlayer(2);
            resetDarts();
        }else{
            homeViewModel.setPlayer2StartPoints(homeViewModel.getPlayer2Points().getValue());
            homeViewModel.setCurrentPlayer(1);
            resetDarts();
        }
    }

    private void calculatePoints(){
        if(homeViewModel.getCurrentPlayer().getValue() == 1){
            int score = homeViewModel.getPlayer1StartPoints();
            switch (homeViewModel.getDartNumber().getValue()){
                case 1:
                    score -= homeViewModel.getDart1Value().getValue().getTotalScore();
                    homeViewModel.setPlayer1Points(score);
                    break;
                case 2:
                    score -= homeViewModel.getDart1Value().getValue().getTotalScore();
                    score -= homeViewModel.getDart2Value().getValue().getTotalScore();
                    homeViewModel.setPlayer1Points(score);
                    break;
                case 3:
                    score -= homeViewModel.getDart1Value().getValue().getTotalScore();
                    score -= homeViewModel.getDart2Value().getValue().getTotalScore();
                    score -= homeViewModel.getDart3Value().getValue().getTotalScore();
                    homeViewModel.setPlayer1Points(score);
                    break;
            }
        }else{
            int score = homeViewModel.getPlayer2StartPoints();
            switch (homeViewModel.getDartNumber().getValue()){
                case 1:
                    score -= homeViewModel.getDart1Value().getValue().getTotalScore();
                    homeViewModel.setPlayer2Points(score);
                    break;
                case 2:
                    score -= homeViewModel.getDart1Value().getValue().getTotalScore();
                    score -= homeViewModel.getDart2Value().getValue().getTotalScore();
                    homeViewModel.setPlayer2Points(score);
                    break;
                case 3:
                    score -= homeViewModel.getDart1Value().getValue().getTotalScore();
                    score -= homeViewModel.getDart2Value().getValue().getTotalScore();
                    score -= homeViewModel.getDart3Value().getValue().getTotalScore();
                    homeViewModel.setPlayer2Points(score);
                    break;
            }
        }
    }
}