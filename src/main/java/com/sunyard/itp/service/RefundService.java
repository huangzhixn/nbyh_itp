package com.sunyard.itp.service;


import com.alipay.api.AlipayApiException;
import com.sunyard.itp.entity.RefundParams;

/**
 * 支付业务类
 * @author zhix.huang
 *
 */
public interface RefundService {
	String refund(RefundParams refundParams) throws AlipayApiException;
}
