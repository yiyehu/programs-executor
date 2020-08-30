package tech.yiyehu.executor.modules.sys.service.impl;


import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.Query;
import tech.yiyehu.executor.modules.sys.dao.ExeDao;
import tech.yiyehu.executor.modules.sys.entity.ExeEntity;
import tech.yiyehu.executor.modules.sys.service.ExeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("exeService")
public class ExeServiceImpl extends ServiceImpl<ExeDao, ExeEntity> implements ExeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<ExeEntity> page = this.page(
                new Query<ExeEntity>().getPage(params),
                new QueryWrapper<ExeEntity>()
        );

        return new PageUtils(page);
    }

}
