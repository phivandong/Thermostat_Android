package com.pvdong.thermostat_android.timerlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pvdong.thermostat_android.R;

public class TimerListFragment extends Fragment {
//    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
//    private AlarmsListViewModel alarmsListViewModel;
    private RecyclerView timerRecyclerView;
    private Button addTimer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer_list, container, false);

        timerRecyclerView = view.findViewById(R.id.fragment_timer_list_recyclerView);
        timerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addTimer = view.findViewById(R.id.fragment_timer_list_addTimer);
        addTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_timerListFragment_to_pickTimeFragment);
            }
        });

        return view;
    }
}