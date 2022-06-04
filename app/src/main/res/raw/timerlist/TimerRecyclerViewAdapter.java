package com.pvdong.thermostat_android.timerlist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pvdong.thermostat_android.timerdata.Timer;

import java.util.ArrayList;
import java.util.List;

public class TimerRecyclerViewAdapter extends RecyclerView.Adapter<TimerViewHolder> {

    private List<Timer> timers;
    private OnToggleTimerListener listener;

    public TimerRecyclerViewAdapter(OnToggleTimerListener listener) {
        this.timers = new ArrayList<Timer>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TimerViewHolder holder, int position) {
        Timer timer = timers.get(position);
        holder.bind(timer);
    }

    @Override
    public int getItemCount() {
        return timers.size();
    }

    public void setTimers(List<Timer> timers) {
        this.timers = timers;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull TimerViewHolder holder) {
        super.onViewRecycled(holder);
        holder.timerStarted.setOnCheckedChangeListener(null);
    }
}
