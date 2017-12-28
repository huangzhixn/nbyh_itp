package com.sunyard.itp.entity;

public class RefundParams {
	private String outTradeNo;
	private String receiptAmount;	 //订单实际支付金额
	private String refundFee;   //退款金额
	
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getreceiptAmount() {
		return receiptAmount;
	}
	public void setreceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}	
}
