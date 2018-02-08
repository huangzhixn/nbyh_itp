package com.sunyard.itp.service.imp;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.sunyard.itp.constant.PayConst;
import com.sunyard.itp.entity.Message;
import com.sunyard.itp.entity.TradePayParams;
import com.sunyard.itp.entity.TransFlow;
import com.sunyard.itp.service.QueryOrderService;
import com.sunyard.itp.service.TradePayService;
import com.sunyard.itp.service.TransFlowService;
import com.sunyard.itp.utils.DateUtil;
import com.sunyard.itp.utils.union.AcpService;
import com.sunyard.itp.utils.union.DemoBase;
import com.sunyard.itp.utils.union.LogUtil;
import com.sunyard.itp.utils.union.SDKConfig;
import com.sunyard.itp.utils.wxpay.WXPay;
import com.sunyard.itp.utils.wxpay.WXPayConfigImpl;



/**
 * 主扫业务类
 * @author zhix.huang
 *
 */
@Service
public class TradePayServiceImp implements TradePayService{
	/*static{
		//正向代理
	    System.setProperty("http.proxySet", "true");  
		System.setProperty("http.proxyHost", "12.99.128.130");
		System.setProperty("http.proxyPort", "8080");
		
		System.setProperty("https.proxyHost", "12.99.128.130");
		System.setProperty("https.proxyPort", "8080");
	}*/
	@Autowired
	private TransFlowService transFlowService;
	@Autowired
	private QueryOrderService queryOrderService;
	protected Logger logger = LoggerFactory.getLogger(TradePayServiceImp.class);

	@Override
	public Message tradePay(TradePayParams tradePayparams) throws Exception {
		String auth_code = tradePayparams.getAuthCode();
		BigDecimal totalFee = tradePayparams.getTotalFee();
		Message message = new Message();
		if(auth_code.startsWith("25") || auth_code.startsWith("26") || auth_code.startsWith("27") || auth_code.startsWith("28") || auth_code.startsWith("29") || auth_code.startsWith("30")){
			logger.debug("支付宝");
			message.setPayType("0");
			String out_trade_no = "alipay-tra" + System.currentTimeMillis()
		    + (long) (Math.random() * 10000000L);
			message.setOut_trade_no(out_trade_no);
			//正向代理
			System.setProperty("http.proxyHost", "12.99.128.130");
			System.setProperty("http.proxyPort", "8080");
			AlipayClient alipayClient = new DefaultAlipayClient(PayConst.OPEN_API_DOMAIN,
					PayConst.APP_ID,
					PayConst.PRIVATE_KEY,
					"json","UTF-8",
					PayConst.ALIPAY_PUBLIC_KEY,
					PayConst.SIGN_TYPE);
			AlipayTradePayRequest request = new AlipayTradePayRequest();
			request.setNotifyUrl(PayConst.NOTIFY_URL);
			request.setReturnUrl(PayConst.RETURN_URL);
			request.setBizContent("{" +
					"\"out_trade_no\":\""+out_trade_no+"\"," +
					"\"scene\":\""+PayConst.SCENE+"\"," +
					"\"auth_code\":\""+auth_code+"\"," +
					"\"subject\":\"支付宝主扫测试\"," +
					"\"total_amount\":"+totalFee+"" +
					"  }");
			logger.debug("{" +
					"\"out_trade_no\":\""+out_trade_no+"\"," +
					"\"auth_code\":\""+auth_code+"\"," +
					"\"subject\":\"支付宝主扫测试\"," +
					"\"total_amount\":"+totalFee+"" +
					"  }" );
			System.err.println(request.getBizContent());
			AlipayTradePayResponse response = alipayClient.execute(request);
			logger.debug(response.getBody());
			logger.debug("-----"+response);
			message.setAmount(response.getTotalAmount());
			message.setBuyer(response.getBuyerLogonId());
			TransFlow transFlow = new TransFlow();
			transFlow.setTradeNo(response.getTradeNo());
			transFlow.setOutTradeNo(out_trade_no);
			transFlow.setBuyerLogonId(response.getBuyerLogonId());
//			transFlow.setTradeStatus(response.getTradeStatus());
			double total = (Double.parseDouble(response.getTotalAmount()))*100;
			String total1 =(int)total+""; 
			transFlow.setTotalAmount(total1);
			
			double receipt = (Double.parseDouble(response.getReceiptAmount()))*100;
			String receipt1 =(int)receipt+""; 
			transFlow.setReceiptAmount(receipt1);
			
//			transFlow.setTotalAmount(response.getTotalAmount());
//			transFlow.setReceiptAmount(response.getReceiptAmount());
			transFlow.setMchntNo(PayConst.APP_ID);
			transFlow.setPayModel("0");
			transFlow.setBroadcast("02");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(new Date());
			transFlow.setSendPayDate(date);
			transFlow.setBuyerUserId(response.getBuyerUserId());
			transFlow.setTransType("0");
			
			if(response.isSuccess()){
				logger.debug(response.toString());
				logger.debug("调用成功");
				if(response.getCode().equals("10000") && response.getMsg().equals("Success")){
					logger.debug("支付成功");
					//转换时间格式
					DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date1 = df1.format(response.getGmtPayment());
					transFlow.setSendPayDate(date1);
					transFlow.setTradeStatus("00");
					message.setPayStatu("00");
				}else if(response.getCode().equals("10003")){
					logger.debug("买家正在支付，请确认！");
					transFlow.setTradeStatus("01");
					message.setPayStatu("01");
				}else{
					logger.debug("状态未知!");
					transFlow.setTradeStatus("02");
					message.setPayStatu("02");
				}
				
				//交易成功，插入流水
				
//				AlipayTradeQueryResponse order = queryOrderService.queryAlipayOrder(out_trade_no);
				//如果预下单成功，将该交易流水插入到数据库中
//				TransFlow transFlow = new TransFlow();
//				transFlow.setTradeNo(order.getTradeNo());
//				transFlow.setOutTradeNo(out_trade_no);
//				transFlow.setBuyerLogonId(order.getBuyerLogonId());
//				transFlow.setTradeStatus("00");
//				transFlow.setTotalAmount(order.getTotalAmount());
//				transFlow.setReceiptAmount(order.getReceiptAmount());
//				transFlow.setPayModel("0");
//				//转换时间格式
//				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//				String date = df.format(order.getSendPayDate());
//				transFlow.setSendPayDate(date);
//				transFlow.setBuyerUserId(order.getBuyerUserId());
//				transFlow.setTransType("0");
				
				//插入流水
				//不在公司环境，连接不上数据库
//				transFlowService.addTransFlow(transFlow);
				//return "支付成功！";
				logger.debug("1111111");
//				transFlowService.addTransFlow(transFlow);
				logger.debug("222222");
				} else {
				logger.debug("调用失败");
				transFlow.setTradeStatus("02");
				message.setPayStatu("02");
			}
			return message;
		}else if(auth_code.startsWith("10") || auth_code.startsWith("11") || auth_code.startsWith("12") || auth_code.startsWith("13") || auth_code.startsWith("14") || auth_code.startsWith("15")){
			logger.debug("微信支付");
			message.setPayType("1");
			String statu = "微信支付成功！";
			String out_trade_no = "weixin-tra" + System.currentTimeMillis()
		    + (long) (Math.random() * 10000000L);
			message.setOut_trade_no(out_trade_no);
			WXPayConfigImpl config =  WXPayConfigImpl.getInstance();
			WXPay wxpay = new WXPay(config);
			HashMap<String,String> data = new HashMap<String,String>();
//			data.put("notify_url",PayConst.WX_NOTIFY_URL);
			data.put("body","微信主扫测试");
	    	data.put("out_trade_no", out_trade_no);
	    	String totalFee1 = totalFee.toString();
	    	 Double totalFee2 = Double.parseDouble(totalFee1) * 100;
	    	 int totalFee3 = (new   Double(totalFee2)).intValue();  
	    	 logger.debug("------"+ String.valueOf(totalFee3));
	    	data.put("total_fee", String.valueOf(totalFee3));
	    	data.put("spbill_create_ip", "172.16.17.18");
	    	data.put("auth_code", auth_code);
	    	//正向代理
	    	System.setProperty("http.proxyHost", "12.99.128.130");
			System.setProperty("http.proxyPort", "8080");
	             Map<String, String> r = wxpay.microPay(data);
	             logger.debug(r.toString());
	     
	             String amoubt = r.get("total_fee");
	             double b = (Double.parseDouble(amoubt)) / 100;
	 			String bb = Double.toString(b);
	             message.setAmount(bb);
            	 message.setBuyer(r.get("openid"));
            	 TransFlow transFlow = new TransFlow();
		 			transFlow.setTradeNo(r.get("transaction_id"));
		 			transFlow.setOutTradeNo(out_trade_no);
		 			transFlow.setBuyerLogonId(r.get("openid"));
		 			//支付状态  00-支付成功  01-正在输入密码支付，请确认 02-支付失败
//		 			transFlow.setTradeStatus(r.get("00"));
		 			transFlow.setTotalAmount(r.get("total_fee"));
		 			transFlow.setReceiptAmount(r.get("total_fee"));
		 			transFlow.setSendPayDate(DateUtil.dateformat(r.get("time_end")));
		 			transFlow.setBuyerUserId(r.get("openid"));
		 			transFlow.setMchntNo(r.get("mch_id"));
		 			transFlow.setTransType("1");
		 			transFlow.setPayModel("3");
		 			transFlow.setBroadcast("02");
	             //如果成功 插入流水
	             if(r.get("result_code").equals("SUCCESS")){
		                      
//	            	 Map<String, String> order = queryOrderService.queryWxOrder(out_trade_no);
		           //如果预下单成功，将该交易流水插入到数据库中   组织数据
//		 			TransFlow transFlow = new TransFlow();
//		 			transFlow.setTradeNo(order.get("transaction_id"));
//		 			transFlow.setOutTradeNo(out_trade_no);
//		 			transFlow.setBuyerLogonId(order.get("openid"));
//		 			//支付状态  00-支付成功  01-正在输入密码支付，请确认 02-支付失败
		 			transFlow.setTradeStatus("00");
//		 			transFlow.setTotalAmount(order.get("total_fee"));
//		 			transFlow.setReceiptAmount(order.get("settlement_total_fee"));
//		 			transFlow.setSendPayDate(order.get("time_end"));
//		 			transFlow.setBuyerUserId(order.get("openid"));
//		 			transFlow.setTransType("1");
//		 			transFlow.setPayModel("3");
//		 			//插入流水    非公司环境   不连数据库
//		 			transFlowService.addTransFlow(transFlow);
	 			
	            	 message.setPayStatu("00");
	            	 
//		 			return message;
	             }else{
	            	 logger.debug("微信刷卡支付失败");
	            	 statu = "微信支付失败";
	            	 String errCode = r.get("err_code");
	            	 logger.debug("错误代码："+errCode);
	            	 String errCodeDes = r.get("err_code_des");		
	            	 logger.debug("错误描述"+errCodeDes);
	            	 transFlow.setTradeStatus("02");
	            	 if(errCode.equals("USERPAYING")){
	            		 logger.debug("买家正在支付，请收银员确认核对！");
	            		 message.setPayStatu("01");
	            		 transFlow.setTradeStatus("01");
//	            		 return message;
	            	 }else{
	            		 logger.debug("微信主扫支付其他错误");
	            		 transFlow.setTradeStatus("02");
	            		 message.setPayStatu("02");
//	            		 return message;
	            	 }
	            	 /**
	            	  * 
	            	 TransFlow transFlow = new TransFlow();
	            	 transFlow.setOutTradeNo(out_trade_no);
	            	 transFlow.setTradeStatus(errCode);
	            	 transFlow.setTransType("1");
	            	//不连数据库
	            	 //transFlowService.addTransFlow(transFlow);
	            	  */
	            	
	             }
//	             transFlowService.addTransFlow(transFlow);
	             return message;
		}else if(auth_code.startsWith("62")){
			logger.debug("银联客户被扫");
			message.setPayType("2");
			Map<String, String> contentData = new HashMap<String, String>();
			
			/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
			contentData.put("version", DemoBase.version);            //版本号 全渠道默认值
			contentData.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
			contentData.put("txnType", "01");              		 	//交易类型 01:消费
			contentData.put("txnSubType", "06");           		 	//交易子类 06：二维码消费
			contentData.put("bizType", "000000");          		 	//填写000000
			contentData.put("channelType", "08");          		 	//渠道类型 08手机
			
			String merId = "898310173990680";
			//交易时间
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = df.format(new Date());
			String termId = "00000011";
			String totalFee1 = totalFee.toString();
			logger.debug("银联主扫支付金额-------------"+totalFee1);
			/***商户接入参数***/
			contentData.put("merId", merId);   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			contentData.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
			contentData.put("orderId", date);        	 	    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
			contentData.put("txnTime", date);		 		    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			contentData.put("txnAmt", totalFee1);						//交易金额 单位为分，不能带小数点
			contentData.put("currencyCode", "156");                 //境内商户固定 156 人民币
			contentData.put("qrNo", auth_code);                 		//C2B码,1-20位数字
			contentData.put("termId", termId);                  	//终端号
			logger.debug("授权码++++++++++"+auth_code);
			logger.debug("收款金额+++++++++"+totalFee);
			// 请求方保留域，
	        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
	        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
	        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//			contentData.put("reqReserved", "透传信息1|透传信息2|透传信息3");
	        // 2. 内容可能出现&={}[]"'符号时：
	        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
	        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
	        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
	        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//			contentData.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));
						
			//后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
			//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
			//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
			//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
			//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
			contentData.put("backUrl", DemoBase.backUrl);
			/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
			Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestAppUrl = SDKConfig.getConfig().getBackRequestUrl();								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
			Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,DemoBase.encoding);  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
			if(!rspData.isEmpty()){
				if(AcpService.validate(rspData, DemoBase.encoding)){
					LogUtil.writeLog("验证签名成功");
					String respCode = rspData.get("respCode") ;
					if(("00").equals(respCode)){
						//成功,获取tn号
						//String tn = resmap.get("tn");
						//TODO
						String reqMessage = DemoBase.genHtmlResult(reqData);
						String rspMessage = DemoBase.genHtmlResult(rspData);
						logger.debug("响应数据-----------"+rspData);
						logger.debug("请求数据-----------"+reqData);
						logger.debug("响应数据++++++++++++"+rspMessage);
						logger.debug("请求数据+++++++++++++"+reqMessage);
						message.setPayStatu("00");
						return message;
					}else{
						//其他应答码为失败请排查原因或做失败处理
						message.setPayStatu("02");
						return message;
					}
				}else{
					logger.debug("验证签名失败");
					//TODO 检查验证签名失败的原因
					message.setPayStatu("02");
					return message;
				}
			}else{
				
				//未返回正确的http状态
				logger.debug("未获取到返回报文或返回http状态码非200");
				message.setPayStatu("02");
				return message;
			}
				
		}
		else{
			message.setPayStatu("02");
			return message;
		}
	}
}
