/**
 * 
 */
package com.sunyard.itp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunyard.itp.entity.Test;
import com.sunyard.itp.service.TestService;

/**
 * 
 * @Title: TestDb.java
 * @Package com.sunyard.itp.controller
 * @Description: 测试数据库连接
 * @author zhix.huang
 * @date 2017年7月21日 下午2:21:20
 * @version 1.0
 */
@Controller
public class TestDb {
	public   Logger logger = Logger.getLogger(TestDb.class);

	@Autowired
	private TestService TestService;
	/**
	 * @Description: 测试数据库连接情况
	 * @return   
	 * String  
	 * @throws
	 * @author zhix.huang
	 * @date 2017年7月21日 下午2:22:07
	 */
	@RequestMapping("/test")
	public String test(){
		Test t = new Test();
		t.setId("125");
		t.setName("黄志鑫");
		t.setBirthday("2016-01-01");
		
		TestService.add(t);
		System.out.println("dd");
		return "success";
	}
	public  void  test1(){
		System.out.println("ff");
		logger.info("dd");
		logger.debug("dd");
	}
		
	public static void main(String[] args) {
		TestDb d = new TestDb();
		d.test1();
	}

}
