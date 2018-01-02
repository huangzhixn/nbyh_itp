package com.sunyard.itp.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunyard.itp.constant.CommonConst;
import com.sunyard.itp.entity.Message;
import com.sunyard.itp.service.QueryOrderService;

@Controller
public class QueryOrderController {
	@Autowired
	private QueryOrderService queryOrderService;
	
	private org.slf4j.Logger logger =  LoggerFactory.getLogger(PayController.class);

	@RequestMapping("/queryRound")
	@ResponseBody
	public String queryRound(String out_trade_no){
		Message message=null;
		
		try {
			message = queryOrderService.queryRound(out_trade_no);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return CommonConst.getResult(CommonConst.JSON_SYS_ERROR, "系统错误");
		}
		
		return CommonConst.getResult(CommonConst.RET_SUCCESS, message);
	}
	
	
}
