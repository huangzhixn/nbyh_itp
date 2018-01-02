package com.sunyard.itp.mapper;



import com.sunyard.itp.entity.TransFlow;

public interface QueryOrderMapper {

	void updateTradeStatus(TransFlow transFlow);

	void updateTradeStatusWx(TransFlow transFlow);
	
}
