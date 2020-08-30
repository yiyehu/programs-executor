package tech.yiyehu.executor.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import tech.yiyehu.executor.common.enums.Constant;
import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.yiyehu.executor.modules.app.entity.ExeLogEntity;
import tech.yiyehu.executor.modules.app.service.ExeLogService;
import tech.yiyehu.executor.common.utils.PageUtils;


/**
 * 执行日志
 *
 * @author yiyehu
 * @email
 * @date 2020-08-22 18:36:56
 */
@RestController
@RequestMapping("app/exelog")
public class ExeLogController extends AbstractController {
    @Autowired
    private ExeLogService exeLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:exelog:list")
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        Long userId = getUserId();
        if (userId != Constant.SUPER_ADMIN) {
            params.put(Constant.USER_ID, getUserId());
        }
        PageUtils page = exeLogService.queryPage(params);

        return ResultUtil.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{logId}")
    @RequiresPermissions("app:exelog:info")
    public ResultUtil info(@PathVariable("logId") Long logId) {
        ExeLogEntity exeLog = exeLogService.getById(logId);
        Long userId = getUserId();
        if (userId != Constant.SUPER_ADMIN) {
            if(exeLog.getUserId() != userId){
                return ResultUtil.error("无权限");
            }
        }
        return ResultUtil.ok().put("exeLog", exeLog);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:exelog:delete")
    public ResultUtil delete(@RequestBody Long[] logIds) {
        exeLogService.removeByIds(Arrays.asList(logIds));

        return ResultUtil.ok();
    }

}
