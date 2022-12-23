package com.lin.system.service.impl;

import java.util.List;

import com.lin.system.dao.SysRoleMenuDao;
import com.lin.system.pojo.SysRoleMenu;
import com.lin.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<SysRoleMenu> List(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuDao.list(sysRoleMenu);
    }

    @Override
    public SysRoleMenu query(Long id) {
        return sysRoleMenuDao.query(id);
    }

    @Override
    public int add(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuDao.add(sysRoleMenu);
    }

    @Override
    public int edit(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuDao.edit(sysRoleMenu);
    }

    @Override
    public int delete(Long id) {
        return sysRoleMenuDao.delete(id);
    }

}
