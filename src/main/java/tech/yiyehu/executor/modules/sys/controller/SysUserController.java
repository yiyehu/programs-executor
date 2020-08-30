package tech.yiyehu.executor.modules.sys.controller;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.yiyehu.executor.common.annotation.SysLog;
import tech.yiyehu.executor.common.enums.Constant;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.sys.entity.SysUserEntity;
import tech.yiyehu.executor.modules.sys.form.PasswordDto;
import tech.yiyehu.executor.modules.sys.service.SysUserRoleService;
import tech.yiyehu.executor.modules.sys.service.SysUserService;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public ResultUtil list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}
		PageUtils page = sysUserService.queryPage(params);

		return ResultUtil.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public ResultUtil info(){
		return ResultUtil.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public ResultUtil password(@RequestBody PasswordDto dto){
		//sha256加密
		String password = new Sha256Hash(dto.getPassword(), getUser().getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(dto.getNewPassword(), getUser().getSalt()).toHex();
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return ResultUtil.error("原密码不正确");
		}
		
		return ResultUtil.ok();
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public ResultUtil info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return ResultUtil.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public ResultUtil save(@RequestBody SysUserEntity user){
		user.setCreateUserId(getUserId());
		sysUserService.saveUser(user);
		
		return ResultUtil.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public ResultUtil update(@RequestBody SysUserEntity user){
		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return ResultUtil.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public ResultUtil delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return ResultUtil.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return ResultUtil.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return ResultUtil.ok();
	}
}
