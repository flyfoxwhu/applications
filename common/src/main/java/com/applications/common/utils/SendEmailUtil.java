package com.applications.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SendEmailUtil {
    //邮箱地址
    private static String senderName = (String)SpringContextUtil.getBean("senderName");

    //邮箱密码
    private static String senderPassword = (String)SpringContextUtil.getBean("senderPassword");

    //邮箱服务器
    private static String host = (String)SpringContextUtil.getBean("host");

    private static String sendChineseName = "XXX";
    //发送邮件方法
    public static Boolean send(String receiverNames, String emailTitle, String emailContents) {
        try{
            if(StringUtils.isBlank(receiverNames)|| StringUtils.isBlank(emailTitle)|| StringUtils.isBlank(emailContents)){
                return false;
            }
            String[] receivers =StringUtils.split(receiverNames,";");
            if(receivers.length==0){
                return true;
            }
            Properties props = new Properties();//获取系统环境
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Authenticator auth = new MyAuthenticator(senderName,senderPassword);
            Session session = Session.getDefaultInstance(props, auth);
            // 设置session,和邮件服务器进行通讯。
            MimeMessage message = new MimeMessage(session);
            message.setSubject(emailTitle); // 设置邮件主题
            message.setText(emailContents); // 设置邮件正文
            message.setHeader(emailTitle,emailTitle); // 设置邮件标题
            message.setSentDate(new Date()); // 设置邮件发送日期
            Address address = new InternetAddress(senderName, sendChineseName);
            message.setFrom(address); // 设置邮件发送者的地址

            // 为每个邮件接收者创建一个地址
            Address[]  tos = new InternetAddress[receivers.length];
            for (int i = 0; i < receivers.length; i++) {
                String s = receivers[i];
                tos[i] = new InternetAddress(s);
            }
            message.setRecipients(Message.RecipientType.TO, tos);

            Transport.send(message); // 发送邮件
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean sendWithHtml(String receiverNames, String emailTitle, String emailContents) {
        try{
            if(StringUtils.isBlank(receiverNames)|| StringUtils.isBlank(emailTitle)|| StringUtils.isBlank(emailContents)){
                return false;
            }
            String[] receivers =StringUtils.split(receiverNames,";");
            if(receivers.length==0){
                return true;
            }
            Properties props = new Properties();//获取系统环境
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Authenticator auth = new MyAuthenticator(senderName,senderPassword);
            Session session = Session.getDefaultInstance(props, auth);
            // 设置session,和邮件服务器进行通讯。
            MimeMessage message = new MimeMessage(session);
            Address address = new InternetAddress(senderName, sendChineseName);
            message.setFrom(address); // 设置邮件发送者的地址

            // 为每个邮件接收者创建一个地址
            Address[]  tos = new InternetAddress[receivers.length];
            for (int i=0; i<receivers.length; i++){
                String s=receivers[i];
                tos[i] = new InternetAddress(s);
                message.addRecipient(Message.RecipientType.TO, tos[i]);

            }

            message.setSubject(emailTitle); // 设置邮件主题
            message.setHeader(emailTitle,emailTitle); // 设置邮件标题
            message.setSentDate(new Date()); // 设置邮件发送日期
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            // 设置HTML内容
            BodyPart html = new MimeBodyPart();
            html.setContent(emailContents, "text/html;charset=utf-8");
            mainPart.addBodyPart(html);
            message.setContent(mainPart);
            Transport.send(message); // 发送邮件
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  Description:  邮件授权类
     */
    public static class MyAuthenticator extends javax.mail.Authenticator{
        private String senderName;
        private String senderPassword;
        public MyAuthenticator(String senderName,String senderPassword){
            this.senderName = senderName;
            this.senderPassword = senderPassword;
        }
        protected PasswordAuthentication getPasswordAuthentication(){
            return  new PasswordAuthentication(senderName,senderPassword);

        }
    }

    public static Boolean sendWithHtml(String receiverNames, String emailTitle, String emailContents,
                                       List<File> files) {
        try{
            if(StringUtil.isBlank(receiverNames)|| org.apache.commons.lang.StringUtils.isBlank(emailTitle)|| org.apache.commons.lang.StringUtils.isBlank(emailContents)){
                return false;
            }
            String[] receivers =org.apache.commons.lang.StringUtils.split(receiverNames,";");
            if(receivers.length==0){
                return true;
            }
            Properties props = new Properties();//获取系统环境
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            javax.mail.Authenticator auth = new MyAuthenticator(senderName,senderPassword);
            Session session = Session.getDefaultInstance(props, auth);
            // 设置session,和邮件服务器进行通讯。
            MimeMessage message = new MimeMessage(session);

            Address address = new InternetAddress(senderName, "系统邮件");
            message.setFrom(address); // 设置邮件发送者的地址

            // 为每个邮件接收者创建一个地址
            Address[]  tos = new InternetAddress[receivers.length];
            for (int i=0; i<receivers.length; i++){
                String s=receivers[i];
                tos[i] = new InternetAddress(s);
                message.addRecipient(Message.RecipientType.TO, tos[i]);

            }

            message.setSubject(emailTitle); // 设置邮件主题
            message.setHeader(emailTitle,emailTitle); // 设置邮件标题
            message.setSentDate(new Date()); // 设置邮件发送日期
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            // 设置HTML内容
            BodyPart html = new MimeBodyPart();
            html.setContent(emailContents, "text/html;charset=utf-8");
            mainPart.addBodyPart(html);

            if(!CollectionUtils.isEmpty(files)){
                for( File file:files ){
                    MimeBodyPart mbp=new MimeBodyPart();
                    String filename=file.toString(); //选择出每一个附件名
                    FileDataSource fds=new FileDataSource(filename); //得到数据源
                    mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
                    mbp.setFileName(MimeUtility.encodeText(fds.getName()));  //得到文件名同样至入BodyPart
                    mainPart.addBodyPart(mbp);
                }
            }
            message.setContent(mainPart);
            Transport.send(message); // 发送邮件
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
