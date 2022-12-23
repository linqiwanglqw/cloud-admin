package com.lin.system.service;

import java.util.List;
import java.util.Map;

import com.lin.system.pojo.SysUser;


public interface SysUserService
{


    public List<SysUser>List(SysUser sysUser);

    public SysUser query(Long id);

    public int add(SysUser sysUser);

    public int edit(SysUser sysUser);

    public int delete(Long id);



}
