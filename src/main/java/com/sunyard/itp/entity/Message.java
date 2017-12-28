package com.sunyard.itp.entity;

public class Message {
	private String payType;//1-支付宝，2-微信 3-银联
	private String amount; //支付金额
	private String payStatu;//支付状态  00-支付成功  01-正在输入密码支付，请确认 02-支付失败
	private String buyer;   //支付id
	
	public String getPayTypeName(){
		if(payType == null || payType == "")
			return "";
		switch (payType) {
		case "1":
			return "支付宝";
		case "2":
			return "微信";
		default:
			return "";
		}	
	}
	public String getPayStatuName(){
		if(payStatu == null || payStatu == "")
			return "";
		switch (payStatu) {
		case "00":
			return "支付成功";
		case "01":
			return "正在输入密码支付，请确认";
		case "02":
			return "支付失败";
		default:
			return "";
		}	
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayStatu() {
		return payStatu;
	}
	public void setPayStatu(String payStatu) {
		this.payStatu = payStatu;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	@Override
	public String toString() {
		return "Message [payType=" + payType + ", amount=" + amount + ", payStatu=" + payStatu + ", buyer=" + buyer
				+ "]";
	}
}
