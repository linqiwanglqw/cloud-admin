package com.lin.system.service;

import java.util.List;
import java.util.Map;

import com.lin.system.pojo.SysRole;


public interface SysRoleService {


    public List<SysRole> List(SysRole sysRole);

    public Map<String, Object> query(Long id);

    public int add(SysRole sysRole);

    public int edit(SysRole sysRole);

    public int delete(Long id);

    List<Map<String, Object>> roles();

    void t1();
}
