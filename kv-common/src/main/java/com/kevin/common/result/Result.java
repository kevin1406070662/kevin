package com.kevin.common.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 请求返回结果
 * @author kevin
 * @date 2020/5/28
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result implements Serializable {
    /**
     * 响应状态码
     */
    @ApiModelProperty("响应状态码")
    private int code;
    /**
     * 结果状态，true成功，false：失败
     */
    @ApiModelProperty("true成功，false：失败")
    private boolean result;
    /**
     * 消息
     */
    @ApiModelProperty("消息")
    private String msg;
    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private Object data;
    /**
     * 需要附加的额外数据
     */
    @ApiModelProperty("需要附加的额外数据")
    private Object extraData;

    public com.kevin.common.result.Result setCode(int code) {
        this.code = code;
        return this;
    }

    public com.kevin.common.result.Result setResult(boolean result) {
        this.result = result;
        return this;
    }

    public com.kevin.common.result.Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public com.kevin.common.result.Result setData(Object data) {
        this.data = data;
        return this;
    }

    /**
     * 返回消息
     *
     * @param msg  消息
     * @param code 状态码
     * @param re   成功标识
     * @param data 数据
     * @return 消息
     */
    public static com.kevin.common.result.Result msg(String msg, int code, boolean re, Object data) {
        com.kevin.common.result.Result result = new com.kevin.common.result.Result();
        result.setMsg(msg);
        result.setCode(code);
        result.setResult(re);
        result.setData(data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static com.kevin.common.result.Result success() {
        String message = "操作成功";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        switch (method.toUpperCase()) {
            case "GET":
                message = "获取数据成功";
                break;
            case "POST":
                message = "提交数据成功";
                break;
            case "PUT":
                message = "更新数据成功";
                break;
            case "DELETE":
                message = "删除数据成功";
                break;
            default:
                break;
        }
        return com.kevin.common.result.Result.success(message);
    }

    /**
     * 返回成功消息
     *
     * @param msg 消息
     * @return 成功消息
     */
    public static com.kevin.common.result.Result success(String msg) {
        return com.kevin.common.result.Result.success(msg, 200);
    }

    /**
     * 返回成功消息
     *
     * @param msg  消息
     * @param code 状态码
     * @return 成功消息
     */
    public static com.kevin.common.result.Result success(String msg, int code) {
        return com.kevin.common.result.Result.success(msg, code, true);
    }

    /**
     * 返回成功消息
     *
     * @param msg  消息
     * @param code 状态码
     * @param re   标识
     * @return 成功消息
     */
    public static com.kevin.common.result.Result success(String msg, int code, boolean re) {
        return com.kevin.common.result.Result.msg(msg, code, re, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  消息
     * @param code 状态码
     * @param re   标识
     * @param data  内容
     * @return 成功消息
     */
    public static com.kevin.common.result.Result success(String msg, int code, boolean re,Object data) {
        return com.kevin.common.result.Result.msg(msg, code, re, data);
    }
    /**
     * 返回成功消息
     *
     * @param data 数据
     * @return 成功消息
     */
    public static com.kevin.common.result.Result success(Object data) {
        return com.kevin.common.result.Result.msg("操作成功", 200, true, data);
    }

    /**
     * 返回失败消息
     *
     * @return 失败消息
     */
    public static com.kevin.common.result.Result error() {
        String message = "操作失败";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        switch (method.toUpperCase()) {
            case "GET":
                message = "获取数据失败";
                break;
            case "POST":
                message = "提交数据失败";
                break;
            case "PUT":
                message = "更新数据失败";
                break;
            case "DELETE":
                message = "删除数据失败";
                break;
            default:
                break;
        }
        return com.kevin.common.result.Result.error(message);
    }

    /**
     * 返回失败消息
     *
     * @param msg 消息
     * @return 失败消息
     */
    public static com.kevin.common.result.Result error(String msg) {
        return com.kevin.common.result.Result.success(msg, 500,false);
    }

    /**
     * 返回失败消息
     *
     * @param msg  消息
     * @param code 状态码
     * @return 失败消息
     */
    public static com.kevin.common.result.Result error(String msg, int code) {
        return com.kevin.common.result.Result.success(msg, code, false);
    }

    /**
     * 返回失败消息
     *
     * @param msg  消息
     * @param code 状态码
     * @param re   标识
     * @return 失败消息
     */
    public static com.kevin.common.result.Result error(String msg, int code, boolean re) {
        return com.kevin.common.result.Result.msg(msg, code, re, null);
    }

    /**
     * 返回失败消息
     *
     * @param data 数据
     * @return 失败消息
     */
    public static com.kevin.common.result.Result error(Object data) {
        return com.kevin.common.result.Result.msg("操作失败", 500, false, data);
    }
}
