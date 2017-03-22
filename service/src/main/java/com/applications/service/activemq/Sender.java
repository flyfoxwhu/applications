package com.applications.service.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by hukaisheng on 2017/3/22.
 */
public class Sender {

    // ConnectionFactory ：连接工厂，JMS 用它创建连接
    private static ConnectionFactory connectionFactory;

    // Connection ：JMS 客户端到JMS Provider 的连接
    private static Connection connection;
    // Session： 一个发送或接收消息的线程
    private static Session session;

    // Destination ：消息的目的地;消息发送给谁.
    private static Destination destination;
    // MessageProducer：消息发送者
    private static MessageProducer producer;

    public Sender(String queue) throws JMSException {
        // 构造ConnectionFactory实例对象
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                ActiveConstant.BROKER_URL);
        // 构造从工厂得到连接对象
        connection = connectionFactory.createConnection();
        // 启动
        connection.start();
        // 获取操作连接
        session = connection.createSession(Boolean.TRUE,
                Session.AUTO_ACKNOWLEDGE);

        //消息的目的地即消息的队列名称
        destination = session.createQueue(queue);
        // 得到消息生成者【发送者】
        producer = session.createProducer(destination);
        // 设置不持久化，此处学习，实际根据项目决定
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    public static void sendMessage(String msg)
            throws Exception {
        TextMessage message = session
                .createTextMessage(msg);
        // 发送消息到目的地方
        System.out.println("发送消息：" + msg);
        producer.send(message);
        session.commit();
    }

    public static void main(String[] args) {

        try {
            if (producer==null){
                new  Sender(ActiveConstant.QUEUE);
            }
            // 构造消息，此处写死，项目就是参数，或者方法获取
            Sender.sendMessage("test activemq");

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
