package tech.yiyehu.executor.modules.sys.controller;


import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import tech.yiyehu.executor.common.annotation.SysLog;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.sys.entity.SysConfigEntity;
import tech.yiyehu.executor.modules.sys.service.SysConfigService;

import java.util.Map;

/**
 * 系统配置信息
 */
@RestController
@Api("系统-配置")
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {

    private final SysConfigService sysConfigService;

    public SysConfigController(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

    /**
     * 所有配置列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:config:list")
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysConfigService.queryPage(params);

        return ResultUtil.ok().put("page", page);
    }


    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public ResultUtil info(@PathVariable("id") Long id) {
        SysConfigEntity config = sysConfigService.getById(id);

        return ResultUtil.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @PostMapping("/save")
    @RequiresPermissions("sys:config:save")
    public ResultUtil save(@RequestBody SysConfigEntity config) {
        sysConfigService.saveConfig(config);

        return ResultUtil.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @PostMapping("/update")
    @RequiresPermissions("sys:config:update")
    public ResultUtil update(@RequestBody SysConfigEntity config) {
        sysConfigService.update(config);

        return ResultUtil.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @PostMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public ResultUtil delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);

        return ResultUtil.ok();
    }

}
