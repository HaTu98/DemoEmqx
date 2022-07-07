package com.example.demo.controller;

import com.example.demo.service.EmqxClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class MqttController {

    @Autowired
    private EmqxClient emqxClient;

    @GetMapping("/send-message")
    public void sendMessage() throws MqttException {
        String content = "Message from MqttPublishSample";
        emqxClient.sendMessage(content);
    }
}
