package com.lin.generate.pojo;

import lombok.Data;

@Data
public class GenColumn {
    //列名
    private String columnName;

    //属性类型  sql
    private String dataType;

    //属性类型  java
    private String attrType;

    //java  变量
    private String attrName;

    //列名备注
    private String comments;


}
