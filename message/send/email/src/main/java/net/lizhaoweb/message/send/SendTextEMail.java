/**
 * Copyright (c) 2020, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.message.send
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:08
 */
package net.lizhaoweb.message.send;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lizhaoweb.message.ISendMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * <H1>接口 -  发送纯文本邮件</H1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2020年01月20日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@NoArgsConstructor
public class SendTextEMail implements ISendMessage {

    private static String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";
    private static String DEFAULT_TRANSPORT_PROTOCOL = "smtp";

    @Setter
    private boolean isDebug;

    @Setter
    private String transportHost;// 邮件服务器。比如:smtp.qq.com

    @Setter
    private String transportProtocol;// 邮件传输协议。比如:smtp

    @Setter
    private String loadUser;// 邮件服务器登录账号。

    @Setter
    private String loadPass;// 邮件服务器登录密码。

    private String contentType = DEFAULT_CONTENT_TYPE;

    public SendTextEMail(String transportHost, String transportProtocol, String contentType, String loadUser, String loadPass) {
        this.transportHost = transportHost;
        this.transportProtocol = transportProtocol;
        this.contentType = contentType;
        this.loadUser = loadUser;
        this.loadPass = loadPass;
    }

    public SendTextEMail(String transportHost, String loadUser, String loadPass) {
        this(transportHost, DEFAULT_TRANSPORT_PROTOCOL, DEFAULT_CONTENT_TYPE, loadUser, loadPass);
    }

    @Override
    public boolean send(net.lizhaoweb.message.Message message) {
        boolean result = false;
        Transport transport = null;
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.host", transportHost);
            properties.setProperty("mail.transport.protocol", transportProtocol);
            properties.setProperty("mail.smtp.auth", "true");

            //QQ存在一个特性设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            //创建一个session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(loadUser, loadPass);
                }
            });
            session.setDebug(isDebug);//开启debug模式

            transport = session.getTransport();//获取连接对象
            transport.connect(transportHost, loadUser, loadPass);//连接服务器
            message.setFrom(loadUser);

            MimeMessage mimeMessage = new MimeMessage(session);//创建邮件对象
            mimeMessage.setFrom(new InternetAddress(message.getFrom()));//邮件发送人。new InternetAddress("619046217@qq.com")
            mimeMessage.setRecipients(Message.RecipientType.TO, message.getTo());//邮件接收人。new InternetAddress("875203654@qq.com")
            mimeMessage.setRecipients(Message.RecipientType.CC, message.getCc());//邮件抄送人。new InternetAddress("875203654@qq.com")
            mimeMessage.setRecipients(Message.RecipientType.BCC, message.getBcc());//邮件密送人。new InternetAddress("875203654@qq.com")
            mimeMessage.setSubject(message.getTitle());//邮件标题
            mimeMessage.setContent(message.getContent(), contentType);//邮件内容

            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            result = true;
        } catch (Exception e) {
            throw new SendEMailException(e);
        } finally {
            try {
                if (transport != null && transport.isConnected()) {
                    transport.close();//关闭连接
                }
            } catch (MessagingException e) {
//
            } finally {
                transport = null;
            }
        }
        return result;
    }
}
