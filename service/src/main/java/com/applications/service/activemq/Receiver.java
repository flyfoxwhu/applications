package com.applications.service.activemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by hukaisheng on 2017/3/22.
 */
@Slf4j
public class Receiver {

    // ConnectionFactory ：连接工厂，JMS 用它创建连接
    private static ConnectionFactory connectionFactory;
    // Connection ：JMS 客户端到JMS Provider 的连接
    private static Connection connection = null;
    // Session： 一个发送或接收消息的线程
    private static Session session;
    // Destination ：消息的目的地;消息发送给谁.
    private static Destination destination;
    // 消费者，消息接收者
    private static MessageConsumer consumer;

    Receiver(String queue) throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                ActiveConstant.BROKER_URL);
        // 构造从工厂得到连接对象
        connection = connectionFactory.createConnection();
        // 启动
        connection.start();
        // 获取操作连接
        session = connection.createSession(Boolean.FALSE,
                Session.AUTO_ACKNOWLEDGE);
        // 获取session注意参数值队列
        destination = session.createQueue(queue);
        consumer = session.createConsumer(destination);

    }

    public static void main(String[] args) {


        try {
            //初始化MessageListener
            if (consumer == null) {
                new Receiver(ActiveConstant.QUEUE);
            }
            ActiveListener me = new ActiveListener();
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

    }


}
