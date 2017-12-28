package com.sunyard.itp.entity;

import java.io.Serializable;


/**
 * 
 * @author daox.nie
 * @created 2016年11月29日 上午11:35:27
 * @description 传到页面的提示框数据的实体类
 */
public class BackResult extends LogStatus implements Serializable
{

	/**   
	 * 
	 */
	private static final long serialVersionUID = -9146456459948957130L;

	

	/**
	 * 返回码
	 */
	private String code;
	
	/**
	 * 页面提示信息
	 */
	private String message;
	
	

	public BackResult()
	{
		super();
	}

	public BackResult(String code,String message)
	{
		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setBackResult(String code,String message) {
		this.code = code;
		this.message = message;		
	}
	public static String back(String code, Object message) {
		return new BackResult(code,message.toString()).toString();
	}

	
	
}
