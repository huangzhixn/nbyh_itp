package com.sunyard.itp.mapper;



import com.sunyard.itp.entity.TransFlow;

public interface TransFlowMapper {
	void addTransFlow(TransFlow transFlow);

	int queryTransFlow(TransFlow transFlow);

	void updateTrade(TransFlow trans);
}
