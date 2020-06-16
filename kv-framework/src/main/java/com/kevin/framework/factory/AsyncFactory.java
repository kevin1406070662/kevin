package com.kevin.framework.factory;

import com.kevin.common.utils.email.SendMail;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 异步任务工厂
 *
 * @author kevin
 * @date 2020/5/28
 */
@Component
@EnableAsync
public class AsyncFactory {


    /**
     * 异步邮件发送
     * @param toMail  邮件接收者
     * @param subject 标题
     * @param content 内容
     */
    @Async
    public void asyncSendMail(String toMail, String subject, String content) {
        SendMail.sendMail(toMail, subject, content);
    }

}
