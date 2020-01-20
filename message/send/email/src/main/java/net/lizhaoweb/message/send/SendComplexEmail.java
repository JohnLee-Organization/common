/**
 * Copyright (c) 2020, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.message.send
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:39
 */
package net.lizhaoweb.message.send;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.Setter;
import net.lizhaoweb.message.ISendMessage;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2020年01月20日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class SendComplexEmail implements ISendMessage {

    private static String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";
    private static String DEFAULT_TRANSPORT_PROTOCOL = "smtp";
    private static Pattern PATTERN_IMAGE = Pattern.compile("(<img\\s+src=['\"])([^'\"]+)(['\"])");

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

    public SendComplexEmail(String transportHost, String transportProtocol, String contentType, String loadUser, String loadPass) {
        this.transportHost = transportHost;
        this.transportProtocol = transportProtocol;
        this.contentType = contentType;
        this.loadUser = loadUser;
        this.loadPass = loadPass;
    }

    public SendComplexEmail(String transportHost, String loadUser, String loadPass) {
        this(transportHost, DEFAULT_TRANSPORT_PROTOCOL, DEFAULT_CONTENT_TYPE, loadUser, loadPass);
    }

    @Override
    public boolean send(net.lizhaoweb.message.Message message) {
        boolean result = false;
        Transport transport = null;
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", transportHost); //// 设置QQ邮件服务器
            prop.setProperty("mail.transport.protocol", transportProtocol); // 邮件发送协议
            prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

            // QQ邮箱设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            //1、创建定义整个应用程序所需的环境信息的 Session 对象
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    //传入发件人的姓名和授权码
                    return new PasswordAuthentication(loadUser, loadPass);
                }
            });
            session.setDebug(isDebug);//开启debug模式

            //2、通过session获取transport对象
            transport = session.getTransport();

            //3、通过transport对象邮箱用户名和授权码连接邮箱服务器
            transport.connect(transportHost, loadUser, loadPass);

            //4、创建邮件,传入session对象
            message.setFrom(loadUser);
            MimeMessage mimeMessage = this.complexEmail(session, message);

            //5、发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            result = true;
        } catch (Exception e) {
            throw new SendEMailException(e);
        } finally {
            //6、关闭连接
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

    private MimeMessage complexEmail(Session session, net.lizhaoweb.message.Message message) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);//消息的固定信息
        mimeMessage.setFrom(new InternetAddress(message.getFrom()));//发件人
        mimeMessage.setRecipients(Message.RecipientType.TO, message.getTo());//邮件接收人。new InternetAddress("875203654@qq.com")
        mimeMessage.setRecipients(Message.RecipientType.CC, message.getCc());//邮件抄送人。new InternetAddress("875203654@qq.com")
        mimeMessage.setRecipients(Message.RecipientType.BCC, message.getBcc());//邮件密送人。new InternetAddress("875203654@qq.com")
        mimeMessage.setSubject(message.getTitle());//邮件标题

        MimeMultipart allFile = new MimeMultipart();

        //拼装邮件正文
        MimeMultipart mimeMultipart = new MimeMultipart();
        String eMailContent = message.getContent();
        if (eMailContent == null) {
            eMailContent = "";
        }
        String _eMailContent = eMailContent;
        Matcher imageMatcher = PATTERN_IMAGE.matcher(eMailContent);
        Map<String, String> imageMap = new HashMap<>();
        int imageIndex = 0;
        while (imageMatcher.find()) {
            String imageUrl = imageMatcher.group(2);
            String imgElement = imageMatcher.group(1) + imageUrl + imageMatcher.group(3);
            String _imgElement = imgElement;
            while (imgElement.contains("'")) {
                imgElement = imgElement.replaceAll("'", "\"");
            }
            while (imgElement.contains("\t")) {
                imgElement = imgElement.replaceAll("\\s+", " ");
            }
            while (imgElement.contains("  ")) {
                imgElement = imgElement.replaceAll(" +", " ");
            }
            if (imageMap.containsKey(imgElement)) {
                _eMailContent = _eMailContent.replace(_imgElement, imageMap.get(imgElement));
                continue;
            }
            String suffix = imageUrl.substring(imageUrl.lastIndexOf('.'));
            imageIndex++;
            String imageID = imageIndex + suffix;
            String imageIDElement = "<img src=\"cid:" + imageID + "\"";
            //准备图片数据
            MimeBodyPart image = new MimeBodyPart();
            DataHandler handler = new DataHandler(new FileDataSource(imageUrl));
            image.setDataHandler(handler);
            image.setContentID(imageID); //设置图片id
            imageMap.put(imgElement, imageIDElement);
            mimeMultipart.addBodyPart(image);
            while (_eMailContent.contains(_imgElement)) {
                _eMailContent = _eMailContent.replace(_imgElement, imageIDElement);
            }
        }

        //准备文本
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(_eMailContent, contentType);
        mimeMultipart.addBodyPart(text);
        mimeMultipart.setSubType("related");//文本和图片内嵌成功

        //将拼装好的正文内容设置为主体
        MimeBodyPart contentText = new MimeBodyPart();
        contentText.setContent(mimeMultipart);
        allFile.addBodyPart(contentText);//正文

        //附件
        String enclosure = message.getEnclosure();
        if (enclosure != null && enclosure.trim().length() > 0) {
            String[] fileArray = enclosure.split(",");
            for (String file : fileArray) {
                if (file == null || file.trim().length() < 1) {
                    continue;
                }
                String fileName = file.substring(file.lastIndexOf(File.separator) + 1);
                MimeBodyPart appendix = new MimeBodyPart();
                appendix.setDataHandler(new DataHandler(new FileDataSource(file.trim())));
                appendix.setFileName(fileName);
                allFile.addBodyPart(appendix);//附件
            }
        }

        //拼接附件
        allFile.setSubType("mixed"); //正文和附件都存在邮件中，所有类型设置为mixed


        //放到Message消息中
        mimeMessage.setContent(allFile);
        mimeMessage.saveChanges();//保存修改

        return mimeMessage;
    }
}
