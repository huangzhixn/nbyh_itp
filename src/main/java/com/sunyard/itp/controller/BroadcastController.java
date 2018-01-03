package com.sunyard.itp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sunyard.itp.constant.CommonConst;
import com.sunyard.itp.constant.PayConst;
import com.sunyard.itp.entity.Broadcast;
import com.sunyard.itp.entity.Message;
import com.sunyard.itp.entity.PrecreateParams;
import com.sunyard.itp.entity.TradePayParams;
import com.sunyard.itp.service.PayQrCodeService;
import com.sunyard.itp.service.PrecreateService;
import com.sunyard.itp.service.RefundService;
import com.sunyard.itp.service.TradePayService;
import com.sunyard.itp.service.TransFlowService;
import com.sunyard.itp.service.UnionpayService;
import com.sunyard.itp.service.imp.PrecreateServiceImp;
import com.sunyard.itp.utils.wxpay.HttpRequest;
import com.sunyard.itp.utils.wxpay.HttpUtil;
import com.sunyard.itp.utils.wxpay.PayCommonUtil;
import com.sunyard.itp.utils.wxpay.WXPay;
import com.sunyard.itp.utils.wxpay.WXPayConfigImpl;
import com.sunyard.itp.utils.wxpay.WXPayConstants.SignType;
import com.sunyard.itp.utils.wxpay.WXPayUtil;

@Controller
public class BroadcastController {
	
	@Autowired
	private TransFlowService transFlowService;
	
	private org.slf4j.Logger logger =  LoggerFactory.getLogger(PayController.class);

	@RequestMapping("broadcast")
	@ResponseBody
	public String broadcast(String mchntCd){
		List<Broadcast> broadcast = null;
		try {
			broadcast = transFlowService.broadcast(mchntCd);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return CommonConst.getResult(CommonConst.RET_ERROR, "系统错误");
		}
		return CommonConst.getResult(CommonConst.RET_SUCCESS, broadcast);
	}



}
