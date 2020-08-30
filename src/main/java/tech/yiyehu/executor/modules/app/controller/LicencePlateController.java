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

import tech.yiyehu.executor.modules.app.entity.LicencePlateEntity;
import tech.yiyehu.executor.modules.app.service.LicencePlateService;
import tech.yiyehu.executor.common.utils.PageUtils;


/**
 * 车牌
 *
 * @author yiyehu
 * @email 
 * @date 2020-08-22 18:36:56
 */
@RestController
@RequestMapping("app/licenceplate")
public class LicencePlateController extends AbstractController {
    @Autowired
    private LicencePlateService licencePlateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("app:licenceplate:list")
    public ResultUtil list(@RequestParam Map<String, Object> params){
        Long userId = getUserId();
        if(userId != Constant.SUPER_ADMIN){
            params.put(Constant.USER_ID,getUserId());
        }
        PageUtils page = licencePlateService.queryPage(params);

        return ResultUtil.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{plateId}")
    @RequiresPermissions("app:licenceplate:info")
    public ResultUtil info(@PathVariable("plateId") String plateId){
		LicencePlateEntity licencePlate = licencePlateService.getById(plateId);

        return ResultUtil.ok().put("licencePlate", licencePlate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("app:licenceplate:save")
    public ResultUtil save(@RequestBody LicencePlateEntity licencePlate){
			licencePlateService.save(licencePlate);

        return ResultUtil.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("app:licenceplate:update")
    public ResultUtil update(@RequestBody LicencePlateEntity licencePlate){
			licencePlateService.updateById(licencePlate);

        return ResultUtil.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("app:licenceplate:delete")
    public ResultUtil delete(@RequestBody String[] plateIds){
			licencePlateService.removeByIds(Arrays.asList(plateIds));

        return ResultUtil.ok();
    }

}
