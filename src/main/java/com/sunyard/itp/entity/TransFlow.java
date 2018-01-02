package com.sunyard.itp.entity;

public class TransFlow {
	private String tradeNo;
	private String outTradeNo;
	private String buyerLogonId;
	private String tradeStatus;//支付状态  00-支付成功  01-正在输入密码支付，请确认 02-支付失败03-未付款交易超时关闭，或支付完成后全额退款 04-交易结束，不可退款
	private String totalAmount;
	private String receiptAmount;
	private String sendPayDate;
	private String buyerUserId;
	private String transType;
	
	/*支付方式 
	 * 0 -支付宝客户被扫 1 -支付宝客户主扫（动态码） 2-支付宝客户主扫（静态码）
	 * 3-微信客户被扫 4-微信客户主扫（动态码） 5-微信客户主扫（静态码）
	 * 6-银联客户被扫 7-银联客户主扫（动态码）8-银联客户主扫（静态码）
	 */
	private String payModel;//支付方式   
	
	public String getTradeStatusName(){
		if(tradeStatus == null || tradeStatus == "")
			return "";
		switch (tradeStatus) {
		case "00":
			return "收款成功";
		case "01":
			return "正在输入密码支付";
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
	public String getPayModelName(){
		if(payModel == null || payModel == "")
			return "";
		switch (payModel) {
		case "0":
			return "支付宝客户被扫";
		case "1":
			return "支付宝客户主扫（动态码）";
		case "2":
			return "支付宝客户主扫（静态码）";
		case "3":
			return "微信客户被扫 ";
		case "4":
			return "微信客户主扫（动态码）";
		case "5":
			return "微信客户主扫（静态码）";
		case "6":
			return "银联客户被扫";
		case "7":
			return "银联客户主扫（动态码）";
		case "8":
			return "银联客户主扫（静态码）";	

		default:
			return "";
		}
	}
	
	public String getPayModel() {
		return payModel;
	}
	public void setPayModel(String payModel) {
		this.payModel = payModel;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getBuyerLogonId() {
		return buyerLogonId;
	}
	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getReceiptAmount() {
		return receiptAmount;
	}
	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	public String getSendPayDate() {
		return sendPayDate;
	}
	public void setSendPayDate(String sendPayDate) {
		this.sendPayDate = sendPayDate;
	}
	public String getBuyerUserId() {
		return buyerUserId;
	}
	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}

	
}
