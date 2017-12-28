package com.sunyard.itp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunyard.itp.entity.CshPara;
import com.sunyard.itp.service.CshService;
import com.sunyard.itp.utils.wxpay.Util;



/**
 * 
 * @Title: CshNotify.java
 * @Package com.sunyard.itp.controller
 * @Description: 城商行异步通知转发
 * @author zhix.huang
 * @date 2017年8月30日 下午3:58:15
 * @version 1.0
 */
@Controller
public class CshNotifyController {
	@Autowired
	private CshService cshService;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 * @Description: 接收城商行发送过来的通知
	 * @param request
	 * @param response
	 * @throws IOException void  
	 * @throws
	 * @author zhix.huang
	 * @date 2017年8月30日 下午3:58:58
	 */
	@RequestMapping(value="cshNotify",method = RequestMethod.POST)
	@ResponseBody
	public String cshNotify(HttpServletRequest request,HttpServletResponse response) throws IOException{	
		logger.debug("接收城商行通知");
		logger.debug("访问ip为：---------"+request.getRemoteAddr());
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
           sb.append(line);
        }
        String params = sb.toString();
        String[] arrays = params.split("&");
        Map<String, String> strMap = new HashMap<String, String>();  
        for(String array : arrays){
        	String[] a = array.split("=");
        	strMap.put(a[0], a[1]);
        }
        logger.debug(strMap.toString());
        String mhtOrderNo = strMap.get("mhtOrderNo");
        logger.debug("------"+params);
        logger.debug("---------商户订单号为："+mhtOrderNo);
//	   String params = new String(Util.readInput(request.getInputStream()),"utf-8");
        logger.debug("将接收到的信息存入数据库！");
        try {
//			cshService.delete();
			CshPara cshPara = new CshPara();
			cshPara.setMhtOrderNo(mhtOrderNo);
			cshPara.setParams(params);
			logger.debug("通知数据为-------"+cshPara);
			cshService.add(cshPara);//存入数据库
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return "{'result_code':'000001','result_message':'fail'}";
		}
        //响应返回
        logger.debug("{'result_code':'000000','result_message':'success'}");
//        PrintWriter out=response.getWriter();
        return "success=Y";
//        out.println("{'result_code':'000000','result_message':'success'}");
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
	@RequestMapping(value = "queryCshNotify")
	@ResponseBody
	public String queryCshNotify(HttpServletResponse response,HttpServletRequest request,String mhtOrderNo) throws IOException{
		logger.debug("提取城商行通知数据");
		logger.debug("传递过来的参数为"+mhtOrderNo);
		logger.debug("访问ip为：---------"+request.getRemoteAddr());
		//取出数据库存的通知数据
		String params;
		try {
			params = cshService.queryCsh(mhtOrderNo);
			//取出之后删除
//			cshService.delete();
		} catch (Exception e) {
			return "取出数据异常";
		}
		logger.debug("提取到的通知数据为:"+params);
		return params;
//		PrintWriter out=response.getWriter();
//        out.println(params);
	}
}
