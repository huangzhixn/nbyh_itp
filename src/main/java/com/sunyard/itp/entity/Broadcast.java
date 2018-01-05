package com.sunyard.itp.entity;

public class Broadcast {
	private String transType;
	private String receiptAmount;
	private String outTradeNo;
	public String getTransTypeName() {
		if(transType == null || transType == "")
			return "";
		switch (transType) {
		case "0":
			return "支付宝";
		case "1":
			return "微信";
		case "2":
			return "银联";
		default:
			return "";
		}
		
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getReceiptAmount() {
		if(receiptAmount != null && receiptAmount != ""){
			double b = (Double.parseDouble(receiptAmount)) / 100;
			String bb = Double.toString(b);
			if(transType.equals("1"))
				return bb;
			return receiptAmount;
			}else{
				return "";
			}
	}
	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	public Broadcast(String transType, String receiptAmount) {
		super();
		this.transType = transType;
		this.receiptAmount = receiptAmount;
	}
	public Broadcast() {
		super();
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	
	
}
