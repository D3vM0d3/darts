package com.game.darts1.ui.home;

import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.game.darts1.MainActivity;
import com.game.darts1.R;
import com.game.darts1.ui.db.Database;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View dartTableBox;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        if(activity.getHomeViewModel() == null) {
            homeViewModel =
                    ViewModelProviders.of(this).get(HomeViewModel.class);
            activity.setHomeViewModel(homeViewModel);
        }else{
            homeViewModel = activity.getHomeViewModel();
        }

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        View view = root.findViewById(R.id.table_image);
        dartTableBox = view;
        onTouchListener(view);

        setListeners(root);

        //select active dart
        final TextView p1d1 = root.findViewById(R.id.p1_dart1);
        p1d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 1){
                    homeViewModel.getPlayer1().setDartNumber(1);
                    homeViewModel.getPlayer1().setDart2(null);
                    homeViewModel.getPlayer1().setDart3(null);
                    calculatePoints();
                }
            }
        });
        final TextView p1d2 = root.findViewById(R.id.p1_dart2);
        p1d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 1 && homeViewModel.getPlayer1().getDart1().getValue() != null){
                    homeViewModel.getPlayer1().setDartNumber(2);
                    homeViewModel.getPlayer1().setDart3(null);
                    calculatePoints();
                }
            }
        });
        final TextView p1d3 = root.findViewById(R.id.p1_dart3);
        p1d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 1 && homeViewModel.getPlayer1().getDart2().getValue() != null){
                    homeViewModel.getPlayer1().setDartNumber(3);
                    calculatePoints();
                }
            }
        });

        final TextView p2d1 = root.findViewById(R.id.p2_dart1);
        p2d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 2){
                    homeViewModel.getPlayer2().setDartNumber(1);
                    homeViewModel.getPlayer2().setDart2(null);
                    homeViewModel.getPlayer2().setDart3(null);
                    calculatePoints();
                }
            }
        });
        final TextView p2d2 = root.findViewById(R.id.p2_dart2);
        p2d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 2 && homeViewModel.getPlayer2().getDart1().getValue() != null){
                    homeViewModel.getPlayer2().setDartNumber(2);
                    homeViewModel.getPlayer2().setDart3(null);
                    calculatePoints();
                }
            }
        });
        final TextView p2d3 = root.findViewById(R.id.p2_dart3);
        p2d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 2 && homeViewModel.getPlayer2().getDart2().getValue() != null){
                    homeViewModel.getPlayer2().setDartNumber(3);
                    calculatePoints();
                }
            }
        });

        homeViewModel.getPlayer1().getDartNumber().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                p1d1.setBackgroundColor(0);
                p1d2.setBackgroundColor(0);
                p1d3.setBackgroundColor(0);
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 1){
                    switch (s){
                        case 1:
                            p1d1.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedDart));
                            break;
                        case 2:
                            p1d2.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedDart));
                            break;
                        case 3:
                            p1d3.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedDart));
                            break;
                    }
                }
            }
        });

        homeViewModel.getPlayer2().getDartNumber().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                p2d1.setBackgroundColor(0);
                p2d2.setBackgroundColor(0);
                p2d3.setBackgroundColor(0);
                if(homeViewModel.getCurrentPlayerNumber().getValue() == 2) {
                    switch (s) {
                        case 1:
                            p2d1.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedDart));
                            break;
                        case 2:
                            p2d2.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedDart));
                            break;
                        case 3:
                            p2d3.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedDart));
                            break;
                    }
                }
            }
        });

        homeViewModel.getCurrentPlayerNumber().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                ImageView p1Indicator = root.findViewById(R.id.p1_indicator);
                ImageView p2Indicator = root.findViewById(R.id.p2_indicator);
                View p1 = root.findViewById(R.id.player1);
                View p2 = root.findViewById(R.id.player2);
                p1.setBackgroundColor(0);
                p2.setBackgroundColor(0);
                if(s == 1){
                    p1Indicator.setVisibility(View.VISIBLE);
                    p2Indicator.setVisibility(View.INVISIBLE);
                    p1.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedPlayer));
                }else{
                    p1Indicator.setVisibility(View.INVISIBLE);
                    p2Indicator.setVisibility(View.VISIBLE);
                    p2.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorSelectedPlayer));
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

        if(!homeViewModel.isStarted()){
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setTitle(getResources().getString(R.string.title_newgame));
            dlg.setPositiveButton(getResources().getString(R.string.start_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    BottomNavigationView nav = getActivity().findViewById(R.id.nav_view);
                    nav.setSelectedItemId(R.id.navigation_newgame);
                }
            });
            //dlg.setCancelable(false);
            dlg.setNegativeButton("Cancel", null);
            dlg.show();
        }

        homeViewModel.getDb().connect(ContextCompat.getDataDir(getContext()).toString());

        return root;
    }

    private void setListeners(View root){
        final TextView player1Name = root.findViewById(R.id.p1_name);
        homeViewModel.getPlayer1().getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                player1Name.setText(s);
            }
        });


        final TextView player2Name = root.findViewById(R.id.p2_name);
        homeViewModel.getPlayer2().getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                player2Name.setText(s);
            }
        });


        setObserver(root, R.id.player1_score, homeViewModel.getPlayer1().getScore());
        setObserver(root, R.id.player2_score, homeViewModel.getPlayer2().getScore());
        setObserver(root, R.id.player1_points, homeViewModel.getPlayer1().getPoints());
        setObserver(root, R.id.player2_points, homeViewModel.getPlayer2().getPoints());
        setObserver(root, R.id.round, homeViewModel.getRound());
        setScoreObserver(root, R.id.p1_dart1, homeViewModel.getPlayer1().getDart1());
        setScoreObserver(root, R.id.p1_dart2, homeViewModel.getPlayer1().getDart2());
        setScoreObserver(root, R.id.p1_dart3, homeViewModel.getPlayer1().getDart3());
        setScoreObserver(root, R.id.p2_dart1, homeViewModel.getPlayer2().getDart1());
        setScoreObserver(root, R.id.p2_dart2, homeViewModel.getPlayer2().getDart2());
        setScoreObserver(root, R.id.p2_dart3, homeViewModel.getPlayer2().getDart3());
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

    //Table touch listener, throw dart
    private void onTouchListener(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(!homeViewModel.isStarted()){
                        return true;
                    }
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    Point target = new Point(x, y);
                    Point center = getCenter(v);

                    Score score = getScore(center, target);
                    score.setPosition(target);
                    switch (homeViewModel.getCurrentPlayer().getDartNumber().getValue()){
                        case 1:
                            homeViewModel.getCurrentPlayer().setDart1(score);
                            break;
                        case 2:
                            homeViewModel.getCurrentPlayer().setDart2(score);
                            break;
                        case 3:
                            homeViewModel.getCurrentPlayer().setDart3(score);
                            break;
                    }

                    calculatePoints();
                    homeViewModel.getCurrentPlayer().setDartNumber(homeViewModel.getCurrentPlayer().getDartNumber().getValue() + 1);
                    showDarts();
                }
                return true;
            }
        });
    }

    private void showDarts(){
        View v = getActivity().findViewById(R.id.table_box);
        View d1 = getActivity().findViewById(R.id.img_dart1);
        View d2 = getActivity().findViewById(R.id.img_dart2);
        View d3 = getActivity().findViewById(R.id.img_dart3);
        Point center = getCenter(v);
        showDart(d1, center, homeViewModel.getCurrentPlayer().getDart1().getValue());
        showDart(d2, center, homeViewModel.getCurrentPlayer().getDart2().getValue());
        showDart(d3, center, homeViewModel.getCurrentPlayer().getDart3().getValue());
    }

    private void showDart(View dart, Point center, Score score){
        int[] angles = new int[]{18, 144, 180, 54, 342, 90, 216, 252, 306, 108, 270, 324, 72, 288, 126, 234, 162, 36, 198, 0};
        double x = 0;
        double y = 0;
        if(score != null){
            dart.setVisibility(View.VISIBLE);
            if(score.getValue() <= 20 && score.getValue() > 0) {
                double angle = Math.toRadians(angles[score.getValue() - 1]);
                double length = 0;
                if(score.getRing() < 6){
                    length = (getRingRadius()[score.getRing()] + getRingRadius()[score.getRing() - 1]) / 2;
                }
                x = center.x + Math.sin(angle) * length;
                y = center.y -  Math.cos(angle) * length;
            }else if(score.getTotalScore() == 25) {
                x = score.getPosition().x;
                y = score.getPosition().y;

                double d = getDistance(center, new Point((int)x, (int)y));
                double r = getRingRadius()[0] + getRingRadius()[0] / 2;
                Point px = new Point((int)x - center.x, (int)y - center.y);
                if(d > r) {
                    x = px.x / (d / r) + center.x;
                    y = px.y / (d / r) + center.y;
                }
            }else{
                    x = score.getPosition().x;
                    y = score.getPosition().y;
                }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins((int) x - dart.getWidth() / 2, (int) y - dart.getHeight(), 0, 0);
            dart.setLayoutParams(params);
        }else{
            dart.setVisibility(View.INVISIBLE);
        }
    }

    private Point getCenter(View v){
        return new Point(v.getWidth() / 2, v.getHeight() / 2);
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
                return new Score(2, 25, 0);
            case 1:
                return new Score(1, 25, 1);
            case 6:
                return new Score(0, 0, 6);
            case 3:
                multiplier = 3;
                break;
            case 5:
                multiplier = 2;
                break;
        }

        return new Score(multiplier, getSlice(c, p), ring);
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
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    private void switchPlayer(){
        Player player = homeViewModel.getCurrentPlayer();
        if(player.getLastDart() == null){
            return;
        }
        if(player.getPoints().getValue() == 0){
            if(player.getLastDart().getMultiplier() == 2){
                //win
                player.setScore(player.getScore().getValue() + 1);
                homeViewModel.updateGame();
                homeViewModel.startNewLeg();
                showDarts();
                return;
            }
        }

        if(player.getPoints().getValue() <= 1){
            player.setPoints(player.getStartPoints());
        }

        //new round
        if(homeViewModel.getPlayer1().getDart1().getValue() != null && homeViewModel.getPlayer2().getDart1().getValue() != null){
            homeViewModel.startNewRound();
        }else{
            homeViewModel.switchPlayer();
        }
        showDarts();
    }

    private void calculatePoints(){
            int score = homeViewModel.getCurrentPlayer().getStartPoints();
            switch (homeViewModel.getCurrentPlayer().getDartNumber().getValue()){
                case 1:
                    score -= getTotalScore(homeViewModel.getCurrentPlayer().getDart1().getValue());
                    homeViewModel.getCurrentPlayer().setPoints(score);
                    break;
                case 2:
                    score -= getTotalScore(homeViewModel.getCurrentPlayer().getDart1().getValue());
                    score -= getTotalScore(homeViewModel.getCurrentPlayer().getDart2().getValue());
                    homeViewModel.getCurrentPlayer().setPoints(score);
                    break;
                case 3:
                    score -= getTotalScore(homeViewModel.getCurrentPlayer().getDart1().getValue());
                    score -= getTotalScore(homeViewModel.getCurrentPlayer().getDart2().getValue());
                    score -= getTotalScore(homeViewModel.getCurrentPlayer().getDart3().getValue());
                    homeViewModel.getCurrentPlayer().setPoints(score);
                    break;
            }
    }

    private int getTotalScore(Score score){
        if(score != null){
            return score.getTotalScore();
        }
        return 0;
    }
}