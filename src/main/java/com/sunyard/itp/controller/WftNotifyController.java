package com.sunyard.itp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sunyard.itp.entity.WftPara;
import com.sunyard.itp.service.WftService;



/**
 * 
 * @Title: wftNotify.java
 * @Package com.sunyard.itp.controller
 * @Description: 城商行异步通知转发
 * @author zhix.huang
 * @date 2017年8月30日 下午3:58:15
 * @version 1.0
 */
@Controller
public class WftNotifyController {
	@Autowired
	private WftService wftService;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 * @Description: 接收威富通发送过来的通知
	 * @param request
	 * @param response
	 * @throws IOException void  
	 * @throws
	 * @author zhix.huang
	 * @date 2017年8月30日 下午3:58:58
	 */
	@RequestMapping(value="wftNotify",method = RequestMethod.POST)
	public void wftNotify(HttpServletRequest request,HttpServletResponse response) throws IOException{	
		logger.info("接收威富通通知");
		logger.info("访问ip为：---------"+request.getRemoteAddr());
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
           sb.append(line);
        }
        String params = sb.toString();
//	   String params = new String(Util.readInput(request.getInputStream()),"utf-8");
        logger.info("将接收到的信息存入数据库！");
        wftService.delete();
        WftPara wftPara = new WftPara();
        wftPara.setNo("1");
        wftPara.setParams(params);
        logger.info("通知数据为-------"+params);
        wftService.add(wftPara);//存入数据库
        //响应返回
        logger.info("SUCCESSS");
        PrintWriter out=response.getWriter();
        out.println("SUCCESSS");
//        response.getWriter().print("{'result_code':'000000','result_message':'success'}");
//        response.getWriter().flush();
//        response.getWriter().close();
	}
	/**
	 * 
	 * @Description: 提供异步通知查询
	 * @param response
	 * @throws IOException void  
	 * @throws
	 * @author zhix.huang
	 * @date 2017年8月30日 下午4:00:42
	 */
	@RequestMapping(value = "queryWftNotify",method = RequestMethod.POST)
	public void querywftNotify(HttpServletResponse response,HttpServletRequest request) throws IOException{
		logger.info("提取威富通通知数据");
		logger.info("访问ip为：---------"+request.getRemoteAddr());
		//取出数据库存的通知数据
		String params = wftService.queryWft();
		//取出之后删除
		wftService.delete();
		logger.info("提取到的通知数据为:"+params);
		PrintWriter out=response.getWriter();
        out.println(params);
	}
}
