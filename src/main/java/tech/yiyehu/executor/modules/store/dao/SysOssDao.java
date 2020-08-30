package tech.yiyehu.executor.modules.store.dao;

import tech.yiyehu.executor.modules.store.entity.SysOssEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
