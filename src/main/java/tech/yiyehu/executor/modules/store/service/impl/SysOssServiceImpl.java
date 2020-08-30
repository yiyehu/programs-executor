package tech.yiyehu.executor.modules.store.service.impl;

import tech.yiyehu.executor.common.enums.Constant;
import tech.yiyehu.executor.modules.store.service.SysOssService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import tech.yiyehu.executor.common.utils.PageUtils;
import tech.yiyehu.executor.common.utils.Query;
import tech.yiyehu.executor.modules.store.dao.SysOssDao;
import tech.yiyehu.executor.modules.store.entity.SysOssEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOssEntity> page;
        if (params.get(Constant.USER_ID) != null) {
            QueryWrapper queryWrapper = new QueryWrapper<SysOssEntity>()
                    .eq(Constant.USER_ID, params.get(Constant.USER_ID));
            page = this.page(
                    new Query<SysOssEntity>().getPage(params), queryWrapper);
        } else {
            page = this.page(
                    new Query<SysOssEntity>().getPage(params));
        }
        return new PageUtils(page);
    }

    @Override
    public boolean hasExist(Long id,Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper<SysOssEntity>()
                .eq(Constant.ID, id)
                .eq(Constant.USER_ID, userId);
        int has = getBaseMapper().selectCount(queryWrapper);
        return has > 0;
    }
}
