package com.sunyard.itp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunyard.itp.constant.CommonConst;
import com.sunyard.itp.entity.RefundParams;
import com.sunyard.itp.service.RefundService;


@Controller
public class RefundController {
	@Autowired
	RefundService refundService;
	
	@RequestMapping("/refund")
	@ResponseBody
	public String refund(RefundParams refundParams,ModelMap modelMap)throws Exception{
		 refundService.refund(refundParams);
		try {
			String str = refundService.refund(refundParams);
			return CommonConst.getResult(CommonConst.RET_SUCCESS, str);
		} catch (Exception e) {
			return CommonConst.getResult(CommonConst.RET_ERROR, "系统错误");
		}
		
	}
}
