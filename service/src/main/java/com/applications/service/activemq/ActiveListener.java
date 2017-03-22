package com.applications.service.activemq;

import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by hukaisheng on 2017/3/22.
 */
@Slf4j
public class ActiveListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage txtMessage = (TextMessage)message;
        try {
            log.info ("get message " + txtMessage.getText());
            System.out.println ("get message " + txtMessage.getText());
        } catch (JMSException e) {
            log.error("error {}", e);
        }
    }
}
