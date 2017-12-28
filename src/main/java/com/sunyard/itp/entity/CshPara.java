package com.sunyard.itp.entity;

public class CshPara {
	private String mhtOrderNo;
	private String params;
	public String getMhtOrderNo() {
		return mhtOrderNo;
	}
	public void setMhtOrderNo(String mhtOrderNo) {
		this.mhtOrderNo = mhtOrderNo;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public CshPara(String mhtOrderNo, String params) {
		super();
		this.mhtOrderNo = mhtOrderNo;
		this.params = params;
	}
	public CshPara() {
		super();
	}
	@Override
	public String toString() {
		return "CshPara [mhtOrderNo=" + mhtOrderNo + ", params=" + params + "]";
	}
}
