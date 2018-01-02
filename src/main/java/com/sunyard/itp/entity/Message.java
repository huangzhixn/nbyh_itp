package com.sunyard.itp.entity;

public class Message {
	private String payType;//0-支付宝，1-微信 2-银联
	private String amount; //支付金额
	private String payStatu;//支付状态  00-支付成功  01-正在输入密码支付，请确认 02-支付失败 03-未付款交易超时关闭，或支付完成后全额退款 04-交易结束，不可退款
							//05 -未支付  06- 转入退款 07-已关闭
	private String buyer;   //支付id
	private String out_trade_no;
	
	
	public String getPayTypeName(){
		if(payType == null || payType == "")
			return "";
		switch (payType) {
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
	public String getPayStatuName(){
		if(payStatu == null || payStatu == "")
			return "";
		switch (payStatu) {
		case "00":
			return "收款成功";
		case "01":
			return "正在输入密码支付，请确认";
		case "02":
			return "收款失败";
		case "03":
			return "未付款交易超时关闭，或支付完成后全额退款";
		case "04":
			return "交易结束，不可退款";
		case "05":
			return "未支付";
		case "06":
			return "转入退款";
		case "07":
			return "已关闭";
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
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
}
