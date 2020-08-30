package tech.yiyehu.executor.modules.app.controller;

import tech.yiyehu.executor.common.enums.Constant;
import tech.yiyehu.executor.common.utils.ResultException;
import tech.yiyehu.executor.modules.app.entity.ExeLogEntity;
import tech.yiyehu.executor.modules.app.executor.PythonProgramExecutor;
import tech.yiyehu.executor.modules.app.service.ExeLogService;
import tech.yiyehu.executor.modules.store.entity.SysOssEntity;
import tech.yiyehu.executor.modules.store.service.SysOssService;
import tech.yiyehu.executor.modules.sys.controller.AbstractController;
import tech.yiyehu.executor.modules.sys.entity.ExeEntity;
import tech.yiyehu.executor.modules.sys.service.ExeService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.python.core.PyString;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@Api
@RequestMapping("exe")
public class AlgorithmExeController extends AbstractController {

    private final SysOssService sysOssService;

    private final ExeService exeService;

    private final ExeLogService exeLogService;

    private final PythonProgramExecutor pythonProgramExecutor;

    public AlgorithmExeController(SysOssService sysOssService, ExeService exeService, ExeLogService exeLogService, PythonProgramExecutor pythonProgramExecutor) {
        this.sysOssService = sysOssService;
        this.exeService = exeService;
        this.exeLogService = exeLogService;
        this.pythonProgramExecutor = pythonProgramExecutor;
    }

    /**
     * @param id     算法ID，因为是Python实现，所以决定用数据库存储算法的输入、算法位置、输出。
     * @param fileId 上传的文件ID
     * @return
     */
    @ApiOperation("exeAlgorithm")
    @PostMapping("exeAlgorithm")
    public Object exeAlgorithm(
            @RequestParam Long id,
            @RequestParam(required = false) Long fileId,
            @RequestParam(required = false) Map<String, String> params) throws IOException, InterruptedException {
        ExeEntity entity = exeService.getById(id);
        if (entity == null) {
            throw new ResultException("没有可以执行的程序");
        }
        SysOssEntity ossEntity = sysOssService.getById(fileId);
        String filePath = "";
        if (ossEntity.getUrl().startsWith("https")) {
            filePath = "";
        } else {
            filePath = ossEntity.getUrl();
            File file = new File(filePath);
            if(!file.exists()){
                throw new ResultException("文件不存在");
            }
            filePath = file.getAbsolutePath();
        }
        ArrayList<String> commands = new ArrayList<>();
        commands.add(Constant.ExeType.getWithValue(entity.getType()).getExeCmd());
        commands.add(entity.getPath());
        String prefix = Constant.ExeType.getWithValue(entity.getType()).getPrefix();
        //处理文件参数
        if (entity.getParamType().equals(Constant.ExeParamType.FILE.getValue())
                || entity.getParamType().equals(Constant.ExeParamType.MIX.getValue())) {
            if (fileId == null || fileId == 0 || !sysOssService.hasExist(fileId, getUserId())) {
                throw new ResultException("未找到需要处理的文件");
            }
            commands.add(prefix + Constant.ExeCommonParam.FILE_PATH.getExeParam() + "=" + filePath);
        }
        //处理其他参数
        if (entity.getParamType().equals(Constant.ExeParamType.PARAMS.getValue())
                || entity.getParamType().equals(Constant.ExeParamType.MIX.getValue())) {

            boolean hasParams = (params != null) && params.size() > 0;
            if (!hasParams) {
                throw new ResultException("未找到需要处理的参数");
            }
            HashSet<String> set = new HashSet(Arrays.asList(entity.getParams().split(Constant.SymbolEnum.SPLIT_STRING.getSymbol())));
            set.retainAll(params.keySet());
            if (set.size() < 1) {
                throw new ResultException("参数无效，请与执行文件的参数对应");
            }
            set.stream().forEach(key -> {
                commands.add(prefix + key + "=" + params.get(key));
            });
        }

        Constant.ExeType exeType = Constant.ExeType.getWithValue(entity.getType());
        PyString[] pyStrings = new PyString[commands.size()-2];
        Map result = null;
        if(Constant.ExeType.PYTHON.equals(exeType)) {
            List list = pythonProgramExecutor.exe(commands.toArray(new String[commands.size()]));
            try {
                result = pythonProgramExecutor.dealResult(entity.getResultParams(),list);
            } catch (Exception e) {
                throw new ResultException("处理输出数据时发生错误\n"+e.toString());
            }
        }
        ExeLogEntity exeLogEntity = new ExeLogEntity();
        exeLogEntity.setCreateTime(new Date());
        exeLogEntity.setExeId(entity.getExeId());
        exeLogEntity.setFileId(fileId);
        exeLogEntity.setParams(commands.toString());
        exeLogEntity.setParamType(entity.getParamType());
        exeLogEntity.setResult(JSON.toJSONString(result));
        exeLogService.save(exeLogEntity);
        System.out.println("value is : " + result);
        return result;
    }
}
