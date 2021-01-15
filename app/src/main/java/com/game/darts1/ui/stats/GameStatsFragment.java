package com.game.darts1.ui.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.game.darts1.R;
import com.game.darts1.ui.db.Database;

import java.util.List;

public class GameStatsFragment extends Fragment {

    private GameStatsViewModel gameStatsViewModel;
    private Database db;
    private List<Game> games;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameStatsViewModel =
                ViewModelProviders.of(this).get(GameStatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);

        db = new Database();
        db.connect(ContextCompat.getDataDir(getContext()).toString());
        games = db.getGames();

        RecyclerView list = root.findViewById(R.id.game_list);
        GamesAdapter gamesAdapter = new GamesAdapter(games, db);
        list.setAdapter(gamesAdapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }
}