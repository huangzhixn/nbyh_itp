package com.sunyard.itp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sunyard.itp.entity.RefundParams;
import com.sunyard.itp.service.RefundService;


@Controller
public class RefundController {
	@Autowired
	RefundService refundService;
	
	@RequestMapping("/refund")
	public String refund(RefundParams refundParams,ModelMap modelMap)throws Exception{
		String result = refundService.refund(refundParams);
		modelMap.addAttribute("result",result);
		return  "refundResult";
	}
}
