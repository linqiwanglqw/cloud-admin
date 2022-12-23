package com.lin.generate.controller;

import com.lin.common.security.annotation.RequiresPermissions;
import com.lin.generate.service.GenService;
import com.lin.utils.PageUtils;
import com.lin.utils.R;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GenController {
    @Autowired
    private GenService genService;


    @GetMapping("/list")
    @RequiresPermissions("generate:list")
    public Object list(String name){
        PageUtils.start();
        return R.table(genService.list(name));
    }


    @RequestMapping("/code")
    @RequiresPermissions("generate:code")
    public void  code(String tables, HttpServletResponse response) throws IOException {
        byte[] data = genService.code(tables);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"lin.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }


}
