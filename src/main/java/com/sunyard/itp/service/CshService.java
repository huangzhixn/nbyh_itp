package com.sunyard.itp.service;

import com.sunyard.itp.entity.CshPara;

/**
 * 查询订单业务类
 * @author zhix.huang
 *
 */
public interface CshService {
	void delete();
	void add(CshPara params);
	String queryCsh(String mhtOrderNo);
}
