package com.game.darts1.ui.stats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.game.darts1.R;
import com.game.darts1.ui.home.Score;

import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder>  {

    private List<GameStats> stats;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView point1;
        public TextView point2;
        public TextView p1Dart1;
        public TextView p1Dart2;
        public TextView p1Dart3;
        public TextView p2Dart1;
        public TextView p2Dart2;
        public TextView p2Dart3;
        public ViewHolder(View itemView) {
            super(itemView);
            point1 = itemView.findViewById(R.id.p1_points);
            point2 = itemView.findViewById(R.id.p2_points);
            p1Dart1 = itemView.findViewById(R.id.p1_dart1);
            p1Dart2 = itemView.findViewById(R.id.p1_dart2);
            p1Dart3 = itemView.findViewById(R.id.p1_dart3);
            p2Dart1 = itemView.findViewById(R.id.p2_dart1);
            p2Dart2 = itemView.findViewById(R.id.p2_dart2);
            p2Dart3 = itemView.findViewById(R.id.p2_dart3);
        }
    }

    public StatsAdapter(List<GameStats> stats) {
        this.stats = stats;
    }

    @Override
    public StatsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.stats_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(StatsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        GameStats stat = stats.get(position);
        holder.point1.setText(String.valueOf(stat.getPlayer1().getPoints().getValue()));
        holder.point2.setText(String.valueOf(stat.getPlayer2().getPoints().getValue()));
        TextView[] texts = new TextView[]{
                holder.p1Dart1,
                holder.p1Dart2,
                holder.p1Dart3,
                holder.p2Dart1,
                holder.p2Dart2,
                holder.p2Dart3
        };
        Score[] s = new Score[]{
                stat.getPlayer1().getDart1().getValue(),
                stat.getPlayer1().getDart2().getValue(),
                stat.getPlayer1().getDart3().getValue(),
                stat.getPlayer2().getDart1().getValue(),
                stat.getPlayer2().getDart2().getValue(),
                stat.getPlayer2().getDart3().getValue()
        };
        for(int i = 0; i < 6; i++){
            if(s[i] != null){
                texts[i].setText(String.valueOf(s[i].getTotalScore()));
                if(s[i].getMultiplier() == 2){
                    texts[i].setBackgroundColor(ContextCompat.getColor(texts[i].getContext(), R.color.colorDouble));
                }else if(s[i].getMultiplier() == 3){
                    texts[i].setBackgroundColor(ContextCompat.getColor(texts[i].getContext(), R.color.colorTriple));
                }
            }else{
                texts[i].setText("");
            }
        }


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return stats.size();
    }
}
