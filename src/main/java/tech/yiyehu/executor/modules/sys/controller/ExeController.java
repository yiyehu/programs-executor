package tech.yiyehu.executor.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.sys.entity.ExeEntity;
import tech.yiyehu.executor.modules.sys.service.ExeService;
import tech.yiyehu.executor.common.utils.PageUtils;


/**
 * 角色
 *
 * @author yiyehu
 * @email
 * @date 2020-08-22 18:20:43
 */
@RestController
@RequestMapping("sys/exe")
public class ExeController {
    @Autowired
    private ExeService exeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:exe:list")
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        PageUtils page = exeService.queryPage(params);
        return ResultUtil.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{exeId}")
    @RequiresPermissions("sys:exe:info")
    public ResultUtil info(@PathVariable("exeId") Long exeId) {
        ExeEntity exe = exeService.getById(exeId);
        return ResultUtil.ok().put("exe", exe);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:exe:save")
    public ResultUtil save(@RequestBody ExeEntity exe) {

        exeService.save(exe);
        return ResultUtil.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:exe:update")
    public ResultUtil update(@RequestBody ExeEntity exe) {
        exeService.updateById(exe);

        return ResultUtil.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:exe:delete")
    public ResultUtil delete(@RequestBody Long[] exeIds) {
        exeService.removeByIds(Arrays.asList(exeIds));

        return ResultUtil.ok();
    }

}
