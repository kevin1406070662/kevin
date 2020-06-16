package com.kevin.common.utils.email;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * author:马凯文
 * date: 2020/2/29 23:28
 * description: 邮件工具类
 */
public class SendMail {
    /**
     * 邮件发送
     *
     * @param toMail  邮件接收者
     * @param subject 标题
     * @param content 内容
     */
    public static void sendMail(String toMail, String subject, String content) {
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(465);
        account.setAuth(true);
        account.setFrom("1406070662@qq.com"); //
        account.setUser("1406070662@qq.com");
        account.setPass("vkysbdidehxqjaab"); //
        account.setSslEnable(true);
        account.setSocketFactoryClass("javax.net.ssl.SSLSocketFactory");
        MailUtil.send(account,
                CollUtil.newArrayList(toMail),
                subject, content, false);

    }

}

