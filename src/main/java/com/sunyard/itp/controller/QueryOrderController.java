package com.sunyard.itp.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunyard.itp.constant.CommonConst;
import com.sunyard.itp.entity.DataGrid;
import com.sunyard.itp.entity.Message;
import com.sunyard.itp.entity.TransFlow;
import com.sunyard.itp.service.QueryOrderService;
import com.sunyard.itp.utils.IPage;


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
	
	@RequestMapping("/queryTransFlw")
	@ResponseBody
	public String queryTransFlw(@ModelAttribute TransFlow transFlow,@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "10") Integer rows){
		if(transFlow == null){
			transFlow = new TransFlow();
		 }
		DataGrid<TransFlow> dataGrid=null;
		
		try {
			IPage ipage = new IPage(page,rows);
			dataGrid = queryOrderService.queryTransFlw(transFlow,ipage);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return CommonConst.getResult(CommonConst.JSON_SYS_ERROR, "系统错误");
		}
		
		return CommonConst.getResult(CommonConst.RET_SUCCESS, dataGrid);
	}
}
