package com.applications.service.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by hukaisheng on 2017/3/22.
 */
@Slf4j
public class Receiver implements MessageListener  {

    public static void main(String[] args) {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 消费者，消息接收者
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                ActiveConstant.BROKER_URL);
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue(ActiveConstant.QUEUE);
            consumer = session.createConsumer(destination);
            //初始化MessageListener
            Receiver me = new Receiver();

            //给消费者设定监听对象
            consumer.setMessageListener(me);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }

        //初始化ConnectionFactory
        //connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

    }

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
