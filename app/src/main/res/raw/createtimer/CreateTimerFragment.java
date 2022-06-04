package com.pvdong.thermostat_android.createtimer;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.pvdong.thermostat_android.R;

import java.util.Locale;

public class CreateTimerFragment extends Fragment {

    Button btnPickStartTime;
    Button btnPickStopTime;
    Button btnScheduleTimer;
    LinearLayout recurringOptions;
    CheckBox timerRecurringCheck;

    int startHour, startMinute, stopHour, stopMinute;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_create_timer, container, false);

        btnPickStartTime = view.findViewById(R.id.btnPickStartTime);
        btnPickStopTime = view.findViewById(R.id.btnPickStopTime);
        btnScheduleTimer = view.findViewById(R.id.btnScheduleTimer);
        recurringOptions = view.findViewById(R.id.recurringOptions);
        timerRecurringCheck = view.findViewById(R.id.timerRecurringCheck);

        timerRecurringCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    recurringOptions.setVisibility(View.VISIBLE);
                } else {
                    recurringOptions.setVisibility(View.GONE);
                }
            }
        });

        btnScheduleTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_pickTimeFragment_to_timerListFragment);
            }
        });

        return view;
    }

    public void popStartTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                startHour = selectedHour;
                startMinute = selectedMinute;
                btnPickStartTime.setText(String.format(Locale.getDefault(), "%02d:%02d", startHour, startMinute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), style, onTimeSetListener, startHour, startMinute, true);

        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void popStopTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                stopHour = selectedHour;
                stopMinute = selectedMinute;
                btnPickStopTime.setText(String.format(Locale.getDefault(), "%02d:%02d", stopHour, stopMinute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), style, onTimeSetListener, startHour, startMinute, true);

        timePickerDialog.setTitle("Select Stop Time");
        timePickerDialog.show();
    }
}