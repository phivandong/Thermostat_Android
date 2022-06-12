package com.pvdong.smart_thermostat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class Config extends AppCompatActivity {

    MqttAndroidClient client;
    EditText etUsername, etPassword, etTopicSub, etTopicPub, etSendMsg;
    TextView tvClientID, tvGetMsg, tvStatus;
    String data = "";
    Button btnSend, btnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        String clientId = MqttClient.generateClientId();
        tvClientID = (TextView) findViewById(R.id.tvClientID);
        tvClientID.setText(clientId);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etTopicSub = (EditText) findViewById(R.id.etTopicSub);
        etTopicPub = (EditText) findViewById(R.id.etTopicPub);
        etSendMsg = (EditText) findViewById(R.id.etSendMsg);
        tvGetMsg = (TextView) findViewById(R.id.tvGetMsg);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSub = (Button) findViewById(R.id.btnSub);

        tvGetMsg.setMovementMethod(new ScrollingMovementMethod());

        MqttConnectOptions options = new MqttConnectOptions();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://54.179.56.27:1883", clientId);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etUsername.getText().toString().equals("") && !etPassword.getText().toString().equals("") && !etTopicSub.getText().toString().equals("")&& !etTopicPub.getText().toString().equals("")) {
                    options.setUserName(etUsername.getText().toString());
                    options.setPassword(etPassword.getText().toString().toCharArray());
                    try {
                        IMqttToken token = client.connect(options);
                        token.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                // We are connected
                                String data = etSendMsg.getText().toString();
                                String topicPub = etTopicPub.getText().toString();
                                send(data, topicPub);
                            }
                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                // Something went wrong e.g. connection timeout or firewall problems
                                tvStatus.setText("Status: Disconnected");

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                        tvStatus.setText("Status: Disconnected");
                    }
                }

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    IMqttToken token = client.connect(options);
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            String topicSub= etTopicSub.getText().toString();
                            get(topicSub);
                        }
                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            tvStatus.setText("Status: Disconnected");

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                    tvStatus.setText("Status: Disconnected");
                }

            }
        });

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                tvStatus.setText("Status: Disconnected");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                tvStatus.setText("Status: Connected");
                data += String.valueOf(message) + "\n";
                tvGetMsg.setText(data);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    public void send(String data,String topic){

        String payload = data;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public void get(String a){
        String topic = a;
        int qos = 0;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    //Toast.makeText(getBaseContext(), "The message was published", Toast.LENGTH_SHORT).show();
                    // The message was published
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getBaseContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
