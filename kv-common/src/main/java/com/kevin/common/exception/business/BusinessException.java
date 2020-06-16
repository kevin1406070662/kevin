package com.kevin.common.exception.business;


import com.kevin.common.exception.base.BaseException;

/**
 * 全局业务异常
 *
 * @author kevin
 * @date 2020/5/6
 */
public class BusinessException extends BaseException {
    public BusinessException(String s) {
        super(s);
    }
}
