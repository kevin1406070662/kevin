package com.kevin.common.utils.model;

import lombok.Data;

/**
 * ip 信息
 *
 * @author novel
 * @date 2020/3/24
 */
@Data
public class IpInfo {
    /**
     * 国家
     */
    private String country;
    /**
     * 区域
     */
    private String area;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 运营商
     */
    private String isp;

}
