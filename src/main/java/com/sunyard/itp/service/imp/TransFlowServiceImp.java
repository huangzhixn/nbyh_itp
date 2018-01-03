package com.sunyard.itp.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunyard.itp.entity.Broadcast;
import com.sunyard.itp.entity.TransFlow;
import com.sunyard.itp.mapper.TransFlowMapper;
import com.sunyard.itp.service.TransFlowService;



/**
 * 流水业务实现类
 * @author zhix.huang
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class TransFlowServiceImp implements TransFlowService {
	@Autowired
	private TransFlowMapper transFlowMapper;
	
	
	public void addTransFlow(TransFlow transFlow) {
		System.out.println("33333333333333333333333");
		transFlowMapper.addTransFlow(transFlow);
		
	}
	public int queryTransFlow(Map<String, String> params){
		TransFlow transFlow = new TransFlow();
		transFlow.setOutTradeNo(params.get("out_trade_no"));
		
		return transFlowMapper.queryTransFlow(transFlow);
	}
	@Override
	public void updateTrade(TransFlow trans) {
		transFlowMapper.updateTrade(trans);
		
	}
	@Override
	public List<Broadcast> broadcast(String mchntCd) {
		List<Broadcast> broadcast = transFlowMapper.broadcast();
		for(Broadcast br :broadcast){
			transFlowMapper.setBroadcasted(br.getOutTradeNo());
		}
		return broadcast;
	}

}

