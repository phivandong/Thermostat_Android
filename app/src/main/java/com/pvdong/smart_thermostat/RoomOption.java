package com.pvdong.smart_thermostat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.pvdong.smart_thermostat.Fragment.FanSpeed;
import com.suke.widget.SwitchButton;
import com.travijuu.numberpicker.library.NumberPicker;

import jp.hamcheesedev.outlinedtextview.CompatOutlinedTextView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class RoomOption extends AppCompatActivity {

    NumberPicker numberPicker;
    LinearLayout timer;
    com.airbnb.lottie.LottieAnimationView homeBtn;
    WaveLoadingView mWaveLoadingView;
    com.suke.widget.SwitchButton fan;
    CompatOutlinedTextView fanSpeed;

    LinearLayout powerOn, powerOff;

    String fanValue = "0";
    int fanChangeValue = 0;
    int switchControlValue = 0;
    int checkConnect = 0;
    int switchOffValue = 0;
    int fan_tt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.room1_name);
        setContentView(R.layout.activity_room);
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        timer = (LinearLayout) findViewById(R.id.timer);
        homeBtn = (com.airbnb.lottie.LottieAnimationView) findViewById(R.id.homeBtn);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        fan = (com.suke.widget.SwitchButton) findViewById(R.id.switchFan);
        powerOn = (LinearLayout) findViewById(R.id.powerOn);
        powerOff = (LinearLayout) findViewById(R.id.powerOff);
        fanSpeed = (CompatOutlinedTextView) findViewById(R.id.fanSpeed);

        numberPicker.setMax(30);
        numberPicker.setMin(10);
        numberPicker.setUnit(1);
        numberPicker.setValue(25);

        fanSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FanSpeed fanSpeed = new FanSpeed();
                fanSpeed.show(getSupportFragmentManager(),"FanDialogBox");
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RoomOption.this, Timer.class));
            }
        });

        powerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switchOffValue = 5;

                powerOn.setVisibility(View.GONE);
                powerOff.setVisibility(View.VISIBLE);
                fan.setChecked(false);

                fanValue = "0";
                fanSpeed.setText(fanValue + "%");
                int i = Integer.valueOf(fanValue);
                mWaveLoadingView.setProgressValue(i);

                if (checkConnect == 5)
                {
                    String string = "POWER OFF";
                    //sendReceive.write(string.getBytes());
                }
            }
        });

        powerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerOn.setVisibility(View.VISIBLE);
                powerOff.setVisibility(View.GONE);

                if (checkConnect == 5) {
                    String string = "POWER ON";
                    //sendReceive.write(string.getBytes());
                }
            }
        });

        fan.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                if (view.isChecked())
                {
                    powerOn.setVisibility(View.VISIBLE);
                    powerOff.setVisibility(View.GONE);

                    if (fan_tt == 0) {

                        switchOffValue = 0;

                        if (switchControlValue == 0) {
                            if (fanChangeValue == 0) {
                                fanValue = "70";
                                fanSpeed.setText(fanValue + "%");
                                int i = Integer.valueOf(fanValue);
                                mWaveLoadingView.setProgressValue(i);
                            } else {

                                int i = Integer.valueOf(fanValue);
                                mWaveLoadingView.setProgressValue(i);
                            }
                        }

                        if (checkConnect == 5) {
                            if (fanChangeValue == 0) {
                                fanValue = "70";
                                fanSendValue();
                            }

                        }

                    }
                } else {
                    if (fan_tt == 0) {

                        fanChangeValue = 0;
                        fanValue = "0";
                        fanSpeed.setText("0%");
                        int i = Integer.valueOf(fanValue);
                        mWaveLoadingView.setProgressValue(i);
                        switchControlValue = 0;
                        if (switchOffValue == 0) {
                            if (checkConnect == 5) {
                                String string = "FAN OFF";
                                //sendReceive.write(string.getBytes());
                            }
                        }
                    }
                }
            }
        });
    }

    public void dispatchInformation(String mesg) {
        fanChangeValue = 10;
        fanValue = mesg;
        int i = Integer.valueOf(mesg);
        mWaveLoadingView.setProgressValue(i);
        fanSpeed.setText(mesg + "%");

        if (i != 0) {
            fan.setChecked(true);
            switchControlValue = 5;
        }

        if (i == 0) {
            fan.setChecked(false);
            switchControlValue = 0;
        }

        fanSendValue();

    }

    public String getSpeed() {

        return fanValue;

    }

    public void fanSendValue() {
        int value_control_fan_speed = Integer.valueOf(fanValue);
        if (checkConnect == 5) {
            if (value_control_fan_speed ==0) {
                String string = "FAN OFF";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 10 && value_control_fan_speed !=0 || value_control_fan_speed == 10) {
                String string = "ONE";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 20 && value_control_fan_speed > 10 || value_control_fan_speed == 20) {
                String string = "TWO";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 30 && value_control_fan_speed > 20 || value_control_fan_speed == 30) {
                String string = "THREE";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 40 && value_control_fan_speed > 30 || value_control_fan_speed == 40) {
                String string = "FOUR";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 50 && value_control_fan_speed > 40 || value_control_fan_speed == 50) {
                String string = "FIVE";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 60 && value_control_fan_speed > 50 || value_control_fan_speed == 60) {
                String string = "SIX";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 70 && value_control_fan_speed > 60 || value_control_fan_speed == 70) {
                String string = "SEVEN";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 80 && value_control_fan_speed > 70 || value_control_fan_speed == 80) {
                String string = "EIGHT";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 90 && value_control_fan_speed > 80 || value_control_fan_speed == 90) {
                String string = "NINE";
                //sendReceive.write(string.getBytes());
            }

            if (value_control_fan_speed < 100 && value_control_fan_speed > 90 || value_control_fan_speed == 100) {
                String string = "TEN";
                //sendReceive.write(string.getBytes());
            }
        }
    }
}