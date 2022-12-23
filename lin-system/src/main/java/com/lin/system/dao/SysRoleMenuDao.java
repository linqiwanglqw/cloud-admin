package com.lin.system.dao;

import java.util.List;

import com.lin.system.pojo.SysRoleMenu;


public interface SysRoleMenuDao {


    public List<SysRoleMenu> list(SysRoleMenu sysRoleMenu);

    public SysRoleMenu query(Long id);

    public int add(SysRoleMenu sysRoleMenu);

    public int edit(SysRoleMenu sysRoleMenu);

    public int delete(Long id);

    List<Long> gettree(Long id);

    int deleteByRid(Long id);
}
