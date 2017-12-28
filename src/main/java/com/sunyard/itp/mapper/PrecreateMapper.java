package com.sunyard.itp.mapper;

import com.sunyard.itp.entity.WxPerParams;

public interface PrecreateMapper {

	void addWxPerParams(WxPerParams wxPerParams);
	WxPerParams queryWxPerParams(String productId);
}
