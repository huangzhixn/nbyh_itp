package com.sunyard.itp.utils.alipay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sunyard.itp.service.TransFlowService;

@Service
public class AlipayNotify {
	@Autowired
	private TransFlowService transFlowService;
	
	public boolean verify(Map<String, String> params) {
		if(transFlowService.queryTransFlow(params) >= 1){
			return true;
		}else{
		return false;
		}
	}
}
