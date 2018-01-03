package com.sunyard.itp.service.imp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.sunyard.itp.constant.PayConst;
import com.sunyard.itp.entity.RefundParams;
import com.sunyard.itp.service.RefundService;
import com.sunyard.itp.service.TransFlowService;
import com.sunyard.itp.utils.wxpay.WXPay;
import com.sunyard.itp.utils.wxpay.WXPayConfigImpl;



/**
 * 被扫预下单
 * @author zhix.huang
 *
 */
@Service
public class RefundServiceImp implements RefundService{
	@Autowired
	private TransFlowService transFlowService;
	
	protected Logger logger = LoggerFactory.getLogger(RefundServiceImp.class);
	@Override
	public String refund(RefundParams refundParams) throws AlipayApiException {
		logger.info("进入退款");
		String receiptAmount = refundParams.getreceiptAmount();
		String refund_amount = refundParams.getRefundFee();
		String out_trade_no = refundParams.getOutTradeNo();
		if(refundParams.getOutTradeNo().startsWith("alipay")){
			AlipayClient alipayClient = new DefaultAlipayClient(PayConst.OPEN_API_DOMAIN,
					PayConst.APP_ID,
					PayConst.PRIVATE_KEY,
					"json","UTF-8",
					PayConst.ALIPAY_PUBLIC_KEY,
					PayConst.SIGN_TYPE);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			
			request.setBizContent("{" +
					"\"out_trade_no\":\""+out_trade_no+"\"," +
					"\"refund_amount\":"+refund_amount+"" +
					"  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				logger.info("调用成功");
				logger.info("支付宝退款返回参数:"+response.getBody());
				//修改数据库流水
				//增加一条退款流水
				
				
				return "支付宝退款成功";
			} else {
				logger.info("支付宝退款调用失败");	
				logger.info("支付宝退款返回参数:"+response.getBody());
				return "支付宝退款失败";
			}
		}else if(refundParams.getOutTradeNo().startsWith("weixin")){
			logger.info("进入微信退款");
			String out_refund_no = "weixin-ref" + System.currentTimeMillis()
		    + (long) (Math.random() * 10000000L);
			HashMap<String, String> data = new HashMap<String, String>();
	        data.put("out_trade_no", out_trade_no);
	        data.put("out_refund_no", out_refund_no);
	        data.put("total_fee", receiptAmount);
	        data.put("refund_fee", refund_amount);
	        data.put("refund_fee_type", "CNY");
	        data.put("op_user_id", PayConst.WX_Mch_ID);

	        try {
	        	//加载微信支付参数
				WXPayConfigImpl config =  WXPayConfigImpl.getInstance();
				WXPay wxpay = new WXPay(config);
	            Map<String, String> r = wxpay.refund(data);
	            logger.info("微信退款返回参数是："+r.toString());
	        } catch (Exception e) {
	            logger.debug(e.getMessage());
	            return "微信退款失败";
	        }
	        //进入数据库操作流水

			
			return "微信退款成功";
		}else{
			logger.info("错误的退款参数！");
			return "退款失败";
		}
		
	}
}