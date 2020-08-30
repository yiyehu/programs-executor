package tech.yiyehu.executor.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	ResultUtil createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
