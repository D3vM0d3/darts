package com.game.darts1.ui.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.game.darts1.R;
import com.game.darts1.ui.db.Database;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder>  {

    private List<Game> games;
    private Database db;

    public class ViewHolder extends RecyclerView.ViewHolder {
       public TextView player1;
       public TextView player2;
       public TextView score1;
       public TextView score2;
       public TextView time;
       public Button details;
       public RecyclerView dartList;
       public boolean visible;
        public ViewHolder(View itemView) {
            super(itemView);
            player1 = itemView.findViewById(R.id.player1);
            player2 = itemView.findViewById(R.id.player2);
            score1 = itemView.findViewById(R.id.p1_score);
            score2 = itemView.findViewById(R.id.p2_score);
            time = itemView.findViewById(R.id.time);
            details = itemView.findViewById(R.id.details);
            dartList = itemView.findViewById(R.id.dart_list);

            StatsAdapter gamesAdapter = new StatsAdapter(new ArrayList<GameStats>());
            dartList.setAdapter(gamesAdapter);
            dartList.setLayoutManager(new LinearLayoutManager(dartList.getContext()));
        }
    }

    public GamesAdapter(List<Game> games, Database db) {
        this.games = games;
        this.db = db;
    }

    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.game_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final GamesAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        final Game game = games.get(position);
        holder.player1.setText(game.getPlayer1());
        holder.player2.setText(game.getPlayer2());
        holder.score1.setText(String.valueOf(game.getScore1()));
        holder.score2.setText(String.valueOf(game.getScore2()));
        holder.time.setText(game.getTime());

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.visible){
                    holder.dartList.setVisibility(View.GONE);
                    holder.visible = false;
                }else{
                    holder.dartList.setVisibility(View.VISIBLE);
                    StatsAdapter gamesAdapter = new StatsAdapter(db.getDarts(game.getId()));
                    holder.dartList.setAdapter(gamesAdapter);
                    holder.dartList.setLayoutManager(new LinearLayoutManager(holder.dartList.getContext()));

                    holder.visible = true;
                }
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return games.size();
    }
}
