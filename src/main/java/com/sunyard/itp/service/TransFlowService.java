package com.sunyard.itp.service;

import java.util.List;
import java.util.Map;

import com.sunyard.itp.entity.Broadcast;
import com.sunyard.itp.entity.TransFlow;

/**
 * 流水业务类
 * @author zhix.huang
 *
 */
public interface TransFlowService {

	void addTransFlow(TransFlow transFlow);
	
	int queryTransFlow(Map<String, String> params);

	void updateTrade(TransFlow trans);

	List<Broadcast> broadcast(String mchntCd);

}
