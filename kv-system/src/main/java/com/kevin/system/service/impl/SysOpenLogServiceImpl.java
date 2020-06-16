package com.kevin.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kevin.system.dao.SysOpenLogMapper;
import com.kevin.system.domain.SysOpenLog;
import com.kevin.system.service.SysOpenLogService;

/**
 * 操作日志 服务层实现
 * 
 * @author kevin
 * @date 2020/05/30
 */

@Service
public class SysOpenLogServiceImpl extends ServiceImpl<SysOpenLogMapper, SysOpenLog> implements SysOpenLogService{

	@Autowired
	private SysOpenLogMapper sysOpenLogMapper;

	
}
