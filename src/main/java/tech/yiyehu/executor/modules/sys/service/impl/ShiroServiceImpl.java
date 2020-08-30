package tech.yiyehu.executor.modules.sys.service.impl;

import tech.yiyehu.executor.common.enums.Constant;
import tech.yiyehu.executor.modules.sys.dao.SysMenuDao;
import tech.yiyehu.executor.modules.sys.dao.SysUserDao;
import tech.yiyehu.executor.modules.sys.dao.SysUserTokenDao;
import tech.yiyehu.executor.modules.sys.entity.SysMenuEntity;
import tech.yiyehu.executor.modules.sys.entity.SysUserEntity;
import tech.yiyehu.executor.modules.sys.entity.SysUserTokenEntity;
import tech.yiyehu.executor.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {

    private final SysMenuDao sysMenuDao;
    private final SysUserDao sysUserDao;
    private final SysUserTokenDao sysUserTokenDao;

    public ShiroServiceImpl(SysMenuDao sysMenuDao, SysUserDao sysUserDao, SysUserTokenDao sysUserTokenDao) {
        this.sysMenuDao = sysMenuDao;
        this.sysUserDao = sysUserDao;
        this.sysUserTokenDao = sysUserTokenDao;
    }

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
