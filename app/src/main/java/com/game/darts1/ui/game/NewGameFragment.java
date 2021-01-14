package com.game.darts1.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.game.darts1.MainActivity;
import com.game.darts1.R;
import com.game.darts1.ui.home.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class NewGameFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_newgame, container, false);

        Button btn = root.findViewById(R.id.btn_newgame);
        final HomeViewModel model = ((MainActivity)getActivity()).getHomeViewModel();
        final TextView p1 = root.findViewById(R.id.player1_name);
        final TextView p2 = root.findViewById(R.id.player2_name);
        if(model != null) {
            p1.setText(model.getPlayer1().getName().getValue());
            p2.setText(model.getPlayer2().getName().getValue());
        }else{
            p1.setText(getResources().getString(R.string.player1));
            p2.setText(getResources().getString(R.string.player2));
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView nv = getActivity().findViewById(R.id.nav_view);
                ((MainActivity)getActivity()).getHomeViewModel().startNewGame(p1.getText().toString(), p2.getText().toString());
                nv.setSelectedItemId(R.id.navigation_home);

            }
        });

        return root;
    }
}