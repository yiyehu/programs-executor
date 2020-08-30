package tech.yiyehu.executor.modules.sys.controller;

import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.sys.service.SysLogService;
import tech.yiyehu.executor.common.utils.PageUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 系统日志
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("sys:log:list")
	public ResultUtil list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);

		return ResultUtil.ok().put("page", page);
	}
	
}
