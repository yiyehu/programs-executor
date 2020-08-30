package tech.yiyehu.executor.modules.app.service.impl;

import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.Query;
import tech.yiyehu.executor.modules.app.dao.ExeLogDao;
import tech.yiyehu.executor.modules.app.entity.ExeLogEntity;
import tech.yiyehu.executor.modules.app.service.ExeLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("exeLogService")
public class ExeLogServiceImpl extends ServiceImpl<ExeLogDao, ExeLogEntity> implements ExeLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ExeLogEntity> page = this.page(
                new Query<ExeLogEntity>().getPage(params),
                new QueryWrapper<ExeLogEntity>()
        );

        return new PageUtils(page);
    }

}
