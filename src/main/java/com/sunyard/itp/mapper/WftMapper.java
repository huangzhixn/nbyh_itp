package com.sunyard.itp.mapper;

import com.sunyard.itp.entity.WftPara;

public interface WftMapper {
	void delete();
	void add(WftPara wftPara);
	String query();
}
