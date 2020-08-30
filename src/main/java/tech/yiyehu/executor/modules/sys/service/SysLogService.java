package tech.yiyehu.executor.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
