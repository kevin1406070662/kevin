package com.kevin.common.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 *
 * @Author:kevin
 * @Date: 2020/4/9
 * @Time: 14:39
 **/
@Data
public class Page<T> {

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private List<T> rows = new ArrayList<T>();
    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private long total;
    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer page;
    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer limit;

    public Page() {
        super();
    }

    public Page(Long total,Integer page, Integer limit,List<T> rows) {
        this.page = page;
        this.limit = limit;
        this.total=total;
        this.rows=rows;
    }

}
