package com.lin.generate.pojo;

import lombok.Data;

import java.util.List;

@Data
public class GenTable {
    //表名
    private String tableName;

    //表的主键
    private GenColumn pk;

    //列数据
    private List<GenColumn> columns;

    //类名称  大写
    private String ClassName;

    //类名称  小写
    private String cName;



}
