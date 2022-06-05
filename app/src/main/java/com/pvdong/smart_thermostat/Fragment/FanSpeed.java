package com.pvdong.smart_thermostat.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pvdong.smart_thermostat.RoomOption;
import com.pvdong.smart_thermostat.R;


public class FanSpeed extends androidx.fragment.app.DialogFragment {

    ImageView close;
    String speedValue = "0";
    TextView speed, cancel, set;
    SeekBar seekBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fan_speed_fragment,container,false);


        RoomOption activity = (RoomOption) getActivity();
        String getSpeed = activity.getSpeed();

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        speed = (TextView)view.findViewById(R.id.speed);
        cancel = (TextView)view.findViewById(R.id.cancel);
        set = (TextView)view.findViewById(R.id.set);
        close = (ImageView)view.findViewById(R.id.close);
        seekBar = (SeekBar)view.findViewById(R.id.seekbar);


        speed.setText(getSpeed + "%");
        speedValue = getSpeed;
        int i = Integer.valueOf(getSpeed);
        seekBar.setProgress(i);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                speedValue = String.valueOf(progress);
                speed.setText(speedValue + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RoomOption)getActivity()).dispatchInformation(speedValue);
                dismiss();
            }
        });

        return view;
    }

}
