package com.sunyard.itp.service.imp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sunyard.itp.constant.PayConst;
import com.sunyard.itp.controller.PayController;
import com.sunyard.itp.entity.Message;
import com.sunyard.itp.entity.TransFlow;
import com.sunyard.itp.mapper.QueryOrderMapper;
import com.sunyard.itp.service.QueryOrderService;
import com.sunyard.itp.utils.DateUtil;
import com.sunyard.itp.utils.wxpay.WXPay;
import com.sunyard.itp.utils.wxpay.WXPayConfigImpl;
/**
 * 查询订单业务类
 * @author zhix.huang
 *
 */
@Service
public class QueryOrderServiceImp implements QueryOrderService{
	private org.slf4j.Logger logger =  LoggerFactory.getLogger(QueryOrderServiceImp.class);
	@Autowired
	private QueryOrderMapper queryOrderMapper;
	/**
	 * 查询支付宝订单
	 * @param outTradeNo
	 * @throws Exception 
	 */
	public AlipayTradeQueryResponse queryAlipayOrder(String out_trade_no) throws Exception {
		// TODO Auto-generated method stub
		AlipayClient alipayClient = new DefaultAlipayClient(PayConst.OPEN_API_DOMAIN,
				PayConst.APP_ID,
				PayConst.PRIVATE_KEY,
				"json","UTF-8",
				PayConst.ALIPAY_PUBLIC_KEY,
				PayConst.SIGN_TYPE);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		System.out.println("-----"+out_trade_no);
		request.setBizContent("{" +
				"\"out_trade_no\":\""+out_trade_no+"\"" +
		
		"  }");
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		if(response.isSuccess()){
		System.out.println("调用查询成功");
		
		return response;
		} else {
			System.out.println("调用查询失败");
			System.out.println(response.getBody());
			return null;
			}		
	}
	/**
	 * 查询微信订单
	 * @param outTradeNo
	 * @throws Exception 
	 */
	public  Map<String, String> queryWxOrder(String out_trade_no) throws Exception {
		 System.out.println("查询订单");
			//加载微信支付参数
			WXPayConfigImpl config =  WXPayConfigImpl.getInstance();
			WXPay wxpay = new WXPay(config);
			HashMap<String, String> data = new HashMap<String, String>();
		        data.put("out_trade_no", out_trade_no);
//		        data.put("transaction_id", "4008852001201608221962061594");		       
		            Map<String, String> r = wxpay.orderQuery(data);
		            System.out.println(r);
//		            System.out.println(r.get("result_code"));
//		            System.out.println(r.get("out_trade_no"));
//		            System.out.println(r.get("settlement_total_fee"));
//		            System.out.println(r.get("total_fee"));
//		            System.out.println(r.get("time_end"));
//		            System.out.println(r.get("cash_fee"));
		            return r;        
		
	}
	@Override
	public Message queryRound(String out_trade_no) throws Exception {
		Message message = new Message();
		TransFlow transFlow =new TransFlow();
		transFlow.setOutTradeNo(out_trade_no);
		if(out_trade_no.startsWith("alipay-tra")){
			AlipayTradeQueryResponse resp =	queryAlipayOrder(out_trade_no);
			logger.debug("--------"+resp.getBody());
			
			message.setAmount(resp.getTotalAmount());
			message.setBuyer(resp.getBuyerLogonId());
			message.setPayType("0");
			message.setOut_trade_no(out_trade_no);
			if(resp.isSuccess()){
				if(resp.getCode().equals("10000") && resp.getTradeStatus().equals("TRADE_SUCCESS")){
					logger.debug("支付成功");
					
					transFlow.setTradeStatus("00");
					transFlow.setReceiptAmount(resp.getReceiptAmount());
					queryOrderMapper.updateTradeStatus(transFlow);
					message.setPayStatu("00");
					
				}else if(resp.getCode().equals("10000") && resp.getTradeStatus().equals("WAIT_BUYER_PAY")){
					logger.debug("买家正在支付，请确认！");
					
					message.setPayStatu("01");
				}else if(resp.getCode().equals("10000") && resp.getTradeStatus().equals("TRADE_CLOSED")){
					logger.debug("未付款交易超时关闭，或支付完成后全额退款！");
					transFlow.setTradeStatus("03");
					transFlow.setReceiptAmount(resp.getReceiptAmount());
					queryOrderMapper.updateTradeStatus(transFlow);
					message.setPayStatu("03");
				}
				else if(resp.getCode().equals("10000") && resp.getTradeStatus().equals("TRADE_FINISHED")){
					logger.debug("交易结束，不可退款！");
					transFlow.setTradeStatus("04");
					transFlow.setReceiptAmount(resp.getReceiptAmount());
					queryOrderMapper.updateTradeStatus(transFlow);
					message.setPayStatu("04");
				}
				else{
					logger.debug("状态未知!");
					
					message.setPayStatu("02");
				}
			}
				
			
			
		}else if(out_trade_no.startsWith("weixin-tra")) {
			
			Map<String, String> wxresp = queryWxOrder( out_trade_no);
		
			message.setAmount(wxresp.get("total_fee"));
			message.setBuyer(wxresp.get("openid"));
			message.setPayType("1");
			message.setOut_trade_no(out_trade_no);
			if(wxresp.get("return_code").equals("SUCCESS") && wxresp.get("trade_state").equals("SUCCESS")){
				transFlow.setTradeStatus("00");
				transFlow.setBuyerLogonId(wxresp.get("openid"));
				transFlow.setBuyerUserId(wxresp.get("openid"));
				transFlow.setTotalAmount(wxresp.get("total_fee"));
				transFlow.setReceiptAmount(wxresp.get("settlement_total_fee"));
				transFlow.setSendPayDate(DateUtil.dateformat(wxresp.get("time_end")));
				transFlow.setTradeNo(wxresp.get("transaction_id"));
				message.setPayStatu("00");
			}else if(wxresp.get("return_code").equals("SUCCESS") && wxresp.get("trade_state").equals("USERPAYING")){
				transFlow.setTradeStatus("01");
				transFlow.setBuyerLogonId(wxresp.get("openid"));
				transFlow.setBuyerUserId(wxresp.get("openid"));
				transFlow.setTotalAmount(wxresp.get("total_fee"));
				transFlow.setReceiptAmount(wxresp.get("settlement_total_fee"));
				transFlow.setSendPayDate(wxresp.get("time_end"));
				transFlow.setTradeNo(wxresp.get("transaction_id"));
				message.setPayStatu("01");
			}else if(wxresp.get("return_code").equals("SUCCESS") && wxresp.get("trade_state").equals("NOTPAY")){
				logger.debug("未支付");
				transFlow.setTradeStatus("05");
				transFlow.setBuyerLogonId(wxresp.get("openid"));
				transFlow.setBuyerUserId(wxresp.get("openid"));
				transFlow.setTotalAmount(wxresp.get("total_fee"));
				transFlow.setReceiptAmount(wxresp.get("settlement_total_fee"));
				transFlow.setSendPayDate(wxresp.get("time_end"));
				transFlow.setTradeNo(wxresp.get("transaction_id"));
				message.setPayStatu("05");
			}else if(wxresp.get("return_code").equals("SUCCESS") && wxresp.get("trade_state").equals("REFUND")){
				logger.debug("转入退款");
				transFlow.setTradeStatus("06");
				transFlow.setBuyerLogonId(wxresp.get("openid"));
				transFlow.setBuyerUserId(wxresp.get("openid"));
				transFlow.setTotalAmount(wxresp.get("total_fee"));
				transFlow.setReceiptAmount(wxresp.get("settlement_total_fee"));
				transFlow.setSendPayDate(wxresp.get("time_end"));
				transFlow.setTradeNo(wxresp.get("transaction_id"));
				message.setPayStatu("06");
			}else if(wxresp.get("return_code").equals("SUCCESS") && wxresp.get("trade_state").equals("CLOSED")){
				logger.debug("已关闭");
				transFlow.setTradeStatus("07");
				transFlow.setBuyerLogonId(wxresp.get("openid"));
				transFlow.setBuyerUserId(wxresp.get("openid"));
				transFlow.setTotalAmount(wxresp.get("total_fee"));
				transFlow.setReceiptAmount(wxresp.get("settlement_total_fee"));
				transFlow.setSendPayDate(wxresp.get("time_end"));
				transFlow.setTradeNo(wxresp.get("transaction_id"));
				message.setPayStatu("07");
			}
				
			queryOrderMapper.updateTradeStatusWx(transFlow);
			
			
		}else{
			message =null;
		}
			
		return message;
	}

}
