package com.sunyard.itp.service.imp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sunyard.itp.constant.PayConst;
import com.sunyard.itp.entity.PrecreateParams;
import com.sunyard.itp.entity.TransFlow;
import com.sunyard.itp.entity.WxPerParams;
import com.sunyard.itp.mapper.PrecreateMapper;
import com.sunyard.itp.service.PrecreateService;
import com.sunyard.itp.service.TransFlowService;
import com.sunyard.itp.utils.wxpay.WXPay;
import com.sunyard.itp.utils.wxpay.WXPayConfigImpl;

/**
 * 被扫预下单
 * @author zhix.huang
 *
 */
@Service
public class PrecreateServiceImp implements PrecreateService{
	@Autowired
	private TransFlowService transFlowService;
	@Autowired
	private PrecreateMapper precreateMapper;
	
	protected Logger logger = LoggerFactory.getLogger(PrecreateServiceImp.class);
	@Override
	public String precreate(HttpServletRequest req , HttpServletResponse resp ,PrecreateParams payParams) throws Exception {
		if(payParams.getPayFlag() != null && payParams.getPayFlag().equals("0")){
			logger.info("zhix.huang:------------进入支付宝预支付接口");
			logger.info("支付宝支付");
			String out_trade_no = "alipay-pre" + System.currentTimeMillis()
		    + (long) (Math.random() * 10000000L);
			String totalFee = payParams.getTotalFee();
			AlipayClient alipayClient = new DefaultAlipayClient(PayConst.OPEN_API_DOMAIN,
					PayConst.APP_ID,
					PayConst.PRIVATE_KEY,
					"json","UTF-8",
					PayConst.ALIPAY_PUBLIC_KEY,
					PayConst.SIGN_TYPE);
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
			//异步回调通知
			request.setNotifyUrl(PayConst.NOTIFY_URL);
			//同步通知
			request.setReturnUrl(PayConst.RETURN_URL);
			request.setBizContent("{" +
			"\"out_trade_no\":\""+out_trade_no+"\"," +
			"\"total_amount\":"+totalFee+"," +
//			"\"seller_id \":\"2088102170223040\"," +
			"\"subject\":\"支付宝被扫订单\"" +	
			"}" 	
			);
			AlipayTradePrecreateResponse response = alipayClient.execute(request);			
			if(response.isSuccess()){
			logger.info("调用预下单成功");
			logger.info(response.getQrCode());
			logger.info(out_trade_no);
			
			//查询订单     （注：支付宝预下单无法查询,放弃调用）
//			AlipayTradeQueryResponse queryRes = queryAlipayOrder(out_trade_no);
			//如果预下单成功，将该交易流水插入到数据库中
			TransFlow transFlow = new TransFlow();
			transFlow.setTradeNo("待支付下单");
			transFlow.setOutTradeNo(out_trade_no);
			transFlow.setBuyerLogonId("待支付下单");
			transFlow.setTradeStatus("待支付下单");
			transFlow.setTotalAmount(totalFee);
			transFlow.setReceiptAmount(totalFee);
			//转换时间格式
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = df.format(new Date());
			transFlow.setSendPayDate(date);
			transFlow.setBuyerUserId("待支付下单");
			transFlow.setTransType("0");
			//不再公司环境连接不上数据库
			//transFlowService.addTransFlow(transFlow);
			return response.getQrCode();		
			} else {
			logger.info("调用预下单失败");
			return "fail";
			}	
			//微信支付/**
			/**
			 * 扫码支付的方式获取的支付二维码的连接在页面无法实现跳转，故启用
			 */
		}else if(payParams.getPayFlag() != null && payParams.getPayFlag().equals("1")){
			logger.info(payParams.getPayFlag());
			logger.info("微信支付");
			String out_trade_no = "wxpay-pre" + System.currentTimeMillis()
		    + (long) (Math.random() * 10000000L);
			String body = "微信扫码模式一";
			String fee = payParams.getTotalFee();
			int totalFee = Integer.parseInt(fee) * 100;
			String product_id = "pro_id" + System.currentTimeMillis()
		    + (long) (Math.random() * 10000000L);
			
			//将预下单参数此轮入数据库
			WxPerParams wxPerParams = new WxPerParams();
			wxPerParams.setProductId(product_id);
			wxPerParams.setOutTradeNo(out_trade_no);
			wxPerParams.setBody(body);
			wxPerParams.setTotalFee(fee);
			
			precreateMapper.addWxPerParams(wxPerParams);
			
			/**
			 * 微信扫码支付模式二
			 */
			//加载微信支付参数
			WXPayConfigImpl config =  WXPayConfigImpl.getInstance();
			WXPay wxpay = new WXPay(config);
			
			//组装上传参数
			HashMap<String,String> data = new HashMap<String,String>();
			 data.put("body", "微信被扫预下单");
		        data.put("out_trade_no", out_trade_no);
		        data.put("device_info", "");
		        data.put("fee_type", "CNY");
		        data.put("total_fee", payParams.getTotalFee());
		        data.put("spbill_create_ip",req.getRemoteAddr());
		        data.put("notify_url", PayConst.WX_NOTIFY_URL);
		        data.put("trade_type", "NATIVE");
		        data.put("product_id", "12");
	    	
	             Map<String, String> r = wxpay.unifiedOrder(data);
	             logger.info(r.toString());
	             if(r.get("result_code").equals("SUCCESS")){
	            	 logger.info("微信预下单成功!");
	                 String qrcode =  r.get("code_url");
	                 logger.info("prepay_id:"+r.get("prepay_id"));
//		             logger.info(r.get("code_url"));
	               //生成二维码
	         	  /**
	                 Map<EncodeHintType, Object>  hints=new HashMap<EncodeHintType, Object>();
	                 // 指定纠错等级  
	                 hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
	                 // 指定编码格式  
	                 hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
	                 hints.put(EncodeHintType.MARGIN, 1);
	                 try {
	         			BitMatrix bitMatrix = new MultiFormatWriter().encode(qrcode,BarcodeFormat.QR_CODE, 200, 200, hints);
	         			OutputStream out = resp.getOutputStream();
	         			MatrixToImageWriter.writeToStream(bitMatrix, "png", out);//输出二维码
	                     out.flush();
	                     out.close();
	                     
	                 } catch (WriterException e) {
	         			// TODO Auto-generated catch block
	         			e.printStackTrace();
	         		}
	         		*/
		 	        //调用查询微信订单接口
		 	      Map<String,String> queryRes = queryWxOrder(out_trade_no);
		 	        //组织流水参数
		 	        TransFlow transFlow = new TransFlow();
		 	        //支付不成功无法查询微信订单号
		 	       transFlow.setTradeNo("微信订单");
		 	       transFlow.setOutTradeNo(out_trade_no);
		 	      //支付不成功无法查询用户信息
		 	       transFlow.setBuyerLogonId("微信用户");
		 	       transFlow.setTradeStatus("预下单成功，支付中");
				   transFlow.setTotalAmount(payParams.getTotalFee());
				   transFlow.setReceiptAmount(payParams.getTotalFee());
				 //转换时间格式
					DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String date = df.format(new Date());
				   transFlow.setSendPayDate(date);
				   transFlow.setBuyerUserId(queryRes.get("openid"));
				   transFlow.setTransType("1");
				   transFlowService.addTransFlow(transFlow);
		 	    		 	        
	//	            return qrcode;
				   return r.get("prepay_id");
	             }else{
	            	 logger.info("微信预下单失败！");
	            	 String errCode = r.get("err_code");
	            	 String errCodeDes = r.get("err_code_des");		 
	            	 return "错误代码："+errCode+"    错误描述："+errCodeDes;
	             }
		}
			/**
			 * 微信扫码支付模式一
			 */
			/**
			String nonce_str = WXPayUtil.generateUUID();
			long time_stamp = System.currentTimeMillis() / 1000;
			
			String key = PayConst.WX_API_KEY; // key
			
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			packageParams.put("appid", PayConst.WX_APP_ID);
			packageParams.put("mch_id", PayConst.WX_Mch_ID);
			packageParams.put("time_stamp", String.valueOf(time_stamp));
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("product_id", product_id);
			String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);//MD5哈希
		    packageParams.put("sign", sign); 
		    
		    //生成参数
		    String str = ToUrlParams(packageParams);
		    String payurl = "weixin://wxpay/bizpayurl?" + str;
		    logger.info("payurl:"+payurl);
		    logger.info(payurl);
			return payurl;
		}
		*/
			else{
			logger.info(payParams.getPayFlag());
			return "非法支付类型";
		}	
		
	}
	/**
	 * 查询支付宝订单状态		弃用
	 * @param out_trade_no 
	 * @return 
	 * @throws AlipayApiException 
	 */
	public AlipayTradeQueryResponse queryAlipayOrder(String out_trade_no) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(PayConst.OPEN_API_DOMAIN,
				PayConst.APP_ID,
				PayConst.PRIVATE_KEY,
				"json","UTF-8",
				PayConst.ALIPAY_PUBLIC_KEY,
				PayConst.SIGN_TYPE);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		logger.info("-----"+out_trade_no);
		request.setBizContent("{" +
				"\"out_trade_no\":"+out_trade_no+"\"" +
		
		"  }");
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		if(response.isSuccess()){
		logger.info("调用查询成功");
		logger.info(response.getBuyerPayAmount());
		logger.info(response.getBuyerUserId());
		logger.info(response.getSubMsg());
		logger.info(response.getTradeStatus());
		return response;
		} else {
			logger.info("调用查询失败");
			logger.info(response.getBody());
			return null;
			}		
	}
	
	/**
	 * 
	 * @Description: 微信查询订单		弃用
	 * @param out_trade_no
	 * @return
	 * @throws Exception   
	 * Map<String,String>  
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年8月2日 上午9:51:25
	 */
	public Map<String, String> queryWxOrder(String out_trade_no) throws Exception{
		 logger.info("查询订单");
		//加载微信支付参数
		WXPayConfigImpl config =  WXPayConfigImpl.getInstance();
		WXPay wxpay = new WXPay(config);
		HashMap<String, String> data = new HashMap<String, String>();
	        data.put("out_trade_no", out_trade_no);
//	        data.put("transaction_id", "4008852001201608221962061594");
	       
	            Map<String, String> r = wxpay.orderQuery(data);
	            logger.info(r.toString());
	            logger.info(r.get("result_code"));
	            logger.info(r.get("out_trade_no"));
	            logger.info(r.get("settlement_total_fee"));
	            logger.info(r.get("total_fee"));
	            logger.info(r.get("time_end"));
	            logger.info(r.get("cash_fee"));
	            return r;        
	}	
	/**
	 * 
	 * @Description: 微信扫码支付模式一调用
	 * @param packageParams
	 * @return   
	 * String  
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年8月2日 上午9:52:25
	 */
	public String ToUrlParams(SortedMap<Object, Object> packageParams){
		//实际可以不排序
		StringBuffer sb = new StringBuffer();  
        Set es = packageParams.entrySet();  
        Iterator it = es.iterator();  
        while (it.hasNext()) {  
            Map.Entry entry = (Map.Entry) it.next();  
            String k = (String) entry.getKey();  
            String v = (String) entry.getValue();  
            if (null != v && !"".equals(v)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }
        
        sb.deleteCharAt(sb.length()-1);//删掉最后一个&
        return sb.toString();
	}
	/**
	 * 微信扫码模式一参数查询
	 */
	@Override
	public WxPerParams queryWxPerParams(String productId) {
		return precreateMapper.queryWxPerParams(productId);

	}
	
	
}
