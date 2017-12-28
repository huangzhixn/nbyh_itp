package com.sunyard.itp.service;
import com.alipay.api.AlipayApiException;
import com.sunyard.itp.entity.Message;
import com.sunyard.itp.entity.TradePayParams;
/**
 * 主扫业务类
 * @author zhix.huang
 *
 */
public interface TradePayService {
	Message tradePay(TradePayParams tradePayparams) throws AlipayApiException, Exception;
}
