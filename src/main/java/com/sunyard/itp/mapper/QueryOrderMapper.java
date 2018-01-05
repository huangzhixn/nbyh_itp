package com.sunyard.itp.mapper;



import java.util.List;

import com.sunyard.itp.dto.TransFlowDto;
import com.sunyard.itp.entity.TransFlow;

public interface QueryOrderMapper {

	void updateTradeStatus(TransFlow transFlow);

	void updateTradeStatusWx(TransFlow transFlow);

	List<TransFlowDto> findAllDatas(TransFlow transFlow);
	
}
