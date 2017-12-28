package com.sunyard.itp.controller;



import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * @Title: ReturnController.java
 * @Package com.sunyard.itp.controller
 * @Description: 支付宝同步通知跳转页面
 * @author 黄志鑫
 * @date 2017年8月11日 下午3:10:36
 * @version 1.0
 */
@Controller
public class ReturnController {
	protected org.slf4j.Logger logger =  LoggerFactory.getLogger(ReturnController.class);
	
	
	@RequestMapping("/aliReturn")
	public String aliReturn(String aa) {
		System.out.println("get----------------"+aa);
		logger.info("同步通知");
		return "aliReturn";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
