package tech.yiyehu.executor.modules.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.modules.store.entity.SysOssEntity;

import java.util.Map;

/**
 * 文件上传
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);


    boolean hasExist(Long id,Long userId);
}
