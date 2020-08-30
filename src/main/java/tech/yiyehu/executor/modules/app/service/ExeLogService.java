package tech.yiyehu.executor.modules.app.service;

import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.modules.app.entity.ExeLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 执行日志
 *
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:36:56
 */
public interface ExeLogService extends IService<ExeLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

