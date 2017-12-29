package com.sunyard.itp.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class EncodingFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 处理请求乱码
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletRequest myRequest = new MyRequest(httpServletRequest);

        // 处理响应乱码
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(myRequest, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}

// 自定义request对象
class MyRequest extends HttpServletRequestWrapper {

    private HttpServletRequest request;
    private boolean hasEncode;

    public MyRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    // 对需要增强的方法 进行覆盖
    @Override
    public Map getParameterMap() {
        // 先获得请求方式
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post")) { // post方式请求
            try {
                request.setCharacterEncoding("utf-8");
                return request.getParameterMap();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (method.equalsIgnoreCase("get")) { // get方式请求
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (!hasEncode) {
                for (String parameterName : parameterMap.keySet()) {
                    String[] values = parameterMap.get(parameterName);
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            try {
                                values[i] = new String(values[i].getBytes("ISO-8859-1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                hasEncode = true;
            }
            return parameterMap;
        }
        return super.getParameterMap();
    }

    @Override
    public String getParameter(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        if (values == null) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        Map<String, String[]> parameterMap = getParameterMap();
        String[] values = parameterMap.get(name);
        return values;
    }
}