package com.kevin.framework.xss;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * ClassName kevin-boot - MyRequestWrapper
 * Description   HttpServletRequest 的 getInputStream() 和 getReader() 都只能读取一次，由于 Request Body 是流的形式读取，那么
 * 流读了一次就没有了，所以只能被调用一次。
 * <p>
 * 先将 Request Body 保存，然后通过 Servlet 自带的 HttpServletRequestWrapper 类覆盖 getReader() 和
 * getInputStream() 方法，使流从保存的body读取。然后再Filter中将ServletRequest替换为AuthenticationRequestWrapper。
 * Author kevin
 * Date 2020/5/30 16:18
 * Version 1.0
 **/
public class MyRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;

    public MyRequestWrapper( HttpServletRequest request) throws IOException {
        super(request);

        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String body = sb.toString();
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }


    public String getBody() {
        return new String(body, StandardCharsets.UTF_8);
    }
}