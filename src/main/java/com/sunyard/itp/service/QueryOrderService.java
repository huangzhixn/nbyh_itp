package com.sunyard.itp.service;

import java.util.Map;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sunyard.itp.entity.Message;

/**
 * 查询订单业务类
 * @author zhix.huang
 *
 */
public interface QueryOrderService {
	AlipayTradeQueryResponse queryAlipayOrder(String out_trade_no)throws Exception;
	Map<String, String> queryWxOrder(String out_trade_no)throws Exception;
	Message queryRound(String out_trade_no)throws Exception ;
}
