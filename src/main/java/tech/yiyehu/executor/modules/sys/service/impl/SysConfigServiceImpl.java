package tech.yiyehu.executor.modules.sys.service.impl;

import tech.yiyehu.executor.common.utils.ResultException;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.Query;
import tech.yiyehu.executor.modules.sys.dao.SysConfigDao;
import tech.yiyehu.executor.modules.sys.entity.SysConfigEntity;
import tech.yiyehu.executor.modules.sys.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");

		IPage<SysConfigEntity> page = this.page(
			new Query<SysConfigEntity>().getPage(params),
			new QueryWrapper<SysConfigEntity>()
				.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
				.eq("status", 1)
		);

		return new PageUtils(page);
	}

	@Override
	public void saveConfig(SysConfigEntity config) {
		this.save(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		this.updateById(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.getById(id);
		}

		this.removeByIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = baseMapper.queryByKey(key);
		return config == null ? null : config.getParamValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new ResultException("获取参数失败");
		}
	}
}
