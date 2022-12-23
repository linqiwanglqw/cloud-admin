package com.lin.generate.utils;

import com.lin.generate.pojo.GenColumn;
import com.lin.generate.pojo.GenTable;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class velocityUtil {


    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList() {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/pojo.java.vm");
        templates.add("vm/java/dao.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        templates.add("vm/js/api.js.vm");

        return templates;
    }


    //开始导出
    public static void out(GenTable genTable, List<Map> columns, ZipOutputStream zip) {
        //获取配置文件
        Configuration config = getConfig();
        genTable.setClassName(convertToCamelCase(genTable.getTableName()));  //类名
        genTable.setCName(StringUtils.uncapitalize(genTable.getClassName()));
        //列数据
        List<GenColumn> columsList = new ArrayList();
        for (Map map : columns) {
            GenColumn column = new GenColumn();
            column.setColumnName(map.get("columnName").toString()); // 列名
            column.setDataType(map.get("dataType").toString());  //列类型
            column.setComments(map.get("columnComment").toString()); //列描述
            column.setAttrName(StringUtils.uncapitalize(convertToCamelCase(column.getColumnName())));
            column.setAttrType(config.getString(column.getDataType()));

            //判断是否是主键
            if (map.containsKey("columnKey") & "PRI".equals(map.get("columnKey").toString()) & genTable.getPk() == null) {
                genTable.setPk(column);
            }
            columsList.add(column);
        }
        genTable.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (genTable.getPk() == null) {
            genTable.setPk(genTable.getColumns().get(0));
        }

        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        VelocityContext context = new VelocityContext();
        // 表名
        context.put("tableName", genTable.getTableName());
        context.put("package", config.getString("package"));
        context.put("moduleName", config.getString("moduleName"));
        context.put("columns", genTable.getColumns());
        context.put("pkColumn", genTable.getPk());
        context.put("table", genTable);
        context.put("ClassName", genTable.getClassName());
        context.put("className", genTable.getCName());

        //获取模板列表
        List<String> templateList = getTemplateList();

        for (String template : templateList) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                zip.putNextEntry(new ZipEntry(getFileName(template, genTable.getClassName(), config.getString("package"))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private static String getFileName(String template, String className, String aPackage) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (org.apache.commons.lang.StringUtils.isNotBlank(aPackage)) {
            packagePath += aPackage.replace(".", File.separator) + File.separator;
        }
        if (template.contains("dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }
        if (template.contains("pojo.java.vm")) {
            return packagePath + "pojo" + File.separator + className + ".java";
        }

        if (template.contains("service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("serviceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }

        if (template.contains("api.js.vm")) {
            return "api" + File.separator + className + File.separator + "index.js";
        }

        return null;

    }


    //将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }


    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("table.properties");
        } catch (ConfigurationException e) {
            return null;
        }

    }


}
