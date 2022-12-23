package com.lin.system.service.impl;

import java.util.List;

import com.lin.system.dao.SysUserRoleDao;
import com.lin.system.pojo.SysUserRole;
import com.lin.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
;


@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public List<SysUserRole> List(SysUserRole sysUserRole) {
        return sysUserRoleDao.list(sysUserRole);
    }

    @Override
    public SysUserRole query(Long id) {
        return sysUserRoleDao.query(id);
    }

    @Override
    public int add(SysUserRole sysUserRole) {
        return sysUserRoleDao.add(sysUserRole);
    }

    @Override
    public int edit(SysUserRole sysUserRole) {
        return sysUserRoleDao.edit(sysUserRole);
    }

    @Override
    public int delete(Long id) {
        return sysUserRoleDao.delete(id);
    }

}
