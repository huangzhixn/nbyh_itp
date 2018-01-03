package com.sunyard.itp.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunyard.itp.constant.PayConst;
import com.sunyard.itp.entity.TransFlow;
import com.sunyard.itp.entity.WxPerParams;
import com.sunyard.itp.service.PrecreateService;
import com.sunyard.itp.service.TransFlowService;
import com.sunyard.itp.service.imp.PrecreateServiceImp;
import com.sunyard.itp.utils.wxpay.HttpUtil;
import com.sunyard.itp.utils.wxpay.PayCommonUtil;
import com.sunyard.itp.utils.wxpay.Util;
import com.sunyard.itp.utils.wxpay.XMLParser;




@Controller
public class notifyController {
	@Autowired
	private TransFlowService transFlowService;
	@Autowired
	private PrecreateService precreateService;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@RequestMapping("/aliNotify")
	public void notify(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		Map<String, String> paramsMap = ... //将异步通知中收到的待验证所有参数都存放到map中
//			boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET) //调用SDK验证签名
//			if(signVerfied){
//			   // TODO 验签成功后
//			   //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
//			}else{
//			    // TODO 验签失败则记录异常日志，并在response中返回failure.
//			}
		logger.info("进入支付宝异步通知！");
		 //获取支付宝POST过来反馈信息  
	    Map<String,String> params = new HashMap<String,String>();  
	    Map requestParams = request.getParameterMap();  
	    for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {  
	        String name = (String) iter.next();  
	        String[] values = (String[]) requestParams.get(name);  
	        String valueStr = "";  
	        for (int i = 0; i < values.length; i++) {  
	            valueStr = (i == values.length - 1) ? valueStr + values[i]  
	                    : valueStr + values[i] + ",";  
	        }  
	        //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化  
	        valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");  
	        params.put(name, valueStr);  
	    }  	      
	    //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//   
	    String trade_no = request.getParameter("trade_no");             //支付宝交易号  
	    String order_no = request.getParameter("out_trade_no");         //获取订单号  
	    String total_fee = request.getParameter("total_amount");           //获取总金额  
	    String receipt_fee = request.getParameter("receipt_amount");      //获取实收款项
	    String subject = new String(request.getParameter("subject").getBytes("ISO-8859-1"),"gbk");//商品名称、订单名称  
	    String body = "";  
	    if(request.getParameter("body") != null){  
	        body = new String(request.getParameter("body").getBytes("ISO-8859-1"), "gbk");//商品描述、订单备注、描述  
	    }  
	    String buyer_email = request.getParameter("buyer_logon_id");       //买家支付宝账号  
	    String trade_status = request.getParameter("trade_status");     //交易状态  
	    String gmt_payment = request.getParameter("gmt_payment");//付款时间
	    String seller_id = request.getParameter("seller_id");   //支付宝ID    208......  
	    
	    
	    logger.debug("----"+trade_status);
	    PrintWriter out=response.getWriter();
	    //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//  
	  
//	    if(transFlowService.queryTransFlow(params) >= 1){//验证成功          
	        //请在这里加上商户的业务逻辑程序代码  
	        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——  
	        if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){  
	            //判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）  
	                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序  
	                //如果有做过处理，不执行商户的业务程序  	        	
	        	  TransFlow trans = new TransFlow();
	        	  trans.setTradeNo(trade_no);
	              trans.setOutTradeNo(order_no);
	              trans.setTotalAmount(total_fee);
	              trans.setReceiptAmount(total_fee);
	              trans.setSendPayDate(gmt_payment);
	              trans.setBuyerLogonId(buyer_email);
	              trans.setMchntNo(PayConst.APP_ID);
	              trans.setBuyerUserId(seller_id);
	              trans.setTradeStatus("00");
	              trans.setPayModel("1");
	              trans.setTransType("0");
	              trans.setBroadcast("01");
	              //更新数据库v
	              transFlowService.addTransFlow(trans);;
	              
 	              out.println("success"); //请不要修改或删除  ,返回给支付宝
//	        	return "success";
	        } else {  
//	        	 TransFlow trans = new TransFlow();
//	              trans.setTradeNo(trade_no);
//	              trans.setOutTradeNo(order_no);
//	              trans.setTotalAmount(total_fee);
//	              trans.setReceiptAmount(total_fee);
//	              trans.setSendPayDate(gmt_payment);
//	              trans.setBuyerLogonId(buyer_email);
//	     
//	              trans.setBuyerUserId(seller_id);
//	              trans.setTradeStatus("00");
//	              trans.setPayModel("1");
//	              trans.setTransType("1");
//	              trans.setBroadcast("01");
//	              //更新数据库v
//	              transFlowService.addTransFlow(trans);;
	        	
	            out.println("success"); //请不要修改或删除  
//	              return "fail";
	        }  
	  
	        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——  
	  
	        //////////////////////////////////////////////////////////////////////////////////////////  
//	    }else{//验证失败  
//	        out.println("fail");  
//	    }  
}
	/**
	 * 
	 * @Description: 微信异步回调通知
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 * @throws IOException   
	 * String  
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年8月3日 下午8:53:14
	 */
	@RequestMapping("/wxNotify")
	public void wxNotify(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception, IOException{
		
	logger.info("微信异步回调");
	 String retStr = new String(Util.readInput(request.getInputStream()),"utf-8");
     Map<String, Object> map = XMLParser.getMapFromXML(retStr);
     Map<String,String> newMap =new HashMap<String,String>();
     for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() instanceof String){
                 newMap.put(entry.getKey(), (String) entry.getValue());
               }
      }
     logger.debug("+++"+newMap.get("return_code"));
     logger.debug("------"+newMap.get("result_code"));
     //返回的数据
     if (newMap.get("return_code").equals("SUCCESS") && newMap.get("result_code").equals("SUCCESS")) {
    	 logger.debug("----"+"微信支付异步通知，支付成功");
    	 TransFlow transFlow = new TransFlow();
			transFlow.setTradeNo(newMap.get("transaction_id"));
			transFlow.setOutTradeNo(newMap.get("out_trade_no"));
			transFlow.setBuyerLogonId(newMap.get("openid"));
			//支付状态  00-支付成功  01-正在输入密码支付，请确认 02-支付失败
//			transFlow.setTradeStatus(r.get("00"));
			transFlow.setTotalAmount(newMap.get("total_fee"));
			transFlow.setReceiptAmount(newMap.get("settlement_total_fee"));
			transFlow.setSendPayDate(newMap.get("time_end"));
			transFlow.setBuyerUserId(newMap.get("openid"));
			transFlow.setMchntNo(newMap.get("mch_id"));
			transFlow.setTransType("1");
			transFlow.setPayModel("4");
			transFlow.setTradeStatus("00");
			transFlow.setBroadcast("01");
			transFlowService.addTransFlow(transFlow);
			
			response.setContentType("text/xml");
		     String xml= "<xml>"
		             + "<return_code><![CDATA[SUCCESS]]></return_code>"
		             + "<return_msg><![CDATA[OK]]></return_msg>"
		             + "</xml>";
		     response.getWriter().print(xml);
		     response.getWriter().flush();
		     response.getWriter().close();
     //支付回调处理订单 更改订单状态
     }else{
     response.setContentType("text/xml");
     String xml= "<xml>"
             + "<return_code><![CDATA[FAIL]]></return_code>"
             + "<return_msg><![CDATA[NO]]></return_msg>"
             + "</xml>";
     response.getWriter().print(xml);
     response.getWriter().flush();
     response.getWriter().close();
	
     }
	}
	/**
	 * 
	 * @Description: 微信扫码模式一  支付回调
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return   
	 * String  
	 * @throws IOException 
	 * @throws
	 * @author 黄志鑫
	 * @date 2017年7月28日 下午1:48:57
	 */
	@RequestMapping("/wxPreNotify")
	
	public void returnNotify(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws IOException{
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		
		SortedMap<Object, Object> packageParams = PayCommonUtil.xmlConvertToMap(sb.toString());
//		logger.info(packageParams); 


		// 账号信息
		String key = PayConst.WX_API_KEY; // key
		
		String resXml="";//反馈给微信服务器
		// 验签
		if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
			//appid openid mch_id is_subscribe nonce_str product_id sign
			
			//统一下单
			String openid = (String)packageParams.get("openid");
            String product_id = (String)packageParams.get("product_id");
            //解析product_id，计算价格等
            //查询数据库中存储的模式一预下单的数据
            WxPerParams wxPerParams = precreateService.queryWxPerParams(product_id);
            
    		String out_trade_no = wxPerParams.getOutTradeNo(); // 订单号  
    		String order_price = wxPerParams.getTotalFee(); // 价格   注意：价格的单位是分  
            String body = wxPerParams.getBody();   // 商品名称  这里设置为product_id
            String attach = "信雅达店"; //附加数据
            
    		String nonce_str0 = PayCommonUtil.getNonce_str();
    		
            // 获取发起电脑 ip  
            String spbill_create_ip = PayConst.WX_SPBILL_CREATE_IP;    
            String trade_type = "NATIVE"; 
    		
            
            SortedMap<Object,Object> unifiedParams = new TreeMap<Object,Object>();  
            unifiedParams.put("appid", PayConst.WX_APP_ID); // 必须
            unifiedParams.put("mch_id", PayConst.WX_Mch_ID); // 必须
            unifiedParams.put("out_trade_no", out_trade_no); // 必须
            unifiedParams.put("product_id", product_id);
            unifiedParams.put("body", body); // 必须
            unifiedParams.put("attach", attach);
            unifiedParams.put("total_fee", order_price);  // 必须 
            unifiedParams.put("nonce_str", nonce_str0);  // 必须
            unifiedParams.put("spbill_create_ip", spbill_create_ip); // 必须 
            unifiedParams.put("trade_type", trade_type); // 必须  
            unifiedParams.put("openid", openid);  
            unifiedParams.put("notify_url", PayConst.WX_NOTIFY_URL);//异步通知url
            
            String sign0 = PayCommonUtil.createSign("UTF-8", unifiedParams,key);  
            unifiedParams.put("sign", sign0); //签名
            
            String requestXML = PayCommonUtil.getRequestXml(unifiedParams);  
            logger.info(requestXML);
            //统一下单接口
            String rXml = HttpUtil.postData(PayConst.WX_UFDODER_URL, requestXML);  
            
            //统一下单响应
            SortedMap<Object, Object> reParams = PayCommonUtil.xmlConvertToMap(rXml);
 //   		logger.info(reParams); 
    		System.out.println(reParams);
    		//验签
    		if (PayCommonUtil.isTenpaySign("UTF-8", reParams, key)) {
    			// 统一下单返回的参数
    			String prepay_id = (String)reParams.get("prepay_id");//交易会话标识  2小时内有效
    			
        		String nonce_str1 = PayCommonUtil.getNonce_str();
    			
    			SortedMap<Object,Object> resParams = new TreeMap<Object,Object>();  
    			resParams.put("return_code", "SUCCESS"); // 必须
    			resParams.put("return_msg", "OK");
    			resParams.put("appid", PayConst.WX_APP_ID); // 必须
    			resParams.put("mch_id", PayConst.WX_Mch_ID);
    			resParams.put("nonce_str", nonce_str1); // 必须
    			resParams.put("prepay_id", prepay_id); // 必须
    			resParams.put("result_code", "SUCCESS"); // 必须
    			resParams.put("err_code_des", "OK");
    			
    			String sign1 = PayCommonUtil.createSign("UTF-8", resParams,key);  
    			resParams.put("sign", sign1); //签名
    			
    			resXml = PayCommonUtil.getRequestXml(resParams);
    			logger.info(resXml);
                
    		}else{
    			logger.info("签名验证错误");
    			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                        + "<return_msg><![CDATA[签名验证错误]]></return_msg>" + "</xml> "; 
    		}
            
		}else{
			logger.info("签名验证错误");
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                    + "<return_msg><![CDATA[签名验证错误]]></return_msg>" + "</xml> "; 
		}

		//------------------------------  
        //处理业务完毕  
        //------------------------------  
        BufferedOutputStream out = new BufferedOutputStream(  
                response.getOutputStream());  
        out.write(resXml.getBytes());  
        out.flush();  
        out.close();  
	}	
}
