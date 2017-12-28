package com.sunyard.itp.mapper;

import com.sunyard.itp.entity.CshPara;

public interface CshMapper {
	void delete();
	void add(CshPara cshPara);
	String query(String mhtOrderNo);
}
