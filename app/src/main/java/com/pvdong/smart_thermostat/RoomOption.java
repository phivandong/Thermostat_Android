package com.pvdong.smart_thermostat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.travijuu.numberpicker.library.NumberPicker;

public class RoomOption extends AppCompatActivity {

    NumberPicker numberPicker;
    ImageView ivPower;
    ImageView ivBluetooth;
    LinearLayout timer;
    com.airbnb.lottie.LottieAnimationView homeBtn;
    //WaveLoadingView mWaveLoadingView;
    //com.suke.widget.SwitchButton fan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.room1_name);
        setContentView(R.layout.activity_room);
        super.onCreate(savedInstanceState);

        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        ivPower = (ImageView) findViewById(R.id.ivPower);
        ivBluetooth = (ImageView) findViewById(R.id.ivBluetooth);
        timer = (LinearLayout) findViewById(R.id.timer);
        homeBtn = (com.airbnb.lottie.LottieAnimationView) findViewById(R.id.homeBtn);
        //mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        //fan = (com.suke.widget.SwitchButton) findViewById(R.id.switch_fan);

        numberPicker.setMax(30);
        numberPicker.setMin(10);
        numberPicker.setUnit(1);
        numberPicker.setValue(25);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomOption.this, MainActivity.class));
            }
        });

        ivPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RoomOption.this, "Air Conditioner is on", Toast.LENGTH_SHORT).show();
            }
        });

        ivBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RoomOption.this, "Find device!", Toast.LENGTH_SHORT).show();
            }
        });

//        fan.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//                // wave
//            }
//        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomOption.this, Timer.class));
            }
        });
    }
}