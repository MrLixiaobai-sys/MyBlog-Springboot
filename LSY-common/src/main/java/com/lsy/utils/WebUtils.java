package com.lsy.utils;

import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebUtils
{
    /**
     * renderString 方法的作用是向客户端直接返回一个 JSON 格式的字符串作为 HTTP 响应
     * 将字符串渲染到客户端
     * 将指定的字符串直接写入 HTTP 响应体中，通常用于返回 JSON 数据
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static void renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
    设置 HTTP 响应头，以支持文件下载功能
     */
    public static void setDownLoadHeader(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fname= URLEncoder.encode(filename,"UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition","attachment; filename="+fname);
    }
}