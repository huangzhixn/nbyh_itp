package com.sunyard.itp.service;

import com.sunyard.itp.entity.WftPara;

/**
 * 查询订单业务类ssccccccc
 * @author zhix.huang
 *
 */
public interface WftService {
	void delete();
	void add(WftPara params);
	String queryWft();
}
