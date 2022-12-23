package com.lin.generate.service.impl;

import com.lin.generate.service.GenService;
import com.lin.generate.dao.GenDao;
import com.lin.generate.pojo.GenTable;
import com.lin.generate.utils.velocityUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class GenServiceImpl implements GenService {

    @Autowired
    private GenDao genDao;

    @Override
    public List<Map> list(String name) {
        return genDao.list(name);
    }

    @Override
    public byte[] code(String tables) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 分割
        String[] table = tables.split(",");
        for (String name : table) {
            GenTable genTable = new GenTable();
            genTable.setTableName(name);
            //通过表名获取列数据
            List<Map> columns = genDao.queryColumnByTableName(name);
            velocityUtil.out(genTable, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
