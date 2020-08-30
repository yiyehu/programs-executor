package tech.yiyehu.executor.modules.app.service.impl;

import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.Query;
import tech.yiyehu.executor.modules.app.dao.LicencePlateDao;
import tech.yiyehu.executor.modules.app.entity.LicencePlateEntity;
import tech.yiyehu.executor.modules.app.service.LicencePlateService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("licencePlateService")
public class LicencePlateServiceImpl extends ServiceImpl<LicencePlateDao, LicencePlateEntity> implements LicencePlateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LicencePlateEntity> page = this.page(
                new Query<LicencePlateEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}
