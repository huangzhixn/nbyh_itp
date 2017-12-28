package com.sunyard.itp.entity;

/**   
 * @author: tianch.liu
 * date:   2017-3-7 下午4:09:14     
 */
public class LogStatus {
	
	public static String SUCCESS_STATUS = "0";
	public static String FAIL_STATUS = "1";
	
	private String status = SUCCESS_STATUS;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
