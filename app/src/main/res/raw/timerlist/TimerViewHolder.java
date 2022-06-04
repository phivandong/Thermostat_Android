package com.pvdong.thermostat_android.timerlist;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pvdong.thermostat_android.R;
import com.pvdong.thermostat_android.timerdata.Timer;

public class TimerViewHolder extends RecyclerView.ViewHolder {
    private TextView timerTime;
    private ImageView timerRecurring;
    private TextView timerRecurringDays;

    Switch timerStarted;

    private OnToggleTimerListener listener;

    public TimerViewHolder(@NonNull View itemView) {
        super(itemView);

        timerTime = itemView.findViewById(R.id.timer_item_time);
        timerRecurring = itemView.findViewById(R.id.timer_item_recurring);
        timerRecurringDays = itemView.findViewById(R.id.timer_item_recurringDays);
        timerStarted = itemView.findViewById(R.id.itemTimerStarted);

        this.listener = listener;
    }

    public void bind(Timer timer) {
        String timerText = String.format("%02d:%02d", timer.getHour(), timer.getMinute());

        timerTime.setText(timerText);
        timerStarted.setChecked(timer.isStarted());

        if (timer.isRecurring()) {
            timerRecurring.setImageResource(R.drawable.ic_repeat_black_24dp);
            timerRecurringDays.setText(timer.getRecurringDaysText());
        } else {
            timerRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
            timerRecurringDays.setText("Once Off");
        }

        timerStarted.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listener.onToggle(timer);
            }
        }));
    }
}
