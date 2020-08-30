package tech.yiyehu.executor.modules.app.dao;

import tech.yiyehu.executor.modules.app.entity.ExeLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 执行日志
 * 
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:36:56
 */
@Mapper
public interface ExeLogDao extends BaseMapper<ExeLogEntity> {
	
}
