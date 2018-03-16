package com.sunyard.itp.controller;

import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sunyard.itp.constant.CommonConst;
import com.sunyard.itp.entity.Broadcast;
import com.sunyard.itp.service.TransFlowService;

@Controller
public class BroadcastController {
	
	@Autowired
	private TransFlowService transFlowService;
	
	private org.slf4j.Logger logger =  LoggerFactory.getLogger(PayController.class);
	/**
	 * @Description: 语音播报轮循接口
	 * @author zhix.huang
	 * @date 2018/3/16 11:30
	 */
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
