package tech.yiyehu.executor.modules.app.service;

import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.modules.app.entity.LicencePlateEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 车牌
 *
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:36:56
 */
public interface LicencePlateService extends IService<LicencePlateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

