//package com.mqc.mail.test;
//
//import io.github.biezhi.ome.OhMyEmail;
//import io.github.biezhi.ome.SendMailException;
//
//import javax.mail.MessagingException;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.security.GeneralSecurityException;
//
//public class MailTest {
//    public void before() throws GeneralSecurityException {
//        // 配置，一次即可
//        OhMyEmail.config(OhMyEmail.SMTP_QQ(false), "xiaojiejie@qq.com", "your@password");
//    }
//
//    public void testSendText() throws  SendMailException {
//        OhMyEmail.subject("这是一封测试TEXT邮件")
//                .from("小姐姐的邮箱")
//                .to("xiaojiejie@gmail.com")
//                .text("信件内容")
//                .send();
//    }
//
//}
