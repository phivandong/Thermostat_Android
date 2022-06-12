package com.pvdong.smart_thermostat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.pvdong.smart_thermostat.Fragment.FanSpeed;
import com.suke.widget.SwitchButton;
import com.travijuu.numberpicker.library.NumberPicker;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import jp.hamcheesedev.outlinedtextview.CompatOutlinedTextView;
import me.itangqi.waveloadingview.WaveLoadingView;

public class RoomOption extends AppCompatActivity {

    MqttAndroidClient client;

    NumberPicker temperaturePicker;
    LinearLayout timer;
    com.airbnb.lottie.LottieAnimationView homeBtn;
    WaveLoadingView mWaveLoadingView;
    com.suke.widget.SwitchButton fan;
    com.suke.widget.SwitchButton switchHumidity;
    CompatOutlinedTextView fanSpeed;
    Button BtnSetTemperature;

    LinearLayout powerOn, powerOff;

    String fanValue = "0";
    int fanChangeValue = 0;
    int switchControlValue = 0;
    int checkConnect = 0;
    int switchOffValue = 0;
    int fan_tt = 0;

    boolean isTurnedOn = false;
    boolean isHumidityModeTurnedOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(R.string.room1_name);
        setContentView(R.layout.activity_room);
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        temperaturePicker = (NumberPicker) findViewById(R.id.temperaturePicker);
        timer = (LinearLayout) findViewById(R.id.timer);
        homeBtn = (com.airbnb.lottie.LottieAnimationView) findViewById(R.id.homeBtn);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        fan = (com.suke.widget.SwitchButton) findViewById(R.id.switchFan);
        switchHumidity = (com.suke.widget.SwitchButton) findViewById(R.id.switchHumidity);
        powerOn = (LinearLayout) findViewById(R.id.powerOn);
        powerOff = (LinearLayout) findViewById(R.id.powerOff);
        fanSpeed = (CompatOutlinedTextView) findViewById(R.id.fanSpeed);
        BtnSetTemperature = (Button) findViewById(R.id.BtnSetTemperature);

        temperaturePicker.setMax(30);
        temperaturePicker.setMin(10);
        temperaturePicker.setUnit(1);
        temperaturePicker.setValue(25);

        String clientId = MqttClient.generateClientId();
        MqttConnectOptions options = new MqttConnectOptions();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://54.179.56.27:1883", clientId);

        BtnSetTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject postData = new JSONObject();
                try {
                    postData.put("isTurnedOn", isTurnedOn);
                    postData.put("fanValue", fanValue);
                    postData.put("temperature", temperaturePicker.getValue());
                    postData.put("isHumidityModeTurnedOn", isHumidityModeTurnedOn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sendOverMqtt(postData, options, client);
            }
        });

        fanSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FanSpeed fanSpeed = new FanSpeed();
                fanSpeed.show(getSupportFragmentManager(),"FanDialogBox");

                isTurnedOn = true;
                JSONObject postData = new JSONObject();
                try {
                    postData.put("isTurnedOn", isTurnedOn);
                    postData.put("fanValue", fanSpeed.getSpeedValue());
                    postData.put("temperature", temperaturePicker.getValue());
                    postData.put("isHumidityModeTurnedOn", isHumidityModeTurnedOn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sendOverMqtt(postData, options, client);
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
                isTurnedOn = true;

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

                JSONObject postData = new JSONObject();
                try {
                    postData.put("isTurnedOn", isTurnedOn);
                    postData.put("fanValue", fanValue);
                    postData.put("temperature", temperaturePicker.getValue());
                    postData.put("isHumidityModeTurnedOn", isHumidityModeTurnedOn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sendOverMqtt(postData, options, client);
            }
        });

        powerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTurnedOn = false;
                powerOn.setVisibility(View.VISIBLE);
                powerOff.setVisibility(View.GONE);

                if (checkConnect == 5) {
                    String string = "POWER ON";
                    //sendReceive.write(string.getBytes());
                }

                JSONObject postData = new JSONObject();
                try {
                    postData.put("isTurnedOn", isTurnedOn);
                    postData.put("fanValue", fanValue);
                    postData.put("temperature", temperaturePicker.getValue());
                    postData.put("isHumidityModeTurnedOn", isHumidityModeTurnedOn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sendOverMqtt(postData, options, client);
            }
        });

        fan.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (view.isChecked()) {
                    isTurnedOn = true;
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
                    isTurnedOn = false;
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

                JSONObject postData = new JSONObject();
                try {
                    postData.put("isTurnedOn", isTurnedOn);
                    postData.put("fanValue", fanValue);
                    postData.put("temperature", temperaturePicker.getValue());
                    postData.put("isHumidityModeTurnedOn", isHumidityModeTurnedOn);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sendOverMqtt(postData, options, client);
            }
        });

        switchHumidity.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (view.isChecked()) {
                    isHumidityModeTurnedOn = true;
                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("isTurnedOn", "true");
                        postData.put("fanValue", fanValue);
                        postData.put("temperature", temperaturePicker.getValue());
                        postData.put("isHumidityModeTurnedOn", isHumidityModeTurnedOn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sendOverMqtt(postData, options, client);
                } else {
                    isHumidityModeTurnedOn = false;
                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("isTurnedOn", "true");
                        postData.put("fanValue", fanValue);
                        postData.put("temperature", temperaturePicker.getValue());
                        postData.put("isHumidityModeTurnedOn", isHumidityModeTurnedOn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sendOverMqtt(postData, options, client);
                }
            }
        });
    }

    public void sendOverMqtt(JSONObject postData, MqttConnectOptions options, MqttAndroidClient client) {
        options.setUserName("admin");
        options.setPassword("public".toCharArray());
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    String topicPub = "/home/maker_dong/bed_room/thermostat";
                    send(postData, topicPub);
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void send(JSONObject data, String topic) {
        String payload = data.toString();
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
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