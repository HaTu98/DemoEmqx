package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@Scope("Prototype")
public class CallBackMessage implements MqttCallbackExtended {

    private final EmqxClient client;

    public CallBackMessage(EmqxClient client) {
        this.client = client;
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.info(" Connection is broken , Reconnect after 10s");
        while (true) {
            try {
                Thread.sleep(10000);
                this.client.reConnect();
                log.info("Reconnect success");
                break;
            } catch ( Exception e) {
                log.warn("Reconnect fail, the cause reason: {}", e.getMessage());
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.info("Arrived message");
        System.out.println("\nReceived a Message!" +
                "\n\tTime:    " + new Date() +
                "\n\tTopic:   " + topic +
                "\n\tMessage: " + new String(message.getPayload()) +
                "\n\tQoS:     " + message.getQos() + "\n");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        String[] topics = token.getTopics();
        for (String topic : topics) {

            log.info(" To the subject ：" + topic + " Message sent successfully ！");
        }
        try {
            MqttMessage message = token.getMessage();
            if (message != null) {
                byte[] payload = message.getPayload();
                String s = new String(payload, StandardCharsets.UTF_8);
                log.info(" The content of the message is ：" + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        log.info("Connect {}, uri {}", reconnect, serverURI);
    }
}
