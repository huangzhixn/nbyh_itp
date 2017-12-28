package com.sunyard.itp.constant;

import com.sunyard.itp.entity.BackResult;
import com.sunyard.itp.utils.JsonUtil;

public class CommonConst {

	private CommonConst() {}
	
	//返回失败
	public static final String RET_FAIL = "0";
	//返回成功
	public static final String RET_SUCCESS = "1";
	//密码重置
	public static final String RET_RESET = "2";
	//返回系统异常
	public static final String RET_ERROR = "3";
	
	
	
	public static final String JSON_SYS_ERROR = JsonUtil.ObjectToString(new BackResult(RET_ERROR, new String("系统异常")));
	public static final String JSON_RESET = JsonUtil.ObjectToString(new BackResult(RET_RESET, new String("重置密码")));
	
	public static String getResult(String code,Object obj){
		return JsonUtil.ObjectToString(new BackResult(code, JsonUtil.ObjectToString(obj)));
	}
	
	public static String getResult(String code,String obj){
		return JsonUtil.ObjectToString(new BackResult(code, new String(obj)));
	}
	public static String getResult(BackResult br){
		return JsonUtil.ObjectToString(br);
	}
	
}
