package com.sunyard.itp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyard.itp.entity.PrecreateParams;
import com.sunyard.itp.entity.WxPerParams;

/**
 * 支付业务类
 * @author zhix.huang
 *
 */
public interface PrecreateService {
	String precreate(HttpServletRequest request,HttpServletResponse response, PrecreateParams payParams) throws Exception;
	/*
	 * 查询微信扫码模式一参数
	 */
	WxPerParams queryWxPerParams(String product_id);
	
	
}
