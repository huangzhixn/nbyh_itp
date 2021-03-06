package com.sunyard.itp.mapper;



import java.util.List;

import com.sunyard.itp.entity.Broadcast;
import com.sunyard.itp.entity.TransFlow;

public interface TransFlowMapper {
	void addTransFlow(TransFlow transFlow);

	int queryTransFlow(TransFlow transFlow);

	void updateTrade(TransFlow trans);

	List<Broadcast> broadcast();

	void setBroadcasted(String outTradeNo);

	void updateReStatu(String out_trade_no);
}
