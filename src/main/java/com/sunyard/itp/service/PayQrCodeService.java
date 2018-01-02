package com.sunyard.itp.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.sunyard.itp.entity.CshPara;

/**
 * 查询订单业务类
 * @author zhix.huang
 *
 */
public interface PayQrCodeService {

	void createQr(HttpServletResponse resp,String totalFee)throws IOException;
	
}
