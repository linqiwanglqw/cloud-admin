package com.lin.system.service.impl;

import java.util.List;

import com.lin.system.dao.SysUserDao;
import com.lin.system.dao.SysUserRoleDao;
import com.lin.system.pojo.SysUser;
import com.lin.system.pojo.SysUserRole;
import com.lin.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public List<SysUser> List(SysUser sysUser) {
        return sysUserDao.list(sysUser);
    }

    @Override
    public SysUser query(Long id) {
        return sysUserDao.query(id);
    }

    @Override
    public int add(SysUser sysUser) {
        //添加 用户表
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUserDao.add(sysUser);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(sysUser.getRole());
        //添加用户角色表
        return sysUserRoleDao.add(sysUserRole);
    }

    @Override
    public int edit(SysUser sysUser) {
        return sysUserDao.edit(sysUser);
    }

    @Override
    public int delete(Long id) {
        return sysUserDao.delete(id);
    }


}
