package com.lin.system.controller;


import com.lin.common.security.annotation.RequiresPermissions;
import com.lin.system.pojo.SysUser;
import com.lin.system.service.SysUserService;

import com.lin.utils.PageUtils;
import com.lin.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询列表
     *
     * @return
     */
    @RequiresPermissions("system:sysUser:list")
    @GetMapping("/list")
    public Object list(SysUser sysUser) {
        PageUtils.start();
        List<SysUser> list = sysUserService.List(sysUser);
        return R.table(list);
    }


    /**
     * 通过id 查询
     *
     * @return
     */
    @RequiresPermissions("system:sysUser:query")
    @GetMapping(value = "/{id}")
    public Object query(@PathVariable("id") Long id) {
        return R.ok(sysUserService.query(id));
    }

    /**
     * 新增
     */
    @RequiresPermissions("system:sysUser:add")
    @PostMapping
    public Object add(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.add(sysUser));
    }

    /**
     * 修改
     */
    @RequiresPermissions("system:sysUser:edit")
    @PutMapping
    public Object edit(@RequestBody SysUser sysUser) {
        return R.ok(sysUserService.edit(sysUser));
    }

    /**
     * 删除
     */
    @RequiresPermissions("system:sysUser:remove")
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") Long id) {
        return R.ok(sysUserService.delete(id));
    }


}
