package com.lin.utils;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class PageUtils {

    public static void start(){

        HttpServletRequest request = RequestUtlis.getRequest();
        String num = request.getParameter("pageNum");
        String size = request.getParameter("pageSize");
        PageHelper.startPage(StringUtils.isNotEmpty(num)?Integer.valueOf(num):1, StringUtils.isNotEmpty(size)?Integer.valueOf(size):10);
    }

}
