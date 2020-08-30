package tech.yiyehu.executor.modules.sys.service;

import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.modules.sys.entity.ExeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 角色
 *
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:20:43
 */
public interface ExeService extends IService<ExeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

