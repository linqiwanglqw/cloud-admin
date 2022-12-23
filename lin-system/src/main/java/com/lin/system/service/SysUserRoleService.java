package com.lin.system.service;

import java.util.List;

import com.lin.system.pojo.SysUserRole;


public interface SysUserRoleService {


    public List<SysUserRole> List(SysUserRole sysUserRole);

    public SysUserRole query(Long id);

    public int add(SysUserRole sysUserRole);

    public int edit(SysUserRole sysUserRole);

    public int delete(Long id);

}
