package tech.yiyehu.executor.modules.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.yiyehu.executor.common.annotation.SysLog;
import tech.yiyehu.executor.common.enums.Constant;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.sys.entity.SysRoleEntity;
import tech.yiyehu.executor.modules.sys.service.SysRoleMenuService;
import tech.yiyehu.executor.modules.sys.service.SysRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/sys/role")
@Api("角色")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	@ApiOperation("角色列表")
	public ResultUtil list(@RequestParam Map<String, Object> params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		PageUtils page = sysRoleService.queryPage(params);

		return ResultUtil.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public ResultUtil select(){
		Map<String, Object> map = new HashMap<>();
		
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			map.put("create_user_id", getUserId());
		}
		List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
		
		return ResultUtil.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	@ApiOperation("角色列表")
	public ResultUtil info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return ResultUtil.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	@ApiOperation("角色列表")
	public ResultUtil save(@RequestBody SysRoleEntity role){
		role.setCreateUserId(getUserId());
		sysRoleService.saveRole(role);
		
		return ResultUtil.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	@ApiOperation("角色列表")
	public ResultUtil update(@RequestBody SysRoleEntity role){
		role.setCreateUserId(getUserId());
		sysRoleService.update(role);
		return ResultUtil.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@ApiOperation("角色列表")
	@RequiresPermissions("sys:role:delete")
	public ResultUtil delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		return ResultUtil.ok();
	}
}
