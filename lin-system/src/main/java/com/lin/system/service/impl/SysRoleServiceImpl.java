package com.lin.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lin.system.dao.SysRoleDao;
import com.lin.system.dao.SysRoleMenuDao;
import com.lin.system.pojo.SysRole;
import com.lin.system.pojo.SysRoleMenu;
import com.lin.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;


@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<SysRole> List(SysRole sysRole) {
        return sysRoleDao.list(sysRole);
    }

    @Override
    public Map<String, Object> query(Long id) {
        Map<String, Object> map = new HashMap<>();
        SysRole query = sysRoleDao.query(id);
        List<Long> list = sysRoleMenuDao.gettree(id);
        map.put("itme", query);
        map.put("list", list);
        return map;
    }

    @Override
    public int add(SysRole sysRole) {
        sysRoleDao.add(sysRole);
        List<Long> treelist = sysRole.getTreelist();
        if (!CollectionUtils.isEmpty(treelist)) {
            treelist.forEach(res -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getId());
                sysRoleMenu.setMenuId(res);
                sysRoleMenuDao.add(sysRoleMenu);
            });
        }

        return 1;
    }

    @Override
    public int edit(SysRole sysRole) {
        sysRoleDao.edit(sysRole);
        sysRoleMenuDao.deleteByRid(sysRole.getId());
        List<Long> treelist = sysRole.getTreelist();
        if (!CollectionUtils.isEmpty(treelist)) {
            treelist.forEach(res -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getId());
                sysRoleMenu.setMenuId(res);
                sysRoleMenuDao.add(sysRoleMenu);
            });
        }
        return 1;
    }

    @Override
    public int delete(Long id) {
        return sysRoleDao.delete(id);
    }

    @Override
    public List<Map<String, Object>> roles() {
        return sysRoleDao.roles();
    }

    @Override
    public void t1() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("aabbb");
        sysRoleDao.add(sysRole);
        int a = 1 / 0;
    }

}
