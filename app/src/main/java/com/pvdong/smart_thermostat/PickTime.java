package com.pvdong.smart_thermostat;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import java.util.Locale;

public class PickTime extends AppCompatActivity {

    Button btnPickStartTime, btnPickStopTime, btnScheduleTimer;
    int startHour, startMinute, stopHour, stopMinute;
    LinearLayout recurringOptions;
    CheckBox timerRecurringCheck;

    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Option");
        setContentView(R.layout.activity_pick_time);
        super.onCreate(savedInstanceState);

        btnPickStartTime = findViewById(R.id.btnPickStartTime);
        btnPickStopTime = findViewById(R.id.btnPickStopTime);
        btnScheduleTimer = findViewById(R.id.btnScheduleTimer);
        recurringOptions = findViewById(R.id.recurringOptions);
        timerRecurringCheck = findViewById(R.id.timerRecurringCheck);

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
            }
        });
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, startHour, startMinute, true);

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

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, startHour, startMinute, true);

        timePickerDialog.setTitle("Select Stop Time");
        timePickerDialog.show();
    }
}
