package com.kevin.generator.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kevin.common.result.Page;
import com.kevin.common.result.Result;
import com.kevin.generator.domain.ColumnInfo;
import com.kevin.generator.domain.TableInfo;
import com.kevin.generator.service.IGenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 代码生成 操作处理
 *
 * @author novel
 * @date 2020/3/25
 */
@RestController
@RequestMapping("/tool/gen")
@Api(tags = "代码生成")
public class GenController   {
    private final IGenService genService;

    public GenController(IGenService genService) {
        this.genService = genService;
    }
    /**
     * 获取数据表信息
     *
     * @param tableName 表名
     * @return 表信息
     */

    @GetMapping("/list")
    @ApiOperation("获取数据表信息")
    public Result list(String tableName,@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer limit) {
        //List<TableInfo> list = genService.selectTableList(TableName);
        PageHelper.startPage(page, limit);
        List<TableInfo>  list= genService.selectTableList(tableName);
        PageInfo<TableInfo> pageInfo = new PageInfo<TableInfo>(list);
        return Result.success(new Page<TableInfo>(pageInfo.getTotal(), limit, page, list));
    }

    /**
     * 获取数据表信息
     *
     * @param TableName 表名
     * @return 表信息
     */

    @GetMapping("/listByTable")
    @ApiOperation("获取表下的字段信息")
    public Result   listByTable(@RequestParam String TableName) {
        List<ColumnInfo> columnInfos = genService.selectTableColumn(TableName);
        return Result.success(columnInfos);
    }

    /**
     * 生成代码
     *
     * @param response  response
     * @param tableName 表名
     * @throws IOException io异常
     */
    @GetMapping("/genCode")
    public void genCode(HttpServletResponse response, @RequestParam("tableName") String tableName) throws IOException {
        byte[] data = genService.generatorCode(tableName);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"kevin.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 批量生成代码
     *
     * @param response   response
     * @param tableNames 表名数组
     * @throws IOException io异常
     */
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String[] tableNames) throws IOException {
        byte[] data = genService.generatorCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"novel.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
