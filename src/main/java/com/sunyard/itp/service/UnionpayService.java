package com.sunyard.itp.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyard.itp.entity.PrecreateParams;
import com.sunyard.itp.entity.WxPerParams;

/**
 * 支付业务类
 * @author zhix.huang
 *
 */
public interface UnionpayService {
	String precreate(HttpServletRequest request,HttpServletResponse response, String totalFee)throws IOException;
	
	
	
}
