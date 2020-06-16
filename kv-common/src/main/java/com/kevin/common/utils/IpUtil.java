package com.kevin.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;


/**
 * author:马凯文
 * date: 2020/2/27 14:26
 * description: ip 地址工具
 */
public class IpUtil {

    /**
     * 获取登录用户的IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 通过IP获取地址(需要联网，调用百度的IP库)
     *
     * @param ip
     * @return
     */
    public static String getIpInfo(String ip) {
        if ("127.0.0.1".equals(ip)) {
            ip = "127.0.0.1";
        }
        //    http://api.map.baidu.com/location/ip?ak=您的AK&ip=您的IP&coor=bd09ll
        // HTTP协议 Gpfs5ruxuxgxcmTL74W1u590klL58bFn
        String result1 = HttpUtil.post("https://api.map.baidu.com/location/" +
                "ip?ak=Gpfs5ruxuxgxcmTL74W1u590klL58bFn&ip=" + ip, "");
        JSONObject ipInfo = (JSONObject) JSONObject.parse(result1);
        JSONObject context = (JSONObject) ipInfo.get("content");
        return (String) context.get("address");
    }

    public static void main(String[] args) {
        String ipInfo = getIpInfo("120.243.132.178");

        System.out.println(getIpInfo("120.243.132.178"));
    }
}
