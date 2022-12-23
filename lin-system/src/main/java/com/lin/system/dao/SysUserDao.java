package com.lin.system.dao;

import java.util.List;

import com.lin.system.pojo.SysUser;
import com.lin.utils.LoginUser;

public interface SysUserDao
{


    public List<SysUser> list(SysUser sysUser);

    public SysUser query(Long id);

    public int add(SysUser sysUser);

    public int edit(SysUser sysUser);

    public int delete(Long id);

    LoginUser loadUserByUsername(String name);

    List<String> getPower(Long id);
}
