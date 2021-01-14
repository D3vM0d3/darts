package com.game.darts1.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.game.darts1.MainActivity;
import com.game.darts1.R;
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView nv = getActivity().findViewById(R.id.nav_view);
                ((MainActivity)getActivity()).getHomeViewModel().startNewGame("P1", "P2");
                nv.setSelectedItemId(R.id.navigation_home);
            }
        });

        return root;
    }
}