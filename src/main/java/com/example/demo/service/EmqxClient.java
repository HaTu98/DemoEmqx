package com.example.demo.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component()
public class EmqxClient {
    String topic        = "MQTT/JavaTest";
    int qos             = 1;
    String broker       = "tcp://10.1.23.10:1883";
    String clientId     = "JavaSample";
    String username     = "admin";
    String password     = "public";

    private MqttClient client;
    private MqttConnectOptions conOpt;

    @PostConstruct
    public void initClient() throws MqttException {
        this.connect();

    }

    public void connect() throws MqttException {
        if (this.client == null) {
            this.client = new MqttClient(broker, clientId, new MemoryPersistence());
        }
        this.setConnectOptions();
        if (!client.isConnected()) {
            client.connect(this.conOpt);
        }

        this.client.subscribe(this.topic, qos);
        this.client.setCallback(new CallBackMessage(this));

    }

    private void setConnectOptions() {
        if (conOpt == null) {
            conOpt = new MqttConnectOptions();
            //conOpt.setCleanSession(true);
            conOpt.setUserName(this.username);
            conOpt.setPassword(this.password.toCharArray());
            conOpt.setAutomaticReconnect(true);
            conOpt.setKeepAliveInterval(10);
        }
    }

    public void reConnect() throws MqttException {
        if (this.client != null) {
            this.connect();
        }
     }

    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish
    }

}
