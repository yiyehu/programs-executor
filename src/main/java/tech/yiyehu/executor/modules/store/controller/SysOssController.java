package tech.yiyehu.executor.modules.store.controller;

import tech.yiyehu.executor.common.utils.ResultUtil;
import tech.yiyehu.executor.modules.store.service.SysOssService;
import tech.yiyehu.executor.modules.sys.controller.AbstractController;
import tech.yiyehu.executor.modules.sys.service.SysConfigService;
import com.google.gson.Gson;
import tech.yiyehu.executor.common.utils.ResultException;
import tech.yiyehu.executor.common.utils.ConfigConstant;
import tech.yiyehu.executor.common.enums.Constant;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.modules.store.service.storage.StorageConfig;
import tech.yiyehu.executor.modules.store.service.storage.StorageFactory;
import tech.yiyehu.executor.modules.store.entity.SysOssEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 */
@RestController
@RequestMapping("sys/oss")
@Api("文件上传")
public class SysOssController extends AbstractController {
    @Autowired
    private SysOssService sysOssService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.STORAGE_CONFIG_KEY;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        Long userId = getUserId();
        if (userId != Constant.SUPER_ADMIN) {
            params.put(Constant.USER_ID, getUserId());
        }
        PageUtils page = sysOssService.queryPage(params);
        return ResultUtil.ok().put("page", page);
    }


    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public ResultUtil config() {
        StorageConfig config = sysConfigService.getConfigObject(KEY, StorageConfig.class);
        return ResultUtil.ok().put("config", config);
    }


    /**
     * 保存存储配置信息
     */
    @PostMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public ResultUtil saveConfig(@RequestBody StorageConfig config) {
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

        return ResultUtil.ok();
    }


    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @ApiOperation("上传文件")
    @RequiresPermissions("sys:oss:all")
    public ResultUtil upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new ResultException("上传文件不能为空");
        }
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = StorageFactory.build().uploadSuffix(file.getBytes(), suffix);

        //保存文件信息
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        ossEntity.setUserId(getUserId());
        ossEntity.setUserType(Constant.UserType.SYS.getValue());
        sysOssService.save(ossEntity);
        return ResultUtil.ok().put("url", url);
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:oss:delete")
    public ResultUtil delete(@RequestBody Long[] ids) {
        //TODO 用户权限校验
        sysOssService.removeByIds(Arrays.asList(ids));
        return ResultUtil.ok();
    }
}
